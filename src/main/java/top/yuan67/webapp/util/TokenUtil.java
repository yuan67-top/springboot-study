package top.yuan67.webapp.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.yuan67.webapp.constant.AuthorizationConstant;
import top.yuan67.webapp.constant.RedisConstant;
import top.yuan67.webapp.entity.BaseAdmin;
import top.yuan67.webapp.entity.BaseUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author CAPTAIN
 */
@Component
public class TokenUtil {
  
  @Resource
  private RedisTemplate redisTemplate;
  
  public Map<String, Object> login(BaseAdmin admin){
    Map<String, Object> accessToken = generatorAccessToken(admin);
    String refreshToken = generatorRefreshToken(admin);
    accessToken.put("refreshToken", refreshToken);
    return accessToken;
  }
  
  public Map<String, Object> login(BaseUser user){
    Map<String, Object> accessToken = generatorAccessToken(user);
    String refreshToken = generatorRefreshToken(user);
    accessToken.put("refreshToken", refreshToken);
    return accessToken;
  }
  
  /**
   * 为管理员生成token
   * @param admin
   * @return
   */
  public Map<String, Object> generatorAccessToken(BaseAdmin admin) {
    admin.setPassword(null);
    
    Date date = new Date();
    int day = RedisConstant.ACCESS_TOKEN_EXPIRE;//天
    
    String token = JWT.create().setKey(AuthorizationConstant.ACCESS_TOKEN_SECRET.getBytes())
        .setHeader(AuthorizationConstant.HEADER, AuthorizationConstant.ACCESS_TOKEN)
        .setPayload(AuthorizationConstant.ID, admin.getId())
        .setPayload(AuthorizationConstant.USERNAME, admin.getUsername())
        .setIssuer(AuthorizationConstant.ISSUER)//颁发主体
        .setExpiresAt(DateUtil.offsetDay(date, day))//token过期时间
        .setIssuedAt(date)//token颁布时间
        .sign();
    
    String keyToken =
        new StringBuilder(AuthorizationConstant.AUTHORIZATION_Admin_ACCESS_TOKEN).append(admin.getId()).toString();
  
    //将token存入redis
    redisTemplate.opsForHash().put(keyToken, AuthorizationConstant.ACCESS_TOKEN, token);
    redisTemplate.opsForHash().put(keyToken, AuthorizationConstant.ADMIN, JSON.toJSON(admin).toString());
    redisTemplate.expire(keyToken, day, TimeUnit.DAYS);
  
    Map<String, Object> loginAdmin = new LinkedHashMap<>();
    loginAdmin.put(AuthorizationConstant.ID, admin.getId());
    loginAdmin.put(AuthorizationConstant.USERNAME, admin.getUsername());
    loginAdmin.put(AuthorizationConstant.NAME, admin.getName());
    loginAdmin.put("createTime", admin.getCreate_time());
    
    Map<String, Object> result = new LinkedHashMap<>();
    result.put(AuthorizationConstant.ADMIN, loginAdmin);
    result.put("token", token);
    return result;
  }
  
