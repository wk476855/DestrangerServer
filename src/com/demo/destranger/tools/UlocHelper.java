package com.demo.destranger.tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.demo.destranger.data.LatlngDistance;
import com.demo.destranger.data.UserInfo;
import com.demo.destranger.data.UserLoc;

public class UlocHelper {

	public static boolean updateLoc(int uid, double latitude, double longitude)
	{
		if(!exist(uid))
			return insert(uid, latitude, longitude);
		else {
			return update(uid, latitude, longitude);
		}
	}
	
	public static boolean exist(int uid)
	{
		String exist = "select count(*) as rowCount from uloc where uid = " + uid;
		DBOperator dbOperator = new DBOperator();
		ResultSet rs = dbOperator.query(exist);
		try 
		{
			rs.next();
			if(rs.getInt("rowCount") > 0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static ArrayList<UserInfo> getUsers(int uid, double latitude, double longitude)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String current = df.format(new Date());
		ArrayList<UserInfo> users = new ArrayList<UserInfo>();
		String query = "select * from uloc where uid <> " + uid;
		DBOperator dbOperator = new DBOperator();
		ResultSet rs = dbOperator.query(query);
		try 
		{
			while(rs.next())
			{
				Date date = rs.getDate("timestamp");
				long diff = df.parse(current).getTime() - date.getTime();
				if(diff < 30 * 60 * 1000) //30 minutes
				{
					double lat = rs.getDouble("latitude");
					double lon = rs.getDouble("longitude");
					if(LatlngDistance.getDist(lon, lat, longitude, latitude) <= 1000)
					{
						int id = rs.getInt("uid");
						UserInfo user = UserHelper.getUser(id);
						UserLoc userLoc = new UserLoc();
						userLoc.setUid(id);
						userLoc.setLatitude(lat);
						userLoc.setLongitude(lon);
						userLoc.setTimestamp(date);
						user.setUserLoc(userLoc);
						users.add(user);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	public static boolean insert(int uid, double latitude, double longitude)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String insert = "insert into uloc(uid,latitude,longitude,timestamp) values(" + uid + "," + latitude + "," + longitude + ",'" + df.format(new Date()) + "')";
		DBOperator dbOperator = new DBOperator();
		return dbOperator.insert(insert);
	}
	
	public static boolean update(int uid, double latitude, double longitude)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String update = "update uloc set latitude = " + latitude + ", longitude = " + longitude + ", timestamp = '" + df.format(new Date()) + "' where uid = " + uid;
		DBOperator dbOperator = new DBOperator();
		return dbOperator.updateSQL(update);
	}
	
}
