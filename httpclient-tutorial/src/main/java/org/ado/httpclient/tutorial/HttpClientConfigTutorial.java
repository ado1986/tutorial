package org.ado.httpclient.tutorial;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient配置
 * 
 * 配置示例：
 * http://hc.apache.org/httpcomponents-client-ga/httpclient/examples/org/apache/
 * http/examples/client/ClientConfiguration.java
 * 
 * @author ado1986
 *
 */
public class HttpClientConfigTutorial {
	public static void main(String[] args) {
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(200); // 最大连接数为200
		connManager.setDefaultMaxPerRoute(200); // 每个路由最大连接数，限制连接的每个host最大连接数

		// 连接参数配置
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(2000)// 请求建立连接超过2秒钟
				.setSocketTimeout(2000) // so_timeout
				.setConnectionRequestTimeout(5000) // 从连接池中获取连接等待时间
				// 提交请求前测试连接是否可用，在性能要求较高时，不需要开启，因为最高会耗时30ms
				// .setStaleConnectionCheckEnabled(true)
				.build();
		final CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager)
				.disableAutomaticRetries()// 禁止重试
				.setDefaultRequestConfig(requestConfig).build();
		// 最大连接数为200，避免获取连接超时，只模拟200的并发访问
		ExecutorService exec = Executors.newFixedThreadPool(200);
		for (int i = 0; i < 1000; i++) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					try {
						HttpGet httpGet = new HttpGet("http://www.baidu.com");
						System.out.println("executing request" + httpGet.getURI());
						CloseableHttpResponse response = httpclient.execute(httpGet);
						try {
							HttpEntity entity = response.getEntity();
							System.out.println("-------------------------------");
							System.out.println(response.getStatusLine());
							if (entity != null) {
								System.out.println("Response content length: " + entity.getContentLength());
								System.out.println("Response content: " + EntityUtils.toString(entity));
							}
							System.out.println("-------------------------------");
						} finally {
							response.close();
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		// 任务结束后关闭线程
		exec.shutdown();
		while (!exec.isTerminated()) {
			// 等待线程终止
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			// 关闭连接池，池中连接将会关闭
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