  /**
   * 为普通用户生产token
   * @param user
   * @return
   */
  public Map<String, Object> generatorAccessToken(BaseUser user) {
    user.setPassword(null);
    
    Date date = new Date();
    int day = RedisConstant.ACCESS_TOKEN_EXPIRE;//天
    
    String token = JWT.create().setKey(AuthorizationConstant.ACCESS_TOKEN_SECRET.getBytes())
        .setHeader(AuthorizationConstant.HEADER, AuthorizationConstant.ACCESS_TOKEN)
        .setPayload(AuthorizationConstant.ID, user.getId())
        .setPayload(AuthorizationConstant.USERNAME, user.getUsername())
        .setIssuer(AuthorizationConstant.ISSUER)//颁发主体
        .setExpiresAt(DateUtil.offsetDay(date, day))//token过期时间
        .setIssuedAt(date)//token颁布时间
        .sign();
    
    String keyToken =
        new StringBuilder(AuthorizationConstant.AUTHORIZATION_User_ACCESS_TOKEN).append(user.getId()).toString();
    
    //将token存入redis
    redisTemplate.opsForHash().put(keyToken, AuthorizationConstant.ACCESS_TOKEN, token);
    redisTemplate.opsForHash().put(keyToken, AuthorizationConstant.USER,
        JSON.toJSON(user).toString());
    redisTemplate.expire(keyToken, day, TimeUnit.DAYS);
    
    Map<String, Object> loginAdmin = new LinkedHashMap<>();
    loginAdmin.put(AuthorizationConstant.ID, user.getId());
    loginAdmin.put(AuthorizationConstant.USERNAME, user.getUsername());
    loginAdmin.put(AuthorizationConstant.NAME, user.getName());
    loginAdmin.put("createTime", user.getCreate_time());
    
    Map<String, Object> result = new LinkedHashMap<>();
    result.put(AuthorizationConstant.USER, loginAdmin);
    result.put("token", token);
    return result;
  }
  
  /**
   * 为管理员生成刷新token
   * @param admin
   * @return
   */
  public String generatorRefreshToken(BaseAdmin admin) {
    admin.setPassword(null);
    
    Date date = new Date();
    int hours = 30;//30小时
    
    String token = JWT.create().setKey(AuthorizationConstant.REFRESH_TOKEN_SECRET.getBytes())
        .setHeader(AuthorizationConstant.HEADER, AuthorizationConstant.REFRESH_TOKEN)
        .setPayload(AuthorizationConstant.ID, admin.getId())
        .setPayload(AuthorizationConstant.USERNAME, admin.getUsername())
        .setIssuer(AuthorizationConstant.ISSUER)//颁发主体
        .setExpiresAt(DateUtil.offsetHour(date, hours))//token过期时间
        .setIssuedAt(date)//token颁布时间
        .sign();
    
    String key =
        new StringBuilder(AuthorizationConstant.AUTHORIZATION_Admin_REFRESH_TOKEN).append(admin.getId()).toString();
  
    //将token存入redis
    redisTemplate.opsForHash().put(key, AuthorizationConstant.REFRESH_TOKEN, token);
    redisTemplate.opsForHash().put(key, AuthorizationConstant.ADMIN, JSON.toJSON(admin).toString());
    redisTemplate.expire(key, hours, TimeUnit.HOURS);
  
    return token;
  }
  
  /**
   * 为普通用户生产刷新token
   * @param user
   * @return
   */
  public String generatorRefreshToken(BaseUser user) {
    user.setPassword(null);
    
    Date date = new Date();
    int hours = 30;//30小时
    
    String token = JWT.create().setKey(AuthorizationConstant.REFRESH_TOKEN_SECRET.getBytes())
        .setHeader(AuthorizationConstant.HEADER, AuthorizationConstant.REFRESH_TOKEN)
        .setPayload(AuthorizationConstant.ID, user.getId())
        .setPayload(AuthorizationConstant.USERNAME, user.getUsername())
        .setIssuer(AuthorizationConstant.ISSUER)//颁发主体
        .setExpiresAt(DateUtil.offsetHour(date, hours))//token过期时间
        .setIssuedAt(date)//token颁布时间
        .sign();
    
    String key =
        new StringBuilder(AuthorizationConstant.AUTHORIZATION_User_REFRESH_TOKEN).append(user.getId()).toString();
    
    //将token存入redis
    redisTemplate.opsForHash().put(key, AuthorizationConstant.REFRESH_TOKEN, token);
    redisTemplate.opsForHash().put(key, AuthorizationConstant.USER, JSON.toJSON(user).toString());
    redisTemplate.expire(key, hours, TimeUnit.HOURS);
    
    return token;
  }
  
}
