package tool.sql.hive.websocket.windows;

import hive.web.tool.hive.web.handler.WsWindowsServlet;
import hive.web.tool.hive.web.tool.ExeFilter;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

import tool.sql.config.Config;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class WsDo implements WebSocketListener {
	private boolean login = false;
	private Session outbound;
	private ArrayList<String> cmds = new ArrayList<String>();
	private String data_space = "";

	@Override
	public void onWebSocketBinary(byte[] payload, int offset, int len) {
		System.out.println("socket onWebSocketBinary. status code:");
	}

	@Override
	public void onWebSocketClose(int statusCode, String reason) {
		WsWindowsServlet.sessionList.remove(outbound);
		outbound = null;
	}

	@Override
	public void onWebSocketConnect(Session session) {
		System.out.println("new connect ...");
		if (WsWindowsServlet.sessionList.size() > Config.max_connect) {
			session.getRemote().sendString("too many session! max is " + Config.max_connect, null);
			return;
		}
		this.outbound = session;
		WsWindowsServlet.sessionList.add(session);
	}

	@Override
	public void onWebSocketError(Throwable error) {
		if (outbound != null)
			WsWindowsServlet.sessionList.remove(outbound);
		error.printStackTrace();
	}

	@Override
	public void onWebSocketText(String message) {
		if ((outbound == null) || (!outbound.isOpen())) {
			this.onWebSocketError(new Throwable("session is null"));
			return;
		}

		if (verifyInterrupt(message)) {
			return;
		}
		CMDUtil cmd = new CMDUtil(message, cmds);

		if (cmd.isNeedSwitchDB()) {
			if (cmd.isAllows()) {
				data_space = message;
				println("switch to new database");
			} else {
				println("no authority for '" + message + "'");
			}
			return;
		}

		if (cmd.isCmdEnd()) {
			cmds.add(message);
			println(" ");
			return;
		}
		// Clear堆栈
		cmds.clear();

		if (cmd.isForbid()) {
			println("can not use update,create,delete,drop,insert ");
			return;
		}
		cmd.setNameSpace(data_space);
		ExeFilter.exe(outbound, cmd);

		if (cmd.isExport()) {
			println("redirect:" + cmd.getExportName());
		}
	}

	private void println(String message) {
		try {
			this.outbound.getRemote().sendString(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 是否打断会话
	 * 
	 * @param message
	 * @return
	 */
	private boolean verifyInterrupt(String message) {
		System.out.println("receive message text :" + message);

		if (Config.password.equals(message)) {
			this.login = true;
			println("-1");
			return true;
		}

		if (!this.login) {
			println("password incorrect,Please Enter Password:");
			return true;
		}

		if (message == null || "".equals(message.trim())) {
			println(" ");
			return true;
		}

		return false;
	}
}