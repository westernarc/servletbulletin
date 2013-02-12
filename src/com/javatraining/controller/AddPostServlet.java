package com.javatraining.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javatraining.model.BulletinModel;
import com.javatraining.model.Post;
import com.javatraining.model.Topic;

/**
 * Servlet implementation class AddPostServlet
 */
@WebServlet("/AddPostServlet")
public class AddPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPostServlet() {
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
		//Create the Post object
		BulletinModel model = new BulletinModel();
		
		int nextPostId = model.getHighestPostId() + 1;
		int topicid = 0;
		try {
			topicid = Integer.valueOf(request.getParameter("topicid"));
		} catch(NumberFormatException e) {
			e.printStackTrace();
			topicid = 0;
		}
		String posttext = request.getParameter("posttext");
		int userid = Integer.valueOf(request.getParameter("userid"));
		
		Post newPost = new Post();
		newPost.setPostid(nextPostId);
		newPost.setTopicid(topicid);
		newPost.setPosttext(posttext);
		newPost.setUserid(userid);
		
		//Get the date of post creation
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		newPost.setPostdate(dateFormat.format(date));
		
		model.storePost(newPost);
	}

}
