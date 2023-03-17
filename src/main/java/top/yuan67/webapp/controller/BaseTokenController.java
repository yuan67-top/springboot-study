package top.yuan67.webapp.controller;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson2.JSON;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yuan67.webapp.constant.AuthorizationConstant;
import top.yuan67.webapp.entity.BaseAdmin;
import top.yuan67.webapp.entity.BaseUser;
import top.yuan67.webapp.service.BaseAdminService;
import top.yuan67.webapp.util.HTTPResponse;
import top.yuan67.webapp.util.MessageUtil;
import top.yuan67.webapp.util.TokenUtil;

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
  private AuthenticationManager authenticationManager;
  
  @Resource
  private TokenUtil tokenUtil;
  
  /**
   * 用户名+密码+验证码登录
   * @param username
   * @param password
   * @return
   */
  @PostMapping("/loginAdmin")
  public HTTPResponse loginAdmin(String username, String password) {
    Auth auth = login(username, password);
    if(auth.getAuthentication() == null){
      return HTTPResponse.error(auth.getEx());
    }
    try{
      BaseAdmin admin = (BaseAdmin) auth.getAuthentication().getPrincipal();
      return HTTPResponse.ok("操作成功", tokenUtil.login(admin));
    }catch (Exception e){
      log.error(e.getMessage());
      return HTTPResponse.error("登录失败");
    }
  }
  
  @PostMapping("/loginUser")
  public HTTPResponse loginUser(String username, String password) {
    Auth auth = login(username, password);
    if(auth.getAuthentication() == null){
      return HTTPResponse.error(auth.getEx());
    }
    try{
      BaseUser user = (BaseUser) auth.getAuthentication().getPrincipal();
      return HTTPResponse.ok("操作成功", tokenUtil.login(user));
    }catch (Exception e){
      log.error(e.getMessage());
      return HTTPResponse.error("登录失败");
    }
  }
  
  private Auth login(String username, String password){
    
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(username, password);
    Auth auth = new Auth();
    Authentication authentication;
    try{
      authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }catch (Exception e){
      log.error(e.getMessage());
      auth.setAuthentication(null);
      auth.setEx(e.getMessage());
      return auth;
    }
    
    SecurityContextHolder.getContext()
        .setAuthentication(UsernamePasswordAuthenticationToken
            .authenticated(authentication.getPrincipal(), null, null));
    auth.setAuthentication(authentication);
    auth.setEx(null);
    return auth;
  }
  
  class Auth{
    private Authentication authentication;
    private String ex;
  
    public Authentication getAuthentication() {
      return authentication;
    }
  
    public void setAuthentication(Authentication authentication) {
      this.authentication = authentication;
    }
  
    public String getEx() {
      return ex;
    }
  
    public void setEx(String ex) {
      this.ex = ex;
    }
  }
  
  /**
   * 刷新TOKEN
   * <b>这里用map返回是因为当需要返回刷新的token时前端不弹出提示框</b>
   * @param refreshToken
   * @return
   */
  @PostMapping("/refreshToken")
  public Map<String, Object> refreshToken(String refreshToken) {
    log.info("refreshToken:{}", refreshToken);
  
    Map<String, Object> map = new LinkedHashMap<>();
    if (StringUtil.isNullOrEmpty(refreshToken)) {//如果请求头中有token
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
          String id = jwt.getPayload(AuthorizationConstant.ID).toString();//账号ID
          String key =
              new StringBuffer(AuthorizationConstant.AUTHORIZATION_Admin_REFRESH_TOKEN).append(id).toString();
          String header = jwt.getHeader(AuthorizationConstant.HEADER).toString();
          if (!header.equalsIgnoreCase(AuthorizationConstant.REFRESH_TOKEN)) {
            map.put("status", 500);
            map.put("message", MessageUtil.get("令牌解析异常"));
            return map;
          }
          
          /**
           * 这里在token是可正常解析的基础上用前端传进来的token解析得到用户ID,然后去redis获取token是为了就算是前端保存的token是过期的
           * 只要传进来可以正常解析,就可以正常访问本地系统的接口,服务端刷新token,刷新后的不用返回给前端
           */
          String redisRefreshToken = redisTemplate.opsForHash().get(key, AuthorizationConstant.REFRESH_TOKEN).toString();//redis中的token
          String redisAdmin = redisTemplate.opsForHash().get(key, AuthorizationConstant.ADMIN).toString();//redis中的用户信息
          
          //ACCESS_TOKEN过期时间
          long exp = Long.valueOf(JWTUtil.parseToken(redisRefreshToken).getPayload("exp").toString());
          
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
          BaseAdmin admin = JSON.parseObject(redisAdmin, BaseAdmin.class);
          return tokenUtil.login(admin);
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
}
