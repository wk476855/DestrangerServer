package com.demo.destranger.tools;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBOperator {

	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String URL_SERVER = "jdbc:mysql://localhost:3306/destranger";
	public static final String USER = "root";
	public static final String PASSWORD = "123456";
	
	public Connection getConnection()
	{
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL_SERVER);
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			System.out.println("driver wrong!!");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return con;
	}

}
