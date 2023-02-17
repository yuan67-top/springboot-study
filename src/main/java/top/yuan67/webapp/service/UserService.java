package top.yuan67.webapp.service;

import top.yuan67.webapp.entity.User;

import java.util.List;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-2-17
 * @Desc: 描述信息
 **/
public interface UserService {
  int add(User user);
  
  int del(Integer id);
  
  List<User> findAll();
  
  int update(User user);
}
