package com.demo.destranger.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.demo.destranger.data.User;
import com.demo.destranger.tools.UserHelper;

/**
 * Servlet implementation class RegiserServlet
 */
@WebServlet("/RegiserServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
		  BufferedReader reader = request.getReader();
		  while ((line = reader.readLine()) != null)
		    jb.append(line);
		} catch (Exception e) { /*report an error*/ }
		System.out.println(jb.toString());
		JSONObject requestJsonObject = new JSONObject(jb.toString());
		User user = new User();
		String username = requestJsonObject.getString("username");
		String password = requestJsonObject.get("password").toString();
		String head = requestJsonObject.getString("head");
		int gender = requestJsonObject.getInt("gender");
		user.setUsername(username);
		user.setPassword(password);
		user.setHead(head);
		user.setGender(gender);
		PrintWriter printWriter = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		if(UserHelper.exist(user))
		{
			System.out.println("用戶名已存在");
			jsonObject.put("result", "sorry,user exists");
		}
		else {
			if(UserHelper.add(user))
			{
				System.out.println("注册成功");
				jsonObject.put("result", "register successfully");
			}
			else {
				System.out.println("注册失敗");
				jsonObject.put("result", "register failed");
			}
		}
		printWriter.write(jsonObject.toString());
	}

}
