package com.demo.destranger.tools;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.demo.destranger.data.*;

public class UserDBOperator extends DBOperator {

	public Connection con = null;
	public Statement statement = null;
	public String phase = null;
	public UserDBOperator()
	{
		con = getConnection();
	}
	
	public int insert(User user)
	{
		try {
			if(query(user) == 1)
			{
				System.out.println("user exists");
				return 0;
			}
			else {
				statement = con.createStatement();
				phase = "insert into user(username,password,head,gender) values ('" + user.getUsername() + ",'" + user.getPassword()
						+ ",'" + user.getHead() + ", " + user.getGender() + "";
				int result = statement.executeUpdate(phase);
				return result;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	
	public int query(User user)
	{
		int result = -1;
		try {
			statement = con.createStatement();
			phase = "selet * from user where username = '" + user.getUsername() + "'";
			result = statement.executeUpdate(phase);
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
