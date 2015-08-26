/*
 * @(#) Config.java 2014-4-24
 * 
 * Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package tool.sql.config;


/**
 * 类Config
 *
 * @author hzwenweijiang
 * @version 2014-4-24
 */
public class Config {
    public static final String password = "admin";
    public static final String bash_path = "/home/da/hive/bin/hive ";
	//private static final String bash_path = "/usr/local/bin/mysql ";
    public static final String base_set = "set mapred.job.name=hiveTools;";
    public static final String file_path = "/tmp/";
	public static final String file_head = "h_export_";
	public static final int max_connect = 5;
	public static final long  max_connect_time_out = 5*60*1000;
	public static final int port =8089;
	
	public static final String[] bad_keywords = { "update", "create", "delete",
		"drop", "insert" };

	public static final String[] allows = { "haitao", "default", "trace_da_etl"};
}
