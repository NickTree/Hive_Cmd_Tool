package hive.web.tool.hive.web.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.api.Session;

import tool.sql.config.Config;
import tool.sql.hive.websocket.windows.CMDUtil;
import tool.sql.hive.websocket.windows.WritePip;

public class ExeFilter {

	
	public static void writeFile(HttpServletResponse response,String filename){
		//基础的安全控制
		if(filename == null || filename.contains("/") || !filename.startsWith(Config.file_head)){
			response.setStatus(403);
			return;
		}
		
		File f = new File(Config.file_path+filename);
		if( !f.exists() || !f.isFile()) {
			response.setStatus(404);
			return;
		}
		
		byte[] tempbytes = new byte[2046];
        int byteread = 0;
        FileInputStream in = null;
        try {
        	in = new FileInputStream(f);
			while ((byteread = in.read(tempbytes)) != -1) {
				response.getOutputStream().write(tempbytes,0, byteread);
			}
			f.deleteOnExit();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			try {
				in.close();
				response.getOutputStream().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获得执行语句
	 * @param cmd
	 * @return
	 */
	public static String compose(CMDUtil cmd) {
		//String command ="-h10.240.138.230 -uroot -p5678 -e\""+cmd.getCommand() + "\"";
		String command = "-e\"" + Config.base_set +  cmd.getCommand() + "\"";
		if (cmd.isExport()) {
			command = command + "> " + Config.file_path + cmd.getExportName();
		}
		System.out.println(command);
		return command;
	}
	/**
	 * 执行SQL
	 * @param outbound
	 * @param cmd
	 */
	public static void exe(Session outbound, CMDUtil cmd) {
		Runtime r = Runtime.getRuntime();
		Process p0 = null;
		try {
			p0 = r.exec(new String[] { "/bin/sh", "-c", Config.bash_path + compose(cmd) });

			WritePip w1 = new WritePip(outbound, new BufferedReader(
					new InputStreamReader(p0.getErrorStream())));
			WritePip w2 = new WritePip(outbound, new BufferedReader(
					new InputStreamReader(p0.getInputStream(), "utf-8")));

			w1.start();
			w2.start();
			p0.waitFor();
			while (true) {
				if (w1.isFinsh() && w2.isFinsh())
					break;
				Thread.sleep(1000);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			p0.destroy();
		}
	}

}
