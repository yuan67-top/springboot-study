/**
 * @CreateTime:2022年9月15日下午11:12:33;
 * @Author:NieQiang;
 * @QQ:2548841623;
 * @Email:2548841623@qq.com;
 **/
package top.yuan67.webapp.util;

public class HTTPResponse {
	private Integer status;
	private String message;
	private Object data;
  
  /**
   * 返回<b>固定状态码200</b>和<b>操作成功</b>提示文字
   * @return
   */
	public static HTTPResponse ok() {
    return new HTTPResponse(200, MessageUtil.get("操作成功"));
  }
  
  /**
   * 推荐使用
   * 返回<b>固定状态码200</b>和<b>操作成功</b>提示文字以及<b>[自定义对象数据]</b>
   * @param data
   * @return
   */
	public static HTTPResponse ok(Object data) {
		return new HTTPResponse(200, MessageUtil.get("操作成功"), data);
	}
  
  /**
   * 返回<b>固定状态码200</b>和<b>自定义提示文字</b>
   * @param message
   * @return
   */
  public static HTTPResponse ok(String message) {
    return new HTTPResponse(200, MessageUtil.get(message));
  }
  
  /**
   * 返回<b>固定状态码200</b>和<b>自定义提示文字</b>以及<b>[自定义对象数据]</b>
   * @param message
   * @param data
   * @return
   */
  public static HTTPResponse ok(String message, Object data) {
    return new HTTPResponse(200, MessageUtil.get(message), data);
  }
  
  /**
   * 返回<b>自定义状态码</b>和<b>提示文字</b>以及<b>[对象数据]</b>均自定义
   * @param message
   * @param data
   * @return
   */
	public static HTTPResponse ok(Integer status, String message, Object data) {
		return new HTTPResponse(status, MessageUtil.get(message), data);
	}
  
  
  /**
   * 返回<b>固定状态码500</b>和<b>操作成功</b>提示文字
   * @return
   */
  public static HTTPResponse error() {
    return new HTTPResponse(500, MessageUtil.get("操作失败"));
  }
  /**
   * 推荐使用
   * 返回<b>固定状态码500</b>和<b>操作成功</b>提示文字以及<b>[自定义对象数据]</b>
   * @param data
   * @return
   */
  public static HTTPResponse error(Object data) {
    return new HTTPResponse(500, MessageUtil.get("操作失败"), data);
  }
  
  /**
   * 返回<b>固定状态码500</b>和<b>自定义提示文字</b>
   * @param message
   * @return
   */
  public static HTTPResponse error(String message) {
    return new HTTPResponse(500, MessageUtil.get(message));
  }
  
  /**
   * 返回<b>固定状态码500</b>和<b>自定义提示文字</b>以及<b>[自定义对象数据]</b>
   * @param message
   * @param data
   * @return
   */
  public static HTTPResponse error(String message, Object data) {
    return new HTTPResponse(500, MessageUtil.get(message), data);
  }
  
  /**
   * 返回<b>自定义状态码</b>和<b>提示文字</b>以及<b>[对象数据]</b>均自定义
   * @param message
   * @param data
   * @return
   */
  public static HTTPResponse error(Integer status, String message, Object data) {
    return new HTTPResponse(status, MessageUtil.get(message), data);
  }
	
	protected HTTPResponse() {super();}
	private HTTPResponse(Integer status, String message) {
		this.status = status;
		this.message = message;
	}
 
	private HTTPResponse(Integer status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	public Integer getStatus() {return status;}
	public String getMessage() {return message;}
	public Object getData() {return data;}
	public void setStatus(Integer status) {this.status = status;}
	public void setMessage(String message) {this.message = message;}
	public void setObject(Object data) {this.data = data;}
}