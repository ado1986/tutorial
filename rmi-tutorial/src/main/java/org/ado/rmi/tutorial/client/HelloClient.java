package org.ado.rmi.tutorial.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.ado.rmi.tutorial.Hello;

/**
 * 服务调用客户端
 * 
 * @author ado1986
 *
 */
public class HelloClient {

	public static void main(String[] args) {
		// rmi服务
		String url = "rmi://127.0.0.1/Hello";
		Hello hello;
		try {
			// 查找rmi服务
			hello = (Hello) Naming.lookup(url);
			System.out.println(hello.sayHello("world."));
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

}
