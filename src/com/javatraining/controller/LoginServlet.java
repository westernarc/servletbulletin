package com.javatraining.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javatraining.model.BulletinModel;
import com.javatraining.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		PrintWriter out = response.getWriter();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User requestUser = new User();
		requestUser.setUsername(username);
		requestUser.setPassword(password);
		
		BulletinModel model = new BulletinModel();

		User retrievedUser = model.getUserByUsername(requestUser);
		
		if(model.validLogin(requestUser)) {
			out.print(retrievedUser.getUserid());
		} else {
			out.print(0);
		}
		HttpSession session = request.getSession();
		
		session.setAttribute("userid", retrievedUser.getUserid());
		session.setAttribute("username", retrievedUser.getUsername());
		
		request.setAttribute("userid", retrievedUser.getUserid());
		request.setAttribute("username", retrievedUser.getUsername());
		
		getServletContext().getRequestDispatcher("/index.jsp").include(request, response);
	}

}
