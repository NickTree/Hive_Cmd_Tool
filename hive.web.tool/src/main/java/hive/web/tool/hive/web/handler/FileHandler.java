package hive.web.tool.hive.web.handler;

import hive.web.tool.hive.web.tool.ExeFilter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class FileHandler extends HttpServlet{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
		String filename = request.getParameter("name");
		response.setHeader("content-disposition", "attachment;filename=" + filename);  
		System.out.println(filename);
		ExeFilter.writeFile(response, filename);
    }  
	

}