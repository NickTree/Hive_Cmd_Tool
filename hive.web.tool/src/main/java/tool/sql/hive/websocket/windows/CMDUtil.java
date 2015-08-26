package tool.sql.hive.websocket.windows;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tool.sql.config.Config;

public class CMDUtil {
	

	private String message;
	private String command ="";
	private boolean needSwitchDB = false;
	private boolean isCmdEnd = false;
	private boolean isForbid = false;
	private boolean isExport = false;
	private boolean isAllows = false;
	private String exportName;
	private String nameSpace;
	private ArrayList<String> f_cmd;
	
	public CMDUtil(String message,ArrayList<String> cmds){
		this.message = message.trim();
		this.f_cmd = cmds;
		init();
	}
	
	private void init(){
		composeSql();
		needExport();
		data_space();
		stack_filter();
		keyword_filter();
		db_allow_filter();
	}
	
	private void composeSql(){
		if(!f_cmd.isEmpty()){
			for(String c : f_cmd)
				command = command + " " + c;
		}
		command = command + " "+ message;
		command = command.trim();
	}
	
	/**
	 * 是否切换数据库
	 * @param cmd
	 * @return
	 */
	private void data_space() {
		Pattern pattern = Pattern.compile("^use\\s\\S+;$");
		Matcher matcher = pattern.matcher(message);
		if (matcher.matches())
			needSwitchDB = true;
	}
	/**
	 * 是否结束命令行
	 * @param cmd
	 * @return
	 */
	private void stack_filter() {
		if (!command.endsWith(";"))
			isCmdEnd  = true;
	}
	/**
	 * 禁止的操作命令
	 * @param cmd
	 * @return
	 */
	private void keyword_filter() {
		for (String bas : Config.bad_keywords) {
			if (command.toLowerCase().contains(bas))
				isForbid = true;
		}
		//双分号情况？
	}
	
	
	/**
	 * 禁止的操作命令
	 * @param cmd
	 * @return
	 */
	private void db_allow_filter() {
		for (String bas : Config.allows) {
			if (command.toLowerCase().contains(bas))
				isAllows = true;
		}
	}
	
	
	/**
	 * 获得下载文件名
	 * @param jobname
	 * @return
	 */
	private void needExport() {
		if(command.startsWith("export")){
			isExport = true;
			command = command.substring(6).trim();
			exportName = Config.file_head + System.currentTimeMillis();
		}else {
			command = message;
		}
	}

	
	public String getMessage() {
		return message;
	}

	public String getCommand() {
		return command;
	}

	public boolean isNeedSwitchDB() {
		return needSwitchDB;
	}

	public boolean isCmdEnd() {
		return isCmdEnd;
	}

	public boolean isForbid() {
		return isForbid;
	}

	public boolean isExport() {
		return isExport;
	}

	public String getExportName() {
		return exportName;
	}
	
	
	public boolean isAllows() {
		return isAllows;
	}

	@Override
	public String toString(){
		return command;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
		this.command = nameSpace + this.command;
	}
	

}
