package com.demo.destranger.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.demo.destranger.data.UserInfo;
import com.demo.destranger.data.UserLoc;
import com.demo.destranger.tools.UlocHelper;

/**
 * Servlet implementation class StrangerServlet
 */
@WebServlet("/StrangerServlet")
public class StrangerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("update location");
		System.out.println(request.getHeader("Cookie"));
		PrintWriter printWriter = response.getWriter();
		JSONObject result = new JSONObject();
		HttpSession session = request.getSession(false);
		if(session == null)
		{
			System.err.println("session error");
			result.put("result", 2);
		}
		else 
		{
			int uid = Integer.valueOf(request.getParameter("uid"));
			double latitude = Double.valueOf(request.getParameter("latitude"));
			double longitude = Double.valueOf(request.getParameter("longitude"));
			if(UlocHelper.updateLoc(uid, latitude, longitude))
			{
				result.put("result", 1);
				ArrayList<UserInfo> users = UlocHelper.getUsers(uid, latitude, longitude);
				JSONArray jsonArray = new JSONArray();
				for(UserInfo user : users)
				{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("uid", user.getUid());
					jsonObject.put("username", user.getUsername());
					jsonObject.put("password", user.getPassword());
					jsonObject.put("head", user.getHead());
					jsonObject.put("gender", user.getGender());
					UserLoc userLoc = user.getUserLoc();
					JSONObject userlocObject = new JSONObject();
					userlocObject.put("uid",userLoc.getUid());
					userlocObject.put("latitude", userLoc.getLatitude());
					userlocObject.put("longitude", userLoc.getLongitude());
					userlocObject.put("timestamp", userLoc.getTimestamp());
					jsonObject.put("userloc", userlocObject);
					jsonArray.put(jsonObject);
				}
				result.put("users", jsonArray);
			}
			else 
			{
				result.put("result", 0);
			}
		}
		printWriter.print(result.toString());
	}

}
