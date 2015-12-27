package org.ado.log4j2.tutorial;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;

public class Slf4jAdapterTutorial {

	public static void main(String[] args) {
		try {
			// zk默认使用的是slf4j+log4j，但是目前项目默认使用的是log4j2。也就是说，目前项目中，既有log4j的实现，也有log4j2的实现。
			// slf4j会通过ClassLoader获取org.slf4j.impl.StaticLoggerBinder类的个数，如果有多个会在日志中报告出来。
			// 类加载器会随机选一个logger对象，本案例中使用的是log4j2。
			ZooKeeper zk = new ZooKeeper("localhost", 60, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
