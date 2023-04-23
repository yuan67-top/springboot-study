package top.yuan67.webapp.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.yuan67.webapp.constant.AuthorizationConstant;
import top.yuan67.webapp.entity.BaseAdmin;
import top.yuan67.webapp.entity.BaseUser;
import top.yuan67.webapp.exception.AccessTokenException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author CAPTAIN
 */
@Component
public class TokenUtil {
  private static final Logger log = LoggerFactory.getLogger(TokenUtil.class);
  
  @Resource
  private RedisTemplate redisTemplate;
  
  public Map<String, Object> login(BaseAdmin admin){
    Map<String, Object> accessToken = generatorAccessToken(admin);
    String refreshToken = generatorRefreshToken(admin);
    accessToken.put(AuthorizationConstant.HEADER_REFRESH_TOKEN, refreshToken);
    return accessToken;
  }
  
  public Map<String, Object> login(BaseUser user){
    Map<String, Object> accessToken = generatorAccessToken(user);
    String refreshToken = generatorRefreshToken(user);
    accessToken.put(AuthorizationConstant.HEADER_REFRESH_TOKEN, refreshToken);
    return accessToken;
  }
  
  /**
   * 管理员生成token
   * @param admin
   * @return
   */
  public Map<String, Object> generatorAccessToken(BaseAdmin admin) {
    admin.setPassword(null);
    return accessTokenResult(JSON.toJSON(admin).toString(), admin.getUsername());
  }
  
  private Map<String, Object> accessTokenResult(String userinfo, String username){
    
    String uuid = IdUtil.fastSimpleUUID();
    
    String token = JWT.create().setKey(AuthorizationConstant.ACCESS_TOKEN_SECRET.getBytes())
        .setHeader(AuthorizationConstant.HEADER, AuthorizationConstant.HEADER_ACCESS_TOKEN)
        .setPayload(AuthorizationConstant.ID, uuid)
        .setPayload(AuthorizationConstant.USERNAME, username)
        .sign();
    
    String keyToken = generatorAccessTokenKey(username, uuid);
    
    
    //将token存入redis
    redisTemplate.opsForHash().put(keyToken, AuthorizationConstant.TOKEN, token);
    redisTemplate.opsForHash().put(keyToken, AuthorizationConstant.USER_INFO, userinfo);
    redisTemplate.expire(keyToken, AuthorizationConstant.ACCESS_TOKEN_EXPIRE, TimeUnit.MINUTES);
    
    Map<String, Object> loginAdmin = new LinkedHashMap<>();
    loginAdmin.put(AuthorizationConstant.ID, uuid);
    loginAdmin.put(AuthorizationConstant.USERNAME, username);
    
    Map<String, Object> result = new LinkedHashMap<>();
    result.put(AuthorizationConstant.USER_INFO, loginAdmin);
    result.put(AuthorizationConstant.HEADER_ACCESS_TOKEN, token);
    return result;
  }
  
  /**
   * 用户生成token
   * @param user
   * @return
   */
  public Map<String, Object> generatorAccessToken(BaseUser user) {
    user.setPassword(null);
    return accessTokenResult(JSON.toJSON(user).toString(), user.getUsername());
  }
  
  /**
   * 管理员生成刷新token
   * @param admin
   * @return
   */
  public String generatorRefreshToken(BaseAdmin admin) {
    admin.setPassword(null);
    return refreshTokenResult(admin.getUsername(), JSON.toJSON(admin).toString());
  }
  
  private String refreshTokenResult(String username, String userinfo){
    String uuid = IdUtil.fastSimpleUUID();
    
    String token = JWT.create().setKey(AuthorizationConstant.REFRESH_TOKEN_SECRET.getBytes())
        .setHeader(AuthorizationConstant.HEADER, AuthorizationConstant.HEADER_REFRESH_TOKEN)
        .setPayload(AuthorizationConstant.ID, uuid)
        .setPayload(AuthorizationConstant.USERNAME, username)
        .sign();
    
    String key = generatorRefreshTokenKey(username, uuid);
    
    //将token存入redis
    redisTemplate.opsForHash().put(key, AuthorizationConstant.TOKEN, token);
    redisTemplate.opsForHash().put(key, AuthorizationConstant.USER_INFO, userinfo);
    redisTemplate.expire(key, AuthorizationConstant.REFRESH_TOKEN_EXPIRE, TimeUnit.MINUTES);
    
    return token;
  }
  
  /**
   * 用户生成刷新token
   * @param user
   * @return
   */
  public String generatorRefreshToken(BaseUser user) {
    user.setPassword(null);
    return refreshTokenResult(user.getUsername(), JSON.toJSON(user).toString());
  }
  
  public boolean verify(String accessToken) throws AccessTokenException {
    if (isEmpty(accessToken)) {//如果请求头中有token
      throw new AccessTokenException("令牌解析异常");
    }
    boolean b;
    try{
      b = JWTUtil.verify(accessToken, AuthorizationConstant.ACCESS_TOKEN_SECRET.getBytes());
    }catch (RuntimeException e){
      log.warn("e:{}", e);
      throw new AccessTokenException("令牌解析异常");
    }
    return b;
  }
  
