package top.yuan67.webapp.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.yuan67.webapp.entity.BaseAdmin;
import top.yuan67.webapp.mapper.BaseAdminMapper;

import javax.annotation.Resource;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-3-15
 * @Desc: 描述信息
 **/
@Service
public class BaseAdminServiceImpl implements UserDetailsService {
  
  @Resource
  private BaseAdminMapper adminMapper;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    BaseAdmin admin = adminMapper.loadUserByUsername(username);
    if(admin == null){
      throw new UsernameNotFoundException("用户名不存在");
    }
    return admin;
  }
}
