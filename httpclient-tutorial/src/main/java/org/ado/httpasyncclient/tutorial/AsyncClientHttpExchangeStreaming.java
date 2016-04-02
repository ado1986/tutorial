package org.ado.httpasyncclient.tutorial;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.concurrent.Future;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.protocol.HttpContext;

/**
 * This example demonstrates an asynchronous HTTP request / response exchange
 * with a full content streaming.
 */
public class AsyncClientHttpExchangeStreaming {

	public static void main(String[] args) throws Exception {
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		try {
			httpclient.start();
			Future<Boolean> future = httpclient.execute(HttpAsyncMethods.createGet("http://www.baidu.com"),
					new MyResponseConsumer(), null);
			Boolean result = future.get();
			if (result != null && result.booleanValue()) {
				System.out.println("Request successfully executed");
			} else {
				System.out.println("Request successfully executed");
			}
			System.out.println("Shutting down");
		} finally {
			httpclient.close();
		}
		System.out.println("Done");
	}

	static class MyResponseConsumer extends AsyncCharConsumer<Boolean> {

		@Override
		protected void onCharReceived(CharBuffer buf, IOControl ioctrl) throws IOException {
			while (buf.hasRemaining()) {
				System.out.print(buf.get());
			}
		}

		@Override
		protected Boolean buildResult(HttpContext arg0) throws Exception {
			return Boolean.TRUE;
		}

		@Override
		protected void onResponseReceived(HttpResponse arg0) throws HttpException, IOException {
		}

	}

}
