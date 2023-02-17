package top.yuan67.webapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yuan67.webapp.entity.User;
import top.yuan67.webapp.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-2-17
 * @Desc: 描述信息
 **/
@RestController
@RequestMapping("/sqlite")
public class TestController {
  @Resource
  private UserService userService;
  
  //http://127.0.0.1:8088/sqlite/add?age=20&name=lisi
  @GetMapping("/add")
  public String add(User user){
    int result = userService.add(user);
    if(result == 1){
      return "操作成功";
    }
    return "添加失败";
  }
  
  //http://127.0.0.1:8088/sqlite/findAll
  @GetMapping("/findAll")
  public List<User> findAll(){
    return userService.findAll();
  }
  
  //http://127.0.0.1:8088/sqlite/del?id=1
  @GetMapping("/del")
  public String del(Integer id){
    int result = userService.del(id);
    if(result == 1){
      return "操作成功";
    }
    return "添加失败";
  }
  
  //http://127.0.0.1:8088/sqlite/update?id=3&name=李四&age=22
  @GetMapping("/update")
  public String update(User user){
    int result = userService.update(user);
    if(result == 1){
      return "操作成功";
    }
    return "添加失败";
  }
  
}