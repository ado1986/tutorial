package org.ado.rabbitmq.tutorial.workqueue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connect = null;
		Channel channel = null;
		try {
			connect = factory.newConnection();
			channel = connect.createChannel();
			channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
			String message = getMessage(args);
			channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
					message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + message + "'");
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		} finally {
			try {
				if (channel != null)
					channel.close();
				if (connect != null)
					connect.close();
			} catch (IOException | TimeoutException e) {
				e.printStackTrace();
			}
		}
	}

	private static String getMessage(String[] strings) {
		if (strings.length < 1)
			return "Hello World!";
		return joinStrings(strings, " ");
	}

	private static String joinStrings(String[] strings, String delimiter) {
		int length = strings.length;
		if (length == 0)
			return "";
		StringBuilder words = new StringBuilder(strings[0]);
		for (int i = 1; i < length; i++) {
			words.append(delimiter).append(strings[i]);
		}

		return words.toString();
	}

}
