package hive.web.tool.hive.web.handler;

import java.util.ArrayList;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import tool.sql.config.Config;
import tool.sql.hive.websocket.windows.WsDo;

public class WsWindowsServlet extends WebSocketServlet {  
		public static ArrayList<Session> sessionList = new ArrayList<Session>();
		
	    private static final long serialVersionUID = -2964802839253009970L;  
	  
	    @Override  
	    public void configure(WebSocketServletFactory factory) {  
	    	factory.getPolicy().setIdleTimeout(Config.max_connect_time_out);
	        factory.register(WsDo.class);  
	    }  
	
}
