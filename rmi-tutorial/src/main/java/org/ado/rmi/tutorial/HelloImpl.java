package org.ado.rmi.tutorial;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements Hello {

	public HelloImpl() throws RemoteException {
		super();
	}

	@Override
	public String sayHello(String name) throws RemoteException {
		System.out.println("我在RMI的服务器端，客户端正在调用'sayHello'方法。 ");
		System.out.println("Hello  " + name);
		return "Hello  " + name;
	}

}
