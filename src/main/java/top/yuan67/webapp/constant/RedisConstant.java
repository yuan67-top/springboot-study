package top.yuan67.webapp.constant;

public class RedisConstant {
  public static final String CAPTCHA = "Captcha:";
  
  /**
   * 账户试错最大次数
   */
  public static final int UPPER_LIMIT = 5;
  
  /**
   * token有效时间 单位天
   */
  public static final int ACCESS_TOKEN_EXPIRE = 1;
  public static final int REFRESH_TOKEN_EXPIRE = 5;
  
  /**
   * 禁用账户记录的key
   **/
  public static final String BanKey = "Ban:";
}
