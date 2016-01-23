package org.ado.jetty.tutorial;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

/**
 * 使用Jetty和mave-assembly结合，部署web应用
 * 
 * 不能用于本地调试
 * 
 * @author ado1986
 *
 * @create_time 2016年1月20日 上午9:57:17
 */
public class JettyWebDeploySample {
	public static void main(String[] args) throws Exception {
		Resource server_xml = Resource.newSystemResource("jetty-deploy.xml");
		XmlConfiguration configuration = new XmlConfiguration(server_xml.getInputStream());
		Server server = (Server) configuration.configure();

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setWar("config");
		server.setHandler(webapp);

		server.start();
		server.join();
	}
}
