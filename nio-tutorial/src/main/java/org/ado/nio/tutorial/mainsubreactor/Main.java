package org.ado.nio.tutorial.mainsubreactor;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			TCPReactor reactor = new TCPReactor(1333);
			new Thread(reactor).start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
