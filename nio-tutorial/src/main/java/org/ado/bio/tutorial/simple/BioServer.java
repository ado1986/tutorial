package org.ado.bio.tutorial.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO服务端，不考虑半包、粘包等问题，字符串以\r\n作为结尾，避免等待
 * 
 * @author ado1986
 *
 * @create_time 2016年2月2日 下午1:34:05
 */
public class BioServer {
	public static void main(String[] args) {
		try {
			// 监听端口8080
			ServerSocket serverSocket = new ServerSocket(8080);
			System.out.println("-----服务器启动-----");
			while (true) {
				// 等待连接
				Socket socket = null;
				OutputStream output = null;
				BufferedReader inputReader = null;
				try {
					serverSocket.accept();
					InputStream input = socket.getInputStream();
					inputReader = new BufferedReader(new InputStreamReader(input));
					// 输出请求内容
					System.out.println(inputReader.readLine());

					// 输出应答内容
					output = socket.getOutputStream();
					output.write("Hello World\r\n".getBytes("UTF-8"));
					output.flush();
				} finally {
					if (output != null)
						output.close();
					if (inputReader != null)
						inputReader.close();
					if (socket != null)
						socket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
