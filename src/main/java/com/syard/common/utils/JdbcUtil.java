package com.syard.common.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * @ClassName:JdbcUtil
 * @Decription: 从连接池中获取连接，释放资源等
 * @version: 1.0  
 */
public class JdbcUtil {
	private static final Logger log = LoggerFactory.getLogger(JdbcUtil.class);
	private static ApplicationContext context;
	public static Connection getConnectionFromPool(){
		try {
			DataSource dataSource = (DataSource) context.getBean("dataSource");
			Connection conn = dataSource.getConnection();
			return conn;
		} catch (SQLException e) {
			log.error("获取连接失败异常 ：" + e.getMessage());
		}
		return null;
	}
	
	public static void release(Object object) {
		if(object == null){
			return;
		}
		if(object instanceof ResultSet){
			try {
				((ResultSet)object).close();
			} catch (SQLException e) {
				log.error("数据库访问异常 ： " + e.getMessage());
			}
		} else if(object instanceof PreparedStatement){
			try {
				((PreparedStatement)object).close();
			} catch (SQLException e) {
				log.error("数据库访问异常 ： " + e.getMessage());
			}
		} else if(object instanceof Connection){
			try {
				((Connection)object).close();
			} catch (SQLException e) {
				log.error("数据库访问异常 ： " + e.getMessage());
			}
		}
	}
}
