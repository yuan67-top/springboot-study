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
  
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextHandler.getBean("redisTemplate");
    PrintWriter printWriter = response.getWriter();
    String accessToken = request.getHeader(AuthorizationConstant.ACCESS_TOKEN);
  
    String id = JWTUtil.parseToken(accessToken).getPayload(AuthorizationConstant.ID).toString();
    String accessTokenKey =
        new StringBuffer(AuthorizationConstant.AUTHORIZATION_Admin_ACCESS_TOKEN).append(id).toString();
    String refreshTokenKey =
        new StringBuffer(AuthorizationConstant.AUTHORIZATION_Admin_REFRESH_TOKEN).append(id).toString();
    
    String menuId = new StringBuffer(AuthorizationConstant.MENU).append(id).toString();
    
    redisTemplate.delete(accessTokenKey);
    redisTemplate.delete(refreshTokenKey);
    redisTemplate.delete(menuId);
    HTTPResponse httpResponse = HTTPResponse.ok("账号安全退出", authentication);
    
    printWriter.write(new ObjectMapper().writeValueAsString(httpResponse));
    printWriter.flush();
    printWriter.close();
    log.info("用户退出成功!:{}", authentication);
  }
}
