package top.yuan67.webapp.exception;

import top.yuan67.webapp.util.MessageUtil;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-4-23
 * @Desc: token异常
 **/
public class AccessTokenException extends Exception{
  public AccessTokenException(String message) {
    super(MessageUtil.get(message));
  }
  
  public AccessTokenException(String message, Object... args) {
    super(MessageUtil.get(message, args));
  }
}
