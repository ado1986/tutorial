package org.ado.httpclient.tutorial;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient连接池
 * 
 * @author ado1986
 *
 */
public class HttpClientConnectManagerTotorial {

	public static void main(String[] args) {
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(1); // 最大连接数为1
		connManager.setDefaultMaxPerRoute(1); // 每个路由最大连接数，限制连接的每个host最大连接数

		final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager)
//				 .disableAutomaticRetries()// 禁止重试
				.build();
		ExecutorService exec = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 2; i++)
			exec.execute(new Runnable() {
				@Override
				public void run() {
					try {
						// 发起GET方法
						doGet(httpClient);
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		exec.shutdown();
		// 线程关闭
		while (!exec.isTerminated()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 关闭连接,释放资源
		try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * GET方法
	 * 
	 * @param httpClient
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static void doGet(CloseableHttpClient httpClient) throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet("http://httpbin.org/get");
		System.out.println("executing request" + httpGet.getURI());
		CloseableHttpResponse response = httpClient.execute(httpGet);
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
	}

}
