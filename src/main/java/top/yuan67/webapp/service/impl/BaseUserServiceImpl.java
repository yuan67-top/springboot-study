package top.yuan67.webapp.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.yuan67.webapp.entity.BaseUser;
import top.yuan67.webapp.mapper.BaseUserMapper;
import top.yuan67.webapp.service.BaseUserService;

import javax.annotation.Resource;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-3-15
 * @Desc: 描述信息
 **/
@Service
public class BaseUserServiceImpl implements BaseUserService {
  
  @Resource
  private BaseUserMapper userMapper;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    BaseUser user = userMapper.loadUserByUsername(username);
    if(user == null){
      throw new UsernameNotFoundException("用户名不存在");
    }
    return user;
  }
}
