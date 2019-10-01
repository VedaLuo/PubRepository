package com.lzc.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {

	
	public static Connection getMySqlDB()
	{
		//String url="jdbc:mysql://127.0.0.1/zhxy_test?useUnicode=true&characterEncoding=UTF-8";
		//String url="jdbc:mysql://192.168.3.30/test?useUnicode=true&characterEncoding=UTF-8";	
		String url="jdbc:mysql://192.168.3.60/test?useUnicode=true&characterEncoding=UTF-8";
		String user="root";
		String pass="root";
		pass="123456";
		
		//String url="jdbc:mysql://192.168.3.80:6363/veda_app?useUnicode=true&characterEncoding=UTF-8";		
		//String user="lzc";
		//String pass="123456";
		
		Connection con = null;
        try
        {
        	//Class.forName("com.mysql.jdbc.Driver");
        	Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);
        }
		catch (Exception e) 
		{
			e.printStackTrace();
		}
        
		return con;
	}
	
	public static Connection getMySqlDB(String db)
	{
		//String url="jdbc:mysql://127.0.0.1/zhxy_test?useUnicode=true&characterEncoding=UTF-8";
		String url="jdbc:mysql://192.168.3.30/"+db+"?useUnicode=true&characterEncoding=UTF-8";
		String user="root";
		String pass="root";
		
		
		//阿里云主机
		//String url="jdbc:mysql://www.veda-test.top:6363/"+db+"?useUnicode=true&characterEncoding=UTF-8";
		//String user="lzc";
		//String pass="abcd@1234";
		
		Connection con = null;
        try
        {
        	//Class.forName("com.mysql.jdbc.Driver");
        	Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);
        }
		catch (Exception e) 
		{
			e.printStackTrace();
		}
        
		return con;
	}
}
