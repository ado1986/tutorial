package org.ado.bio.tutorial.simple;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * BIO客户端，不考虑半包、粘包等问题，字符串以\r\n作为结尾，避免等待
 * 
 * @author ado1986
 *
 * @create_time 2016年2月2日 下午1:36:45
 */
public class BioClient {
	public static void main(String[] args) {
		Socket socket = null;
		OutputStream output = null;
		BufferedReader inputReader = null;
		try {
			socket = new Socket();
			// 连接到本机8080端口
			socket.connect(new InetSocketAddress("127.0.0.1", 8080));
			output = socket.getOutputStream();
			// 发送消息
			output.write("Hello World\r\n".getBytes("UTF-8"));
			output.flush();

			InputStream input = socket.getInputStream();
			inputReader = new BufferedReader(new InputStreamReader(input));
			// 输出服务端应答内容
			System.out.println(inputReader.readLine());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null)
					output.close();
				if (inputReader != null)
					inputReader.close();
				if (socket != null)
					socket.close();
			} catch (Exception e) {
				// ignore
			}
		}
	}
}
