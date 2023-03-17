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
  public static final String AUTHORIZATION_Admin_ACCESS_TOKEN = "Authorization:Admin:AccessToken:";
  public static final String AUTHORIZATION_Admin_REFRESH_TOKEN = "Authorization:Admin:RefreshToken:";
  public static final String AUTHORIZATION_User_ACCESS_TOKEN = "Authorization:User:AccessToken:";
  public static final String AUTHORIZATION_User_REFRESH_TOKEN = "Authorization:User:RefreshToken:";
  public static final String ADMIN = "admin";//redis中用户键
  public static final String MENU = "Menu:";//redis中的菜单
  public static final String HEADER = "header";
  public static final String ACCESS_TOKEN = "access_token";//redis中token键
  public static final String REFRESH_TOKEN = "refresh_token";//redis中token键
  public static final String USERNAME = "username";//用户名
  public static final String ID = "id";//用户id
  public static final String ISSUER = "jian-shi";//token颁发主体
  public static final String EMAIL_CODE = "Email:Code:";
  public static final String PHONE_CODE = "Phone:Code:";
  public static final String NAME = "name";
  public static final String USER = "user";
}
