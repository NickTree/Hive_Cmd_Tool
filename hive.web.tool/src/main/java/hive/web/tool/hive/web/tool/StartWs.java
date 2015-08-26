package hive.web.tool.hive.web.tool;

import hive.web.tool.hive.web.handler.FileHandler;
import hive.web.tool.hive.web.handler.WebPagerHandler;
import hive.web.tool.hive.web.handler.WsWindowsServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import tool.sql.config.Config;


public class StartWs {
	public static void main(String[] args) throws Exception {
		Server server = new Server(Config.port);
		ServletContextHandler context = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		context.addServlet(new ServletHolder(new WsWindowsServlet()),
				"/web");
		context.addServlet(new ServletHolder(new FileHandler()), "/download");
		context.addServlet(new ServletHolder(new WebPagerHandler()), "/index");
		server.start();
		server.join();
	}
}
