package top.yuan67.webapp.constant;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-3-17
 * @Desc: 描述信息
 **/
public class AuthorizationConstant {
  
  public static final String ACCESS_TOKEN_SECRET = "nieqiang";//accessToken秘钥
  public static final String REFRESH_TOKEN_SECRET = "nieqiang";//refreshToken秘钥
  
  /**
   * access_token存入redis前缀
   */
  public static final String AUTHORIZATION_ACCESS_TOKEN = "Authorization:AccessToken:";
  public static final String AUTHORIZATION_REFRESH_TOKEN = "Authorization:RefreshToken:";
  public static final String USER_INFO = "userinfo";//redis中（认证token与刷新token的用户信息健）用户键
  public static final String MENU = "Menu:";//redis中的菜单
  public static final String HEADER = "header";//token建的key
  public static final String HEADER_ACCESS_TOKEN = "access_token";//redis中认证token header的键
  public static final String HEADER_REFRESH_TOKEN = "refresh_token";//redis中刷新token header的键
  public static final String TOKEN = "token";//redis中token键
  public static final String USERNAME = "username";//用户名
  public static final String ID = "id";//用户id
  public static final String EMAIL_CODE = "Email:Code:";
  public static final String PHONE_CODE = "Phone:Code:";
  
  /**
   * 验证码的key
   */
  public static final String CAPTCHA = "Captcha:";
  
  /**
   * 账户试错最大次数
   */
  public static final int UPPER_LIMIT = 5;
  
  /**
   * 一个账号最大登录数
   */
  public static final int MAX_LOGIN = 2;
  
  /**
   * 超过试错次数后禁用时长，单位分钟
   */
  public static final int DISABLED_DURATION = 30;
  
  /**
   * token有效时间 单位分钟
   */
  public static final int ACCESS_TOKEN_EXPIRE = 1 * 60; // 1小时
  public static final int REFRESH_TOKEN_EXPIRE = 5 * 60; // 5小时
  
  /**
   * 禁用账户记录的key
   **/
  public static final String BanKey = "Ban:";
  
  /**
   * 最小到期时间，单位分钟
   */
  public static final int MIN_EXPIRE = 20;
}
