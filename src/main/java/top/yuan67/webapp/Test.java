package top.yuan67.webapp;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-5-26
 * @Desc: 测试
 **/
public class Test {
  public static void main(String[] args) {
    aa();
  }
  
  static void aa(){
    String filePath = "F:/Desktop/test (2).html";
    //读取.html文件为字符串
    String htmlStr = toHtmlString(new File(filePath));
    //解析字符串为Document对象
    Document doc = Jsoup.parse(htmlStr);
    //获取body元素，获取class="fc"的table元素
    doc.charset(StandardCharsets.UTF_8);
    List<A> aList = new LinkedList<>();
    Elements alls = doc.body().getElementsByClass("main-content-margin");
    for(Element all : alls){
      Elements tables = all.getElementsByTag("table");
      Elements initials = all.getElementsByClass("title-text");
//      String initial = null;
//      for(Element element : initials){
//        initial = element.text();
//      }
      
//      for(Element table : tables){
      for(int i = 0; i < tables.size(); i++){
        Element table = tables.get(i);
        Elements ths = table.getElementsByTag("th");
        Elements tds = table.getElementsByTag("td");
        
        for(int j = 0; j < ths.size(); j++){
          A a = new A();
          a.setInitial(initials.get(i).text());
          a.setYin(ths.get(j).text());
          a.setKey(tds.get(j).text());
          aList.add(a);
        }
      }
    }
//    String sql = "INSERT INTO variant_character (`id`, `key`, `val`, `initial`, `yin`) VALUES ";
    String sql = "INSERT INTO variant_character (\"id\", \"key\", \"val\", \"initial\", \"yin\") VALUES ";
    int num=0;
    for(A a : aList){
      try {
//        A a = aList.get(i);
        String aa = a.getKey();
        String[] paras = aa.replaceAll("〕", "〔").split("〔");
        JSONArray wordArr = new JSONArray();
        for(int j=0;j<paras.length;j=j+2){
          JSONObject wordObj = new JSONObject();
          wordObj.put("key",paras[j]);
          wordObj.put("initial",a.getInitial());
          wordObj.put("yin",a.getYin());
          JSONArray val = new JSONArray();
          val.add(paras[j]);
          String para = paras[j + 1];
          char[] chars = para.toCharArray();
          for (int k = 0; k < chars.length; k++) {
            val.add(String.valueOf(chars[k]));
          }
          wordObj.put("val",val);
          wordArr.add(wordObj);
          num++;
//          sql+=("("+num+",\""+paras[j].trim()+"\",'"+(val.toJSONString().replaceAll(" ", ""))+"',\""+a.getInitial()+
//              "\"," +
//              "\""+a.getYin()+
//              "\"),");
  
          sql+=("("+num+",'"+paras[j].trim()+"','"+(val.toJSONString().replaceAll(" ", ""))+"'," +
              "'"+a.getInitial()+
              "'," +
              "'"+a.getYin()+
              "'),");
        }
      }catch (Exception e){
      
      }
    }
    System.out.println(sql);
  }
  
  public static String toHtmlString(File file) {
    // 获取HTML文件流
    StringBuffer htmlSb = new StringBuffer();
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(
          new FileInputStream(file), "UTF-8"));
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

class A{
  private String initial;
  private String yin;
  private String key;
  private String val;
  
  public String getInitial() {
    return initial;
  }
  
  public void setInitial(String initial) {
    this.initial = initial;
  }
  
  public String getYin() {
    return yin;
  }
  
  public void setYin(String yin) {
    this.yin = yin;
  }
  
  public String getKey() {
    return key;
  }
  
  public void setKey(String key) {
    this.key = key;
  }
  
  public String getVal() {
    return val;
  }
  
  @Override
  public String toString() {
    return new StringBuilder("\"A\":{")
        .append("initial:\"").append(initial).append("\",")
        .append("yin:\"").append(yin).append("\",")
        .append("key:\"").append(key).append("\",")
        .append("val:\"").append(val).append("\",")
        .append('}').toString();
  }
  
  public void setVal(String val) {
    this.val = val;
  }
  
  //  @Override
//  public String toString() {
//    String sb = new StringBuilder("{\"initial\":\"").append(initial)
//        .append("\",\"").append("yin\":\"").append(yin).append("\",\"key\":\"")
//        .append(key)//.append("\",\"").append(val).append("\":\"").append(val)
//        .append("\"}")
//        .toString();
//    return sb;
//  }
}