package top.yuan67.webapp.configuration.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import top.yuan67.webapp.util.HTTPResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 权限不足
 * @author CAPTAIN
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  public static final Logger log = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(403);//权限不足
    PrintWriter printWriter = response.getWriter();

    HTTPResponse httpResponse = HTTPResponse.error("AbstractAccessDecisionManager.accessDenied");
    printWriter.write(new ObjectMapper().writeValueAsString(httpResponse));
    printWriter.flush();
    printWriter.close();
    log.info("访问失败!权限不足{}", accessDeniedException);
  }
}
