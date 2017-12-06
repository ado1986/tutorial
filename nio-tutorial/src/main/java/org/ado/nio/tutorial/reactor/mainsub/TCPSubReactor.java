package org.ado.nio.tutorial.reactor.mainsub;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TCPSubReactor implements Runnable {

	private final ServerSocketChannel ssc;
	private final Selector selector;
	private boolean restart = false;
	int num;

	public TCPSubReactor(Selector selector, ServerSocketChannel ssc, int num) {
		this.ssc = ssc;
		this.selector = selector;
		this.num = num;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			System.out.println("waiting for restart");
			while (!Thread.interrupted() && !restart) {
				try {
					if (selector.select() == 0)
						continue;
				} catch (IOException e) {
					e.printStackTrace();
				}
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				while (it.hasNext()) {
					dispatch(it.next());
					it.remove();
				}
			}
		}
	}

	private void dispatch(SelectionKey key) {
		Runnable r = (Runnable) key.attachment();
		if (r != null)
			r.run();
	}

	public void setRestart(boolean restart) {
		this.restart = restart;
	}

}
