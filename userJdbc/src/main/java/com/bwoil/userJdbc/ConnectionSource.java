package com.bbbbb.userJdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * 
 * @date 2017年12月15日 下午3:40:11
 */
public class ConnectionSource {

	private static BasicDataSource dataSource=null;
	public static void init(){
		String driverClassName="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://127.0.0.1:3306/student";
		String username="root";
		String password="root";
		String initialSize="1";
		String minIdle="20";
		String maxIdle="5";
		String maxWait="50";
		String maxActive="10";
		
		dataSource =new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		if(initialSize!=null){
			dataSource.setInitialSize(Integer.parseInt(initialSize));
		}
		if(minIdle!=null){
			dataSource.setMinIdle(Integer.parseInt(minIdle));
		}
		if(maxIdle!=null){
			dataSource.setMaxIdle(Integer.parseInt(maxIdle));
		}
		if(maxWait!=null){
			dataSource.setMaxWait(Long.parseLong(maxWait));
		}
		if(maxActive!=null){
			if(!maxActive.trim().equals("0")){
				dataSource.setMaxActive(Integer.parseInt(maxActive));
			}
			
		}
		
	}
	
	
	public static synchronized Connection getConnection() throws SQLException{
		if(dataSource==null){
			init();
		}
		Connection conn= null;
		if(dataSource!=null){
			conn=dataSource.getConnection();
		}
		return conn;
	}
}
