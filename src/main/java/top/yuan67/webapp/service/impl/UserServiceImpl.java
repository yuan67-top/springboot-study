package top.yuan67.webapp.service.impl;

import org.springframework.stereotype.Service;
import top.yuan67.webapp.entity.User;
import top.yuan67.webapp.mapper.UserMapper;
import top.yuan67.webapp.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-2-17
 * @Desc: 描述信息
 **/
@Service
public class UserServiceImpl implements UserService {
  @Resource
  private UserMapper userMapper;
  
  @Override
  public int add(User user) {
    return userMapper.add(user);
  }
  
  @Override
  public int del(Integer id) {
    return userMapper.del(id);
  }
  
  @Override
  public List<User> findAll() {
    return userMapper.findAll();
  }
  
  @Override
  public int update(User user) {
    return userMapper.update(user);
  }
}
