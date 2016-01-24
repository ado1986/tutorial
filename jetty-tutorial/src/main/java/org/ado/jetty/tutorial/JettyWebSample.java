package org.ado.jetty.tutorial;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlConfiguration;

/**
 * Jetty配置web应用的例子，采用XML配置server
 * 参考：http://wiki.eclipse.org/Jetty/Tutorial/Embedding_Jetty#Writing_Handlers
 * 
 * 此案例只适合本机调试，不能部署
 * 
 * 测试URL：http://localhost:8080/jetty/helloWorld
 * 
 * @author ado1986
 *
 * @create_time 2016年1月18日 下午6:46:39
 */
public class JettyWebSample {

	public static void main(String[] args) throws Exception {
		Resource server_xml = Resource.newSystemResource("jetty.xml");
		XmlConfiguration configuration = new XmlConfiguration(server_xml.getInputStream());
		Server server = (Server) configuration.configure();

		server.start();
		server.join();
	}

}
