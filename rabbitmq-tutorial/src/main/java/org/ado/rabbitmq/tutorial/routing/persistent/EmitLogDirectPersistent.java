package org.ado.rabbitmq.tutorial.routing.persistent;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 
 * @author ado1986
 *
 */
public class EmitLogDirectPersistent {
	private static final String EXCHANGE_NAME = "persistent_direct_logs";

	public static void main(String[] args) throws IOException, TimeoutException {
		for (int i = 0; i < 1000; i++) {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("127.0.0.1");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();

			// 交换机持久化
			channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);

			String severity = getSeverity(args);
			String message = getMessage(args);

			// 消息持久化
			channel.basicPublish(EXCHANGE_NAME, severity, MessageProperties.PERSISTENT_TEXT_PLAIN,
					message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + severity + "':'" + message + "'");

			channel.close();
			connection.close();
		}
	}

	private static String getSeverity(String[] strings) {
		if (strings.length < 1)
			return "info";
		return strings[0];
	}

	private static String getMessage(String[] strings) {
		if (strings.length < 2)
			return "Hello World!";
		return joinString(strings, " ", 1);
	}

	private static String joinString(String[] strings, String delimiter, int startIndex) {
		int length = strings.length;
		if (length == 0)
			return "";
		if (length < startIndex)
			return "";
		StringBuilder words = new StringBuilder(strings[startIndex]);
		for (int i = startIndex + 1; i < length; i++)
			words.append(delimiter).append(strings[i]);

		return words.toString();
	}

}
