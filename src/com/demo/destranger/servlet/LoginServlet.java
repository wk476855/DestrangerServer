package com.demo.destranger.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("login");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UserInfo user = new UserInfo();
		user.setUsername(username);
		user.setPassword(password);
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(30 * 60);
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

}
