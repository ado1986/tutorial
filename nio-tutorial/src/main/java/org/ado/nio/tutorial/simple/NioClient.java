package org.ado.nio.tutorial.simple;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {

	public static void main(String[] args) {
		try {
			SocketChannel socketChannel = SocketChannel.open();
			socketChannel.socket().setSoTimeout(30000);
			socketChannel.configureBlocking(true);
			socketChannel.connect(new InetSocketAddress("localhost", 8080));
			// 发送消息
			ByteBuffer writeBuffer = ByteBuffer.wrap("Hello Wolrd\r\n".getBytes("UTF-8"));
			socketChannel.write(writeBuffer);
			socketChannel.socket().shutdownOutput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
