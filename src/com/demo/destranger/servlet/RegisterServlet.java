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

import com.demo.destranger.data.UserInfo;
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
		UserInfo user = new UserInfo();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String head = request.getParameter("head");
		int gender = Integer.valueOf(request.getParameter("gender"));
		user.setUsername(username);
		user.setPassword(password);
		user.setHead(head);
		user.setGender(gender);
		PrintWriter printWriter = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		if(UserHelper.exist(user))
		{
			System.out.println("用户名已存在");
			jsonObject.put("result", 0);
		}
		else {
			if(UserHelper.add(user))
			{
				System.out.println("注册成功");
				jsonObject.put("result", 1);
			}
			else {
				System.out.println("注册失败");
				jsonObject.put("result", 2);
			}
		}
		printWriter.write(jsonObject.toString());
	}

}
