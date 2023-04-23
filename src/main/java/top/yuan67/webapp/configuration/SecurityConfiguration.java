package top.yuan67.webapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import top.yuan67.webapp.configuration.handler.CustomAccessDeniedHandler;
import top.yuan67.webapp.configuration.handler.CustomLogoutHandler;
import top.yuan67.webapp.configuration.handler.CustomLogoutSuccessHandler;
import top.yuan67.webapp.configuration.handler.UnAuthenticatedRequestHandler;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-3-15
 * @Desc: 描述信息
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
  
  @Bean
  public AuthenticationManager manager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
  
  @Bean
  public BCryptPasswordEncoder encoder(){
    return new BCryptPasswordEncoder();
  }
  
  String[] permitAll = {
      "/oauth/loginAdmin",
      "/oauth/loginUser"
  };
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()//关闭csrf
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//关闭session
        .and()
        .formLogin().disable()//关闭表单登录
        .logout().addLogoutHandler(new CustomLogoutHandler())
        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new UnAuthenticatedRequestHandler())
        .accessDeniedHandler(new CustomAccessDeniedHandler())
        .and()
        .authorizeRequests(auth->
            auth.antMatchers(permitAll).permitAll()
            .anyRequest().authenticated()
        );
    return http.build();
  }
}
