package com.demo.destranger.tools;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.demo.destranger.data.MD5Util;
import com.demo.destranger.data.UserInfo;

public class UserHelper {

	public static boolean exist(UserInfo user)
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
	
	public static boolean add(UserInfo user)
	{
		String insertStr = "insert into user(username,password,head,gender) values ('" + user.getUsername() + "','" 
				+ user.getPassword() + "','" + user.getHead() + "'," + user.getGender() + ")";
		DBOperator dbOperator = new DBOperator();
		return dbOperator.insert(insertStr);
	}
	
	public static boolean login(UserInfo user)
	{
		String loginStr = "select * from user where username = '" + user.getUsername() + "' and password = '" + user.getPassword() + "'";
		DBOperator dbOperator = new DBOperator();
		ResultSet rs = dbOperator.query(loginStr);
		try 
		{
			if(rs.next())
			{
				user.setUid(rs.getInt("uid"));
				user.setHead(rs.getString("head"));
				user.setGender(rs.getInt("gender"));
				user.setSession(setSession(rs.getInt("uid")));
				return true;
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return false;
	}
	
	public static UserInfo getUser(int uid)
	{
		UserInfo user = new UserInfo();
		String query = "select * from user where uid = '" + uid + "'";
		DBOperator dbOperator = new DBOperator();
		ResultSet rs = dbOperator.query(query);
		try 
		{
			if(rs.next())
			{
				user.setUid(rs.getInt("uid"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setHead(rs.getString("head"));
				user.setGender(rs.getInt("gender"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public static String setSession(int uid)
	{
		String session = MD5Util.getMD5(String.valueOf(uid));
		String update = "update user set session = '" + session + "' where uid = " + uid + "";
		DBOperator dbOperator = new DBOperator();
		dbOperator.updateSQL(update);
		return session;
	}
	
	public static String getSession(int uid)
	{
		String query = "select session from user where uid = " + uid;
		DBOperator dbOperator = new DBOperator();
		ResultSet rs = dbOperator.query(query);
		try {
			rs.next();
			return rs.getString("session");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
