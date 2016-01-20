package org.ado.jetty.tutorial;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * 简单的jetty例子
 * 
 * @author ado1986
 *
 * @create_time 2016年1月18日 下午6:31:55
 */
public class JettySample extends AbstractHandler {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		server.setHandler(new JettySample());
		server.start();

		server.join();
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		response.getWriter().println("<h1>Hello World</h1>");
	}
}
