package top.yuan67.webapp;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Set;

/**
 * @Author: zy
 * @User: Administrator
 * @CreateTime: 2023/5/26
 * @Desc: 描述信息
 **/
public class TestController {
  
  public String test1(){
    File file = new File("C:\\Users\\Administrator\\Desktop\\test (2).html");
    String string = toHtmlString(file);
    Document parse = Jsoup.parse(string);
    Element element = parse.getElementsByClass("content").get(0);
    Elements textList = element.getElementsByClass("title-text");
    Elements tableList = element.getElementsByTag("table");
    //组装json
    JSONArray jsonArray = new JSONArray();
    for(int i =0;i<tableList.size();i++){
      JSONObject jsonObject = new JSONObject();
      Element element1 = textList.get(i);
      String text = element1.text();
      jsonObject.put("text",text);
      //获取读音
      Element table = tableList.get(i);
      //获取到一行
      Elements tr = table.getElementsByTag("tr");
      for (int l =0;l<tr.size();l++){
        System.out.println(l);
        String para_mark_module;
        try {
          para_mark_module = tr.get(l).getElementsByClass("para").get(0).text();
        }catch (Exception e){
          continue;
        }
          String[] paras =
            tr.get(l).getElementsByClass("para").get(1).text().replaceAll("〕", "〔").split("〔");
        //数组
        JSONArray wordArr = new JSONArray();
        for(int j=0;j<paras.length;j=j+2){
          JSONObject wordObj = new JSONObject();
          wordObj.put("word",paras[j]);
          wordObj.put("pinyin",para_mark_module);
          JSONArray child = new JSONArray();
          String para = paras[j + 1];
          char[] chars = para.toCharArray();
          for (int k = 0; k < chars.length; k++) {
            child.add(String.valueOf(chars[k]));
          }
          wordObj.put("child",child);
          wordArr.add(wordObj);
        }
        jsonObject.put(para_mark_module,wordArr);
      }
      jsonArray.add(jsonObject);
      }
     
    System.out.println(jsonArray);
    
    JSONArray temp = new JSONArray();
    //转成另外一种数据结构
    for(int i =0;i<jsonArray.size();i++){
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      Set<String> strings = jsonObject.keySet();
      for (String key:strings) {
        if(key!="text"){
          JSONArray jsonArray1 = jsonObject.getJSONArray(key);
          for(int j =0;j<jsonArray1.size();j++){
            JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
            JSONArray child = jsonObject1.getJSONArray("child");
            if(null!=child&&child.size()>0){
             
                JSONObject data = new JSONObject();
                data.put("首字母",jsonObject.getString("text"));
                data.put("拼音",key);
                data.put("字",jsonObject1.getString("word"));
              JSONArray child1 = jsonObject1.getJSONArray("child");
              child1.add(jsonObject1.getString("word"));
              data.put("异体字",child1);
              temp.add(data);
              
            }
          }
        }
      }
    }
    System.out.println(temp);
    return "1";
  }
  
  
  /**
   *  读取本地html文件里的html代码
   * @return
   */
  public  String toHtmlString(File file) {
    // 获取HTML文件流
    StringBuffer htmlSb = new StringBuffer();
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(
          new FileInputStream(file), "utf-8"));
      while (br.ready()) {
        htmlSb.append(br.readLine());
      }
      br.close();
      // 删除临时文件
      //file.delete();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // HTML文件字符串
    String htmlStr = htmlSb.toString();
    // 返回经过清洁的html文本
    return htmlStr;
  }

}
