package com.javatraining.controller;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javatraining.model.BulletinModel;
import com.javatraining.model.Topic;
import com.javatraining.model.TopicSet;
import com.javatraining.model.User;

/**
 * Servlet implementation class GetThreadListServlet
 */
@WebServlet("/GetTopicListServlet")
public class GetTopicListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTopicListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		response.setContentType("text/html");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8");

		BulletinModel model = new BulletinModel();
		
		TopicSet topicSet = model.getTopicSet();

		Topic[] topicArray = topicSet.getTopicsInDateOrder();
		
		//For every topicid in the set of topic ids
		//Print a list of topic titles
		for(int topicIndex = topicArray.length - 1; topicIndex > 0; topicIndex--) {
			Topic currentTopic = topicArray[topicIndex];
			
			out.write("#" + currentTopic.getTopicid() + " ");
			
			User topicAuthor = new User();
			topicAuthor.setUserid(currentTopic.getUserid());
			String username = model.getUserById(topicAuthor).getUsername();
			
			String topictitle = currentTopic.getTopictitle();
			if(topictitle.equals("")) topictitle = "[no title]";
			String topictext = currentTopic.getTopictext();
			if(topictext.equals("")) topictext = "[no text]";
			
			out.print("<a href = '#"+currentTopic.getTopicid()+"'> <b> " +topictitle + "</b></a>  <font size = '0.5px'>"+currentTopic.getViews()+" views</font><br>");
			out.print("<font size = '0.5px'>Posted by "+username+" on "+currentTopic.getTopicdate()+"</font><br>");
			out.print("<font size = '0.5px'>Last reply on "+currentTopic.getLastpost()+"</font><br>");
			out.print(topictext + "<p>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
