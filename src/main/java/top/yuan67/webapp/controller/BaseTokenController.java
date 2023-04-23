package top.yuan67.webapp.controller;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import top.yuan67.webapp.constant.AuthorizationConstant;
import top.yuan67.webapp.entity.BaseAdmin;
import top.yuan67.webapp.entity.BaseUser;
import top.yuan67.webapp.exception.AccessTokenException;
import top.yuan67.webapp.service.BaseAdminService;
import top.yuan67.webapp.service.BaseUserService;
import top.yuan67.webapp.util.HTTPResponse;
import top.yuan67.webapp.util.MessageUtil;
import top.yuan67.webapp.util.TokenUtil;
import top.yuan67.webapp.vo.LoginAdminVO;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author CAPTAIN
 */
@RestController
@RequestMapping("/oauth")
public class BaseTokenController {
  public static final Logger log = LoggerFactory.getLogger(BaseTokenController.class);
  
  @Resource
  private RedisTemplate redisTemplate;
  
  @Resource
  private TokenUtil tokenUtil;
  
  @Resource
  private BaseAdminService adminService;
  
  @Resource
  private BaseUserService userService;
  
  @Resource
  private BCryptPasswordEncoder encoder;
  
  /**
   * 管理员
   * 用户名+密码
   *
   * @param login
   * @return
   */
  @PostMapping("/loginAdmin")
  public HTTPResponse loginAdmin(@RequestBody LoginAdminVO login){
    log.info("用户登录：{}", login);
    
    Authentication authentication;
    try {
      DaoAuthenticationProvider adminProvider = new DaoAuthenticationProvider();
      adminProvider.setUserDetailsService(adminService);
      adminProvider.setPasswordEncoder(encoder);//设置密码加密方式
      ProviderManager manager = new ProviderManager(adminProvider);
      UsernamePasswordAuthenticationToken token =
          new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
      
      authentication = manager.authenticate(token);
    } catch (Exception e) {
      return exception(login, e);
    }
    SecurityContextHolder.getContext()
        .setAuthentication(UsernamePasswordAuthenticationToken
            .authenticated(authentication.getPrincipal(), null, authentication.getAuthorities()));
  
    return HTTPResponse.ok("登录成功", tokenUtil.login((BaseAdmin) authentication.getPrincipal()));
  }
  
  
  /**
   * 普通用户
   * 用户名+密码
   * @param login
   * @return
   */
  @PostMapping("/loginUser")
  public HTTPResponse loginUser(@RequestBody LoginAdminVO login) throws AccessTokenException {
  
    Authentication authentication;
    try{
    
      DaoAuthenticationProvider userProvider = new DaoAuthenticationProvider();
      userProvider.setUserDetailsService(userService);
      userProvider.setPasswordEncoder(encoder);//设置密码加密方式
      ProviderManager manager = new ProviderManager(userProvider);
    
      UsernamePasswordAuthenticationToken token =
          new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
    
      authentication = manager.authenticate(token);
    
    }catch (Exception e){
      return exception(login, e);
    }
  
    SecurityContextHolder.getContext()
        .setAuthentication(UsernamePasswordAuthenticationToken
            .authenticated(authentication.getPrincipal(), null, authentication.getAuthorities()));
  
    return HTTPResponse.ok("登录成功", tokenUtil.login((BaseUser)authentication.getPrincipal()));
  }
  
  
  @GetMapping("/findCurrentUserInfo")
  public Map<String, Object> findCurrentUserInfo(){
    String token = redisTemplate.opsForHash()
        .get(tokenUtil.accessTokenKey(), AuthorizationConstant.USER_INFO).toString();
    return JSONObject.parseObject(token, Map.class);
  }
  
  /**
   * 刷新TOKEN
   * <b>这里用map返回是因为当需要返回刷新的token时前端不弹出提示框</b>
   * @param refreshToken
   * @return
   */
  @PostMapping("/refreshToken/admin")
  public Map<String, Object> refreshTokenAdmin(String refreshToken) {
    return refreshToken(refreshToken, "admin");
  }
  @PostMapping("/refreshToken/user")
  public Map<String, Object> refreshTokenUser(String refreshToken) {
    return refreshToken(refreshToken, "user");
  }
  
