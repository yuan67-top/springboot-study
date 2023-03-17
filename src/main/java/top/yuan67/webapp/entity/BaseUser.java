package top.yuan67.webapp.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-3-15
 * @Desc: 描述信息
 **/
public class BaseUser implements UserDetails {
  
  private String id;
  private String username;
  private String password;
  private String name;
  private String type;
  private String create_time;
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  @Override
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  
  @Override
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getType() {
    return type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public String getCreate_time() {
    return create_time;
  }
  
  public void setCreate_time(String create_time) {
    this.create_time = create_time;
  }
  
  
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  
  @Override
  public boolean isEnabled() {
    return true;
  }
  
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }
}
