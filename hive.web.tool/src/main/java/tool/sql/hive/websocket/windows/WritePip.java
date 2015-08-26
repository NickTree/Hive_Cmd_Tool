package tool.sql.hive.websocket.windows;

import java.io.BufferedReader;
import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;

public class WritePip extends Thread {
	private Session outbound;
	private BufferedReader input;
	private boolean finsh = false;
	public WritePip(Session outbound, BufferedReader input) {
		this.outbound = outbound;
		this.input = input;
	}

	@Override
	public void run() {
		String line;
		try {
			while ((line = input.readLine()) != null) {
				System.out.println(line);
				outbound.getRemote().sendString(line);
				outbound.getRemote().flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.finsh = true;
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean isFinsh() {
		return finsh;
	}


}
