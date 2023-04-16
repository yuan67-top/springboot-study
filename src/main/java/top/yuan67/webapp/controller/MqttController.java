package top.yuan67.webapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import top.yuan67.webapp.configuration.MyMQTTClient;
import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-4-15
 * @Desc: 控制器
 **/
@RestController
@RequestMapping("/sun/mqtt")
public class MqttController {
  
  @Resource
  private MyMQTTClient myMQTTClient;
  
  @Value("${mqtt.topic1}")
  String topic1;
  
  Queue<String> msgQueue = new LinkedList<>();
  
  @GetMapping("/getMsg")
  public String mqttMsg(String type) {
    msgQueue.offer(type);
    myMQTTClient.publish(msgQueue.poll(), topic1);
    if("1".equals(type)){
      return "关闭LED";
    }
    return "打开LED";
  }
}
