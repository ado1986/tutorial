package org.ado.nio.tutorial.reactor.mainsub;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TCPReactor implements Runnable {

	private final ServerSocketChannel ssc;
	private final Selector selector; // mainReactor用的selector

	public TCPReactor(int port) throws IOException {
		selector = Selector.open();
		ssc = ServerSocketChannel.open();

		InetSocketAddress addr = new InetSocketAddress(port);
		ssc.socket().bind(addr); // 在ServerSocketChannel绑定监听端口
		ssc.configureBlocking(false); // 设置ServerSocketChannel为非阻塞
		SelectionKey sk = ssc.register(selector, SelectionKey.OP_ACCEPT); // ServerSocketChannel向selector注册一个OP_ACCEPT事件，然后返回该通道的key
		sk.attach(new Acceptor(ssc));
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			System.out.println("mainReactor waiting for new event on port: " + ssc.socket().getLocalPort() + "...");
			try {
				if (selector.select() == 0) // 若没有时间就绪则不往下执行
					continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
			Set<SelectionKey> selectionKeys = selector.selectedKeys(); // 取得所有已就绪时间的key集合
			Iterator<SelectionKey> it = selectionKeys.iterator();
			while (it.hasNext()) {
				dispatch((SelectionKey) it.next());
				it.remove();
			}
		}
	}

	private void dispatch(SelectionKey key) {
		Runnable r = (Runnable) key.attachment();
		if (r != null) {
			r.run();
		}
	}

}
