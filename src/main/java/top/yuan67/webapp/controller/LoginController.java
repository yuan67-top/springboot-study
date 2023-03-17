package top.yuan67.webapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-3-17
 * @Desc: 描述信息
 **/
@RestController
@RequestMapping
public class LoginController {
  @GetMapping("/success")
  public String success(){
    return "/success";
  }
}