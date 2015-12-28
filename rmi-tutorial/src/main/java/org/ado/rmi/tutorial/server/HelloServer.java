package org.ado.rmi.tutorial.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import org.ado.rmi.tutorial.Hello;
import org.ado.rmi.tutorial.HelloImpl;

/**
 * 注册服务，运行服务
 * 
 * @author ado1986
 *
 */
public class HelloServer {
	public static void main(String[] args) {
		try {
			// 启动RMI注册服务，指定端口为1099 （1099为默认端口）
			// 也可以通过命令 ＄java_home/bin/rmiregistry 1099启动
			// 这里用这种方式避免了再打开一个DOS窗口
			// 而且用命令rmiregistry启动注册服务还必须事先用RMIC生成一个占位程序(stub类)为它所用
			LocateRegistry.createRegistry(1099);
			// 创建远程对象的一个或多个实例，下面是hello对象
			// 可以用不同名字注册不同的实例
			Hello hello = new HelloImpl();
			// 把hello注册到RMI注册服务器上，命名为Hello
			Naming.bind("Hello", hello);
			// 如果要把hello实例注册到另一台启动了RMI注册服务的机器上
			// Naming.rebind("//192.168.1.105:1099/Hello",hello);

			System.out.println("Hello Server is ready.");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}
}
