package org.ado.nio.tutorial.mainsubreactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

public class WorkState implements HandlerState {

	@Override
	public void changeState(TCPHandler h) {
		h.setState(new WriteState());
	}

	@Override
	public void handle(TCPHandler h, SelectionKey sk, SocketChannel sc, ThreadPoolExecutor pool) throws IOException {
	}

}
