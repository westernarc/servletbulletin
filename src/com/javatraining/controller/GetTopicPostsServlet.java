package com.javatraining.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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
import com.javatraining.model.User;

/**
 * Servlet implementation class GetTopicPostsServlet
 */
@WebServlet("/GetTopicPostsServlet")
public class GetTopicPostsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTopicPostsServlet() {
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
		
		int requestedTopicId = Integer.valueOf(request.getParameter("topicid"));
		BulletinModel model = new BulletinModel();
		
		Topic requestedTopic = new Topic();
		requestedTopic.setTopicid(requestedTopicId);
		
		requestedTopic = model.getTopic(requestedTopic);
		
		PostSet postSet = model.getPostSetByTopic(requestedTopic);
		Set<Integer> idSet = postSet.getPostIds();
		
		User authorUser = new User();
		authorUser.setUserid(requestedTopic.getUserid());
		
		String topictitle = requestedTopic.getTopictitle();
		if(topictitle.equals("")) topictitle = "[no title]";
		String topictext = requestedTopic.getTopictext();
		if(topictext.equals("")) topictext = "[no text]";
		
		response.getWriter().print("#"+ requestedTopicId + " <b>" + topictitle + "</b><br>");
		response.getWriter().print("<font size = '0.5px'>Posted by "+model.getUserById(authorUser).getUsername()+" on "+requestedTopic.getTopicdate()+"</font><br>");
		out.print("<font size = '0.5px'>Last reply on "+requestedTopic.getLastpost()+"</font><br>");
		response.getWriter().print(topictext + "<p>");
		//For every postid in the set of post ids
		//Print each post
		
		Integer[] idArray = idSet.toArray(new Integer[0]);
		Arrays.sort(idArray);
		response.getWriter().print("<div style='margin-left:10px;'>");
		for(int currentId = 0; currentId < idArray.length; currentId++) {
			Post currentPost = postSet.getPost(idArray[currentId]);
			
			//Encase the post id with a link that includes the post number into the create post text box
			//It also scrolls the document to that post
			out.write("<b><a id = '#"+requestedTopicId+"##"+idArray[currentId]+"' onclick = addToPost('#"+requestedTopicId+"##"+idArray[currentId]+"')>");
			
			out.write("#" + requestedTopicId + "##" + idArray[currentId] + "<br>");
			
			out.write("</a></b>");
			User tempUser = new User();
			tempUser.setUserid(currentPost.getUserid());
			response.getWriter().print("<font size = '0.5px'>Posted by "+model.getUserById(tempUser).getUsername()+" on "+currentPost.getPostdate()+"</font><br>");
			String posttext = currentPost.getPosttext();
			if(posttext.equals("")) posttext = "[no text]";
			response.getWriter().print(posttext + "<p>");
			
		}
		response.getWriter().print("</div>");
		
		//Increment this topic's view count
		model.addViewToTopic(requestedTopic);
	}

}
