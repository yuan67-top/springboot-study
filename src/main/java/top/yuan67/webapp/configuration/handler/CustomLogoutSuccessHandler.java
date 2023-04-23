package top.yuan67.webapp.configuration.handler;

import cn.hutool.jwt.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import top.yuan67.webapp.constant.AuthorizationConstant;
import top.yuan67.webapp.util.ApplicationContextHandler;
import top.yuan67.webapp.util.HTTPResponse;
import top.yuan67.webapp.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 退出成功!
 * @author CAPTAIN
 */
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
  public static final Logger log = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);
  
  TokenUtil tokenUtil = ApplicationContextHandler.getBean(TokenUtil.class);
  
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                              Authentication authentication) throws IOException {
    String accessToken = request.getHeader(AuthorizationConstant.TOKEN);
    if (accessToken != null){
      tokenUtil.logout();
      log.info("用户退出成功!:{}", authentication);
      HTTPResponse.ok(response, "账号安全退出");
    }
  }
}
