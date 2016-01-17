package org.ado.httpclient.tutorial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 简单的HttpClient案例，保证底层的连接释放
 * 
 * @author ado1986
 *
 */
public class HttpClientTutorial {

	/**
	 * 发起GET请求
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

	/**
	 * 发起POST请求
	 * 
	 * @param httpClient
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static void doPost(CloseableHttpClient httpClient) throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost("http://openapi.tencentyun.com/v3/user/get_info");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("openid", "B624064BA065E01CB73F835017FE96FA"));
		nvps.add(new BasicNameValuePair("openkey", "5F154D7D2751AEDC8527269006F290F70297B7E54667536C"));
		nvps.add(new BasicNameValuePair("appid", "2"));
		nvps.add(new BasicNameValuePair("sig", "9999b41ad0b688530bb1b21c5957391c"));
		nvps.add(new BasicNameValuePair("pf", "qzone"));
		nvps.add(new BasicNameValuePair("format", "json"));
		nvps.add(new BasicNameValuePair("userip", "112.90.139.30"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpClient.execute(httpPost);
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

	public static void main(String[] args) {
		// 全局使用同一个HttpClient实例
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			System.out.println("-----------------doGet() begin--------------");
			doGet(httpClient);
			System.out.println("-----------------doGet() end--------------");
			System.out.println("-----------------doPost() begin--------------");
			doPost(httpClient);
			System.out.println("-----------------doPost() end--------------");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
