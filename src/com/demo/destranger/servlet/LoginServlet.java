package com.demo.destranger.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.demo.destranger.data.UserInfo;
import com.demo.destranger.tools.UserHelper;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("login");
		String requestString = request.getParameter("request");
		JSONObject requestJsonObject = new JSONObject(requestString);
		String username = requestJsonObject.getString("username");
		String password = requestJsonObject.getString("password");
		UserInfo user = new UserInfo();
		user.setUsername(username);
		user.setPassword(password);
		PrintWriter printWriter = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		if(UserHelper.login(user))
		{
			jsonObject.put("result", 1);
			jsonObject.put("uid", user.getUid());
			jsonObject.put("username", user.getUsername());
			jsonObject.put("password", user.getPassword());
			jsonObject.put("head", user.getHead());
			jsonObject.put("gender", user.getGender());
			jsonObject.put("session", user.getSession());
		}
		else {
			jsonObject.put("result", 0);
		}
		printWriter.write(jsonObject.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
