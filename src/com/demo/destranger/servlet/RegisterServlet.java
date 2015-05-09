package com.demo.destranger.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.destranger.data.User;
import com.demo.destranger.tools.UserDBOperator;

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
		System.out.println("doget");
		User user = new User();
		/*user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setHead(request.getParameter("head"));
		user.setGender(Integer.valueOf(request.getParameter("gender")));
		UserDBOperator udb = new UserDBOperator();
		int result = udb.insert(user);
		PrintWriter printWriter = response.getWriter();
		printWriter.write(result);*/
		user.setUsername("tang");
		user.setPassword("tang");
		user.setHead("tang");
		user.setGender(0);
		UserDBOperator udb = new UserDBOperator();
		int result = udb.insert(user);
		PrintWriter printWriter = response.getWriter();
		printWriter.write(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
