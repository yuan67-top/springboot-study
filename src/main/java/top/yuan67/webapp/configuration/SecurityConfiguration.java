package top.yuan67.webapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
import top.yuan67.webapp.service.impl.BaseAdminServiceImpl;
import top.yuan67.webapp.service.impl.BaseUserServiceImpl;

import javax.annotation.Resource;

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
  
  @Resource
  private BaseAdminServiceImpl adminService;
  
  @Resource
  private BaseUserServiceImpl userService;
  
  @Bean
  protected AuthenticationManager authenticationManager(){
    DaoAuthenticationProvider userProvider = new DaoAuthenticationProvider();
    userProvider.setUserDetailsService(userService);
    userProvider.setPasswordEncoder(encoder());//设置密码加密方式
  
    DaoAuthenticationProvider adminProvider = new DaoAuthenticationProvider();
    adminProvider.setUserDetailsService(adminService);
    adminProvider.setPasswordEncoder(encoder());//设置密码加密方式

    ProviderManager manager = new ProviderManager(userProvider, adminProvider);
    return manager;
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
