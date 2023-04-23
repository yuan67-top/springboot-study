package top.yuan67.webapp.vo;

import com.alibaba.fastjson2.JSON;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-4-23
 * @Desc: 登录用户实体类
 **/
public class LoginAdminVO {
  String username;
  String password;
  String code;
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getCode() {
    return code;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
}
