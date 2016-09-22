package com.bootcamp.portal.tools;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;


public class WebRunner {
	public static void main(String[] args) throws Exception {
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setWar("web");

		Server jetty = new Server(8092);
		jetty.setHandler(webapp);
		jetty.start();
		jetty.join();
	}
}
