package com.demo.destranger.tools;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.demo.destranger.data.User;

public class UserHelper {

	public static boolean exist(User user)
	{
		String queryStr = "select count(*) as rowCount from user where username = '" + user.getUsername() + "'";
		DBOperator dbOperator = new DBOperator();
		ResultSet rs = dbOperator.query(queryStr);
		try {
			rs.next();
			if(rs.getInt("rowCount") > 0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean add(User user)
	{
		String insertStr = "insert into user(username,password,head,gender) values ('" + user.getUsername() + "','" 
				+ user.getPassword() + "','" + user.getHead() + "'," + user.getGender() + ")";
		DBOperator dbOperator = new DBOperator();
		return dbOperator.insert(insertStr);
	}
}
