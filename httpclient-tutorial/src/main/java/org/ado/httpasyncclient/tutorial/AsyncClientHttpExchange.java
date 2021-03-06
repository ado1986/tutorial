package org.ado.httpasyncclient.tutorial;

import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

/**
 * This example demonstrates a basic asynchronous HTTP request / response exchange.
 * Response content is buffered in memory for simplicity.
 */
public class AsyncClientHttpExchange {

	public static void main(String[] args) throws Exception {
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();

		try {
			httpclient.start();
			HttpGet request = new HttpGet("http://www.apache.org/");
			Future<HttpResponse> future = httpclient.execute(request, null);
			HttpResponse response = future.get();
			System.out.println("Response: " + response.getStatusLine());
			System.out.println("Shutting down");
		} finally {
			httpclient.close();
		}
		System.out.println("Done");
	}

}
