package org.ado.rabbitmq.tutorial.routing.persistent;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * 队列持久化后，发布的消息才不会丢失。交换机只负责分发消息，不会持久化消息。
 * 
 * @author ado1986
 *
 */
public class ReceiveLogsDirectPersistent2 {
	private static final String EXCHANGE_NAME = "persistent_direct_logs";
	private static final String QUEUE_NAME = "persistent_queue_logs_2";

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		// 交换机持久化
		channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
		// 队列持久化
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);

		if (args.length < 1) {
			System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
			System.exit(1);
		}

		for (String severity : args) {
			channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, severity);
		}
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}

}
