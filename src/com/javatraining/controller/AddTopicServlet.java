package com.javatraining.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javatraining.model.BulletinModel;
import com.javatraining.model.Post;
import com.javatraining.model.PostSet;
import com.javatraining.model.Topic;

/**
 * Servlet implementation class AddTopicServlet
 */
@WebServlet("/AddTopicServlet")
public class AddTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTopicServlet() {
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
		//Create the Topic object
		BulletinModel model = new BulletinModel();
		int nextTopicid = model.getHighestTopicId() + 1;
		String topictitle = request.getParameter("topictitle");
		String topictext = request.getParameter("topictext");
		int userid = Integer.valueOf(request.getParameter("userid"));
		
		Topic newTopic = new Topic();
		newTopic.setTopicid(nextTopicid);
		newTopic.setTopictitle(topictitle);
		newTopic.setTopictext(topictext);
		
		//Get the date of topic creation
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		newTopic.setTopicdate(dateFormat.format(date));
		newTopic.setLastpost(dateFormat.format(date));
		newTopic.setUserid(userid);
		//New topics don't have views.
		newTopic.setViews(0);
		
		model.storeTopic(newTopic);
	}

}
