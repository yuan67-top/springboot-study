package top.yuan67.webapp.entity;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-2-17
 * @Desc: 描述信息
 **/
public class User {
  private Integer id;
  private String name;
  private Integer age;
  
  public Integer getId() {
    return id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public Integer getAge() {
    return age;
  }
  
  public void setAge(Integer age) {
    this.age = age;
  }
}
