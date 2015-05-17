package com.demo.destranger.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBOperator {

	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String URL_SERVER = "jdbc:mysql://localhost:3306/destranger";
	public static final String USER = "root";
	public static final String PASSWORD = "123456";
	public Connection con = null;
	public PreparedStatement statement = null;
	
	public DBOperator()
	{
		getConnection();
	}
	
	public void getConnection()
	{
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL_SERVER,USER,PASSWORD);
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("driver wrong!!");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void CancelConnection()
	{
		try {
			if(con != null)
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public ResultSet query(String sql)
	{
		ResultSet rs = null;  
        try {  
            statement = con.prepareStatement(sql);  
            rs = statement.executeQuery(sql);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return rs;  
	}

	public boolean insert(String sql) 
	{  
        try 
        {  
            statement = con.prepareStatement(sql);  
            statement.executeUpdate();  
            return true;  
        } catch (SQLException e) {  
            System.out.println("insert error");  
            e.printStackTrace();  
        } catch (Exception e) {  
            System.out.println("insert error");  
            e.printStackTrace();  
        }  
        return false;  
    }  
	
	public boolean deleteSQL(String sql) 
	{  
        try 
        {  
            statement = con.prepareStatement(sql);  
            statement.executeUpdate();  
            return true;  
        } catch (SQLException e) {  
            System.out.println("delete error");  
            e.printStackTrace();  
        } catch (Exception e) {  
            System.out.println("delete error");  
            e.printStackTrace();  
        }  
        return false;  
    }  
	
    //execute update language  
    public boolean updateSQL(String sql) 
    {  
        try 
        {  
            statement = con.prepareStatement(sql);  
            statement.executeUpdate();  
            return true;  
        } catch (SQLException e) {  
            System.out.println("update error");  
            e.printStackTrace();  
        } catch (Exception e) {  
            System.out.println("update error");  
            e.printStackTrace();  
        }  
        return false;  
    }  
}
