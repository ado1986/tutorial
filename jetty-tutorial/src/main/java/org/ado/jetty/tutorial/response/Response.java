package org.ado.jetty.tutorial.response;

/**
 * 返回应答
 * 
 * @author ado1986
 *
 * @create_time 2016年1月20日 上午10:41:19
 */
public class Response {

	private int status;
	private String message;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
