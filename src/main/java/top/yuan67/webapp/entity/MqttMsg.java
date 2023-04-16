package top.yuan67.webapp.entity;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-4-15
 * @Desc: 消息实体类
 **/
public class MqttMsg {
  private String name = "";
  private String content = "";
  private String time = "";
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getContent() {
    return content;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public String getTime() {
    return time;
  }
  
  public void setTime(String time) {
    this.time = time;
  }
  
  @Override
  public String toString() {
    return "MqttMsg{" +
        "name='" + name + '\'' +
        ", content='" + content + '\'' +
        ", time='" + time + '\'' +
        '}';
  }
}