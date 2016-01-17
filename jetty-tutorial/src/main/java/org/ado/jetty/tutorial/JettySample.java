package org.ado.jetty.tutorial;

import org.eclipse.jetty.server.Server;

public class JettySample {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		server.start();
	}
}