  private Map<String, Object> refreshToken(String refreshToken, String type){
    log.info("refreshToken:{}", refreshToken);
    
    Map<String, Object> map = new LinkedHashMap<>();
    if (tokenUtil.isNotEmpty(refreshToken)) {//如果请求头中有token
      boolean verify;
      try {
        //令牌校验
        verify = JWTUtil.verify(refreshToken, AuthorizationConstant.REFRESH_TOKEN_SECRET.getBytes());
        
        log.info("verify:{}", verify);
      } catch (Exception e) {
        log.error("token无法解析:{}", e);
        throw new RuntimeException(MessageUtil.get("令牌解析异常"));
      }
      if (verify) {//校验token是否正确,是否本机生成的token
        try {
          JWT jwt = JWTUtil.parseToken(refreshToken);
          String key = tokenUtil.refreshTokenKey(refreshToken);
          boolean b = redisTemplate.hasKey(key);
          if(!b){
            map.put("status", 500);
            map.put("message", MessageUtil.get("刷新令牌过期"));
            return map;
          }
          String header = jwt.getHeader(AuthorizationConstant.HEADER).toString();
          if (!header.equalsIgnoreCase(AuthorizationConstant.HEADER_REFRESH_TOKEN)) {
            map.put("status", 500);
            map.put("message", MessageUtil.get("令牌解析异常"));
            return map;
          }
          
          /**
           * 这里在token是可正常解析的基础上用前端传进来的token解析得到用户ID,然后去redis获取token是为了就算是前端保存的token是过期的
           * 只要传进来可以正常解析,就可以正常访问本地系统的接口,服务端刷新token,刷新后的不用返回给前端
           */
          String redisRefreshToken = redisTemplate.opsForHash().get(key, AuthorizationConstant.TOKEN).toString();//redis中的token
          String redisAdmin =
              redisTemplate.opsForHash().get(key, AuthorizationConstant.USER_INFO).toString();//redis中的用户信息
          
          //ACCESS_TOKEN过期时间
          long exp = redisTemplate.getExpire(key);
          //当前时间
          long now = System.currentTimeMillis() / 1000;
          log.info("key:{}, exp:{}, now:{}, cha:{}", key, exp, now, exp - now);
          if (exp - now < 0) {//令牌过期===判断查到的刷新token是否过期
            log.info("刷新令牌彻底过期exp:{}, now:{}, exp - now={}", exp, now, (exp - now));
            map.put("status", 500);
            map.put("message", MessageUtil.get("令牌过期！请重新登录"));
            return map;
          }
          if (!redisRefreshToken.equals(refreshToken)) {//redis中的token
            map.put("status", 500);
            map.put("message", MessageUtil.get("令牌解析异常"));
            return map;
          }
          
          log.info("刷新令牌还未过期,但距离过期还有{}分钟,所以刷新token", (exp - now) / 60);
          if(type.equalsIgnoreCase("user")){
            BaseUser user = JSON.parseObject(redisAdmin, BaseUser.class);
            return tokenUtil.login(user);
          }
          if(type.equalsIgnoreCase("admin")){
            BaseAdmin admin = JSON.parseObject(redisAdmin, BaseAdmin.class);
            return tokenUtil.login(admin);
          }
        } catch (Exception e) {
          log.warn("用户身份过期！:{}", e);
          map.put("status", 500);
          map.put("message", MessageUtil.get("用户身份过期!请重新登录"));
          map.put("data", MessageUtil.get("用户身份不存在"));
          return map;
        }
      }else{
        map.put("status", 500);
        map.put("message", MessageUtil.get("令牌解析异常"));
        return map;
      }
    }
    map.put("status", 500);
    map.put("message", MessageUtil.get("关键参数不能为空"));
    return map;
  }
  
  private HTTPResponse exception(LoginAdminVO login, Exception e) {
    
    if (e instanceof BadCredentialsException) {
      log.error("e====={}", e);
      log.info("用户名或密码错误：username:{}, password:{}", login.getUsername(), login.getPassword());
      return HTTPResponse.error("用户名或密码错误");
    }
    return HTTPResponse.error(e.getMessage());
  }
}
