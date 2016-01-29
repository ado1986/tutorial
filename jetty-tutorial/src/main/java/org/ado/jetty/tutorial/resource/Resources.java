package org.ado.jetty.tutorial.resource;

import org.springframework.stereotype.Service;

/**
 * Asynchronous Servlet测试，用资源模拟第三方调用或获取DB连接时，等待。
 * 
 * @author ado1986
 *
 * @create_time 2016年1月29日 下午7:06:26
 */
@Service
public class Resources {

	/**
	 * 获取资源
	 * 
	 * @return
	 */
	public String getResource() {
		try {
			synchronized (this) {
				// 模拟等待10s
				this.wait(10000);
			}
		} catch (Exception e) {
			// ignore
		}
		return "New Resource";
	}
}
