package top.yuan67.webapp.configuration.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import top.yuan67.webapp.util.HTTPResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未认证访问处理器
 * @author CAPTAIN
 */
public class UnAuthenticatedRequestHandler implements AuthenticationEntryPoint {
    private static final Logger log = LoggerFactory.getLogger(UnAuthenticatedRequestHandler.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(401);//访问未授权
        PrintWriter printWriter = response.getWriter();
        HTTPResponse httpResponse = HTTPResponse.error("AbstractAccessDecisionManager.accessDenied");
        printWriter.write(new ObjectMapper().writeValueAsString(httpResponse));
        printWriter.flush();
        printWriter.close();
        log.info("访问未授权:Path:{}, authException:{}", request.getServletPath(),authException);
    }
}
