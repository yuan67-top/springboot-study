package top.yuan67.webapp.configuration.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import top.yuan67.webapp.constant.AuthorizationConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomLogoutHandler implements LogoutHandler {
    public static final Logger log = LoggerFactory.getLogger(CustomLogoutHandler.class);
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("CustomLogoutHandler======{}", authentication);
        log.info("request======{}", request.getHeader(AuthorizationConstant.ACCESS_TOKEN));
    }
}