  public void logout(){
    String accessTokenKey = accessTokenKey();
    String refreshTokenKey = refreshTokenKey();
    redisTemplate.delete(accessTokenKey);
    redisTemplate.delete(refreshTokenKey);
  }
  
  /**
   * 更新token过期时间，
   * @param accessToken 认证token
   * @param refreshToken 刷新token
   */
  public void refreshTokenExpire(String accessToken, String refreshToken){
    String accessTokenKey = accessTokenKey(accessToken);
    String refreshTokenKey = refreshTokenKey(refreshToken);
    redisTemplate.expire(accessTokenKey, AuthorizationConstant.ACCESS_TOKEN_EXPIRE, TimeUnit.MINUTES);
    redisTemplate.expire(refreshTokenKey, AuthorizationConstant.REFRESH_TOKEN_EXPIRE,
        TimeUnit.MINUTES);
  }
  
  /**
   * 根据请求获取token，然后返uuid和username
   * @return
   */
  public TokenKey parseTokenToAdmin(){
    HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
    String token = request.getHeader(AuthorizationConstant.HEADER_ACCESS_TOKEN);
    String payloads = JWTUtil.parseToken(token).getPayloads().toString();
    return JSONObject.parseObject(payloads, TokenKey.class);
  }
  
  public TokenKey parseTokenToAdmin(String token){
    String payloads = JWTUtil.parseToken(token).getPayloads().toString();
    return JSONObject.parseObject(payloads, TokenKey.class);
  }
  
  /**
   * 根据认证token获取完整的key
   * @param accessToken
   * @return
   */
  public String accessTokenKey(String accessToken){
    TokenKey token = parseTokenToAdmin(accessToken);
    String tokenKey = new StringBuilder(AuthorizationConstant.AUTHORIZATION_ACCESS_TOKEN)
        .append(token.getUsername()).append(":").append(token.getId()).toString();
    return tokenKey;
  }
  
  public String accessTokenKey(){
    TokenKey token = parseTokenToAdmin();
    String tokenKey = new StringBuilder(AuthorizationConstant.AUTHORIZATION_ACCESS_TOKEN)
        .append(token.getUsername()).append(":").append(token.getId()).toString();
    return tokenKey;
  }
  
  /**
   * 根据刷新token获取完整的key
   * @param refreshToken
   * @return
   */
  public String refreshTokenKey(String refreshToken){
    TokenKey token = parseTokenToAdmin(refreshToken);
    String tokenKey = new StringBuilder(AuthorizationConstant.AUTHORIZATION_REFRESH_TOKEN)
        .append(token.getUsername()).append(":").append(token.getId()).toString();
    return tokenKey;
  }
  public String refreshTokenKey(){
    TokenKey token = parseTokenToAdmin();
    String tokenKey = new StringBuilder(AuthorizationConstant.AUTHORIZATION_REFRESH_TOKEN)
        .append(token.getUsername()).append(":").append(token.getId()).toString();
    return tokenKey;
  }
  
  /**
   * 获取已登录用户的认证token的key
   * @return
   */
  public String accessTokenKeys(){
    TokenKey token = parseTokenToAdmin();
    String tokenKeys = new StringBuilder(AuthorizationConstant.AUTHORIZATION_ACCESS_TOKEN)
        .append(token.getUsername()).append(":*").toString();
    return tokenKeys;
  }
  
  /**
   * 根据username查询当前账户登录情况，
   * @param username
   * @return 所有登录账号
   */
  public Set<String> accessTokenKeys(String username){
    String tokenKeys = new StringBuilder(AuthorizationConstant.AUTHORIZATION_ACCESS_TOKEN)
        .append(username).append(":*").toString();
    return redisTemplate.keys(tokenKeys);
  }
  
  
  /**
   * 获取已登录用户的刷新token的key
   * @return
   */
  public String refreshTokenKeys(){
    TokenKey token = parseTokenToAdmin();
    String tokenKey = new StringBuilder(AuthorizationConstant.AUTHORIZATION_REFRESH_TOKEN)
        .append(token.getUsername()).append(":*").toString();
    return tokenKey;
  }
  
  /**
   * 生成认证token的key
   * @param username
   * @param uuid
   * @return
   */
  public String generatorAccessTokenKey(String username, String uuid){
    return new StringBuilder(AuthorizationConstant.AUTHORIZATION_ACCESS_TOKEN)
        .append(username).append(":").append(uuid).toString();
  }
  
  /**
   * 生成刷新token的key
   * @param username
   * @param uuid
   * @return
   */
  public String generatorRefreshTokenKey(String username, String uuid){
    return new StringBuilder(AuthorizationConstant.AUTHORIZATION_REFRESH_TOKEN).append(username).append(":").append(uuid).toString();
  }
  class TokenKey {
    String id;
    String username;
    
    public String getId() {
      return id;
    }
    
    public void setId(String uuid) {
      this.id = uuid;
    }
    
    public String getUsername() {
      return username;
    }
    
    public void setUsername(String username) {
      this.username = username;
    }
  }
  
  public boolean isEmpty(String str) {
    if (str == null || str.length() == 0) {
      return true;
    }
    return false;
  }
  
  public boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }
}
