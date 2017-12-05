package org.ado.nio.tutorial.mainsubreactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

public class ReadState implements HandlerState {

	private SelectionKey sk;

	public ReadState() {
	}

	@Override
	public void changeState(TCPHandler h) {
		h.setState(new WorkState());
	}

	@Override
	public void handle(TCPHandler h, SelectionKey sk, SocketChannel sc, ThreadPoolExecutor pool) throws IOException {
		this.sk = sk;
		byte[] arr = new byte[1024];
		ByteBuffer buf = ByteBuffer.wrap(arr);

		int numBytes = sc.read(buf);
		if (numBytes == -1) {
			System.out.println("[Warning!] A client has been closed.");
			h.closeChannel();
			return;
		}

		String str = new String(arr);
		if (str != null && !str.equals(" ")) {
			System.out.println(str);
			h.setState(new WorkState());
			pool.execute(new WorkerThread(h, str));
		}
	}

	synchronized void process(TCPHandler h, String str) {
		h.setState(new WriteState());
		this.sk.interestOps(SelectionKey.OP_WRITE);
		this.sk.selector().wakeup();
	}

	class WorkerThread implements Runnable {
		TCPHandler h;
		String str;

		public WorkerThread(TCPHandler h, String str) {
			this.h = h;
			this.str = str;
		}

		@Override
		public void run() {
			process(h, str);
		}

	}

}
