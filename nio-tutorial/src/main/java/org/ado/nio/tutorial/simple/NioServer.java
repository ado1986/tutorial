package org.ado.nio.tutorial.simple;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

	public static void main(String[] args) {
		try {
			ServerSocketChannel server = ServerSocketChannel.open();
			server.configureBlocking(true);
			server.socket().bind(new InetSocketAddress(8080));
			// 服务启动
			System.out.println("----server started------");

			Selector selector = Selector.open();

			Thread selectorThread = new Thread(new SelectorTask(selector));
			selectorThread.setName("--selector--");
			selectorThread.start();

			while (true) {
				SocketChannel socketChannel = server.accept();
				socketChannel.configureBlocking(false);
				if (selector != null) {
					socketChannel.register(selector, SelectionKey.OP_READ);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * selector任务
	 * 
	 * @author ado1986
	 *
	 * @create_time 2016年1月31日 下午9:00:05
	 */
	static class SelectorTask implements Runnable {
		private Selector selector;

		SelectorTask(Selector selector) {
			this.selector = selector;
		}

		@Override
		public void run() {
			try {
				while (true) {
					int readyChannels = selector.selectNow();
					if (readyChannels == 0)
						continue;
					Set selectedKeys = selector.selectedKeys();
					Iterator keyIterator = selectedKeys.iterator();
					while (keyIterator.hasNext()) {
						SelectionKey key = (SelectionKey) keyIterator.next();
						if (key.isAcceptable()) {
						} else if (key.isConnectable()) {
						} else if (key.isReadable()) {
							SocketChannel socketChannel = (SocketChannel) key.channel();
							ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
							socketChannel.read(byteBuffer);
							byteBuffer.flip();
							String receivedString = Charset.forName("UTF-8").newDecoder().decode(byteBuffer).toString();
							// 输出客户端请求消息
							System.out.println(receivedString);

							// 关闭socket，避免一直读取该socket里的内容
							socketChannel.close();
						}
						keyIterator.remove();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
