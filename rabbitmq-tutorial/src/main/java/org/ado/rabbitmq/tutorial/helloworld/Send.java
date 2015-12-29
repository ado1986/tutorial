package org.ado.rabbitmq.tutorial.helloworld;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

	private static final String QUEUE_NAME = "hello";

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = null;
		Channel channel = null;
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			// 声明队列
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = "Hello World!";
			// 发布消息
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		} finally {
			try {
				if (channel != null)
					channel.close();
				if (connection != null)
					connection.close();
			} catch (IOException | TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
