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
	
	public static HTTPResponse ok(String message) {return new HTTPResponse(200, message);	}
	public static HTTPResponse ok(Object data) {
		return new HTTPResponse(200, data);
	}	
	public static HTTPResponse ok(String message,Object data) {
		return new HTTPResponse(200, message, data);
	}
	public static HTTPResponse ok(Integer status, String message,Object data) {
		return new HTTPResponse(status, message, data);
	}

	public static HTTPResponse error(String message) {
		return new HTTPResponse(500, message);
	}
	public static HTTPResponse error(Object data) {
		return new HTTPResponse(500, data);
	}	
	public static HTTPResponse error(String message,Object data) {
		return new HTTPResponse(500, message, data);
	}
  public static HTTPResponse error(Integer status, String message,Object data) {
    return new HTTPResponse(status, message, data);
  }
  public static HTTPResponse error(Integer status, String message) {
    return new HTTPResponse(status, message);
  }
	
	protected HTTPResponse() {super();}
	private HTTPResponse(Integer status, String message) {
		this.status = status;
		this.message = message;
	}	
	private HTTPResponse(Integer status, Object data) {
		this.status = status;
		this.data = data;
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