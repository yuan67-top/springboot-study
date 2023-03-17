package top.yuan67.webapp.configuration.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.yuan67.webapp.util.HTTPResponse;

@RestControllerAdvice
public class CustomExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);
  
  @ExceptionHandler(RuntimeException.class)
  public HTTPResponse exceptionHandler(RuntimeException e) {
    log.error("RuntimeException:{}", e);
    return HTTPResponse.error(e.getMessage());
  }
  
//  @ExceptionHandler(DataAccessException.class)
//  public HTTPResponse dataAccessException(Exception e) {
//    return HTTPResponse.error("数据SQL异常");
//  }
  
  @ExceptionHandler(Exception.class)
  public HTTPResponse exception(Exception e) {
    log.error("Exception:{}", e.getMessage());
    return HTTPResponse.error("系统异常");
  }
}
