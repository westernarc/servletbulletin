package com.javatraining.model;

public class Post {
	private int postid;
	private int topicid;
	private int userid;
	private String posttext;
	private String postdate;
	
	public int getPostid() {
		return postid;
	}
	public void setPostid(int postid) {
		this.postid = postid;
	}
	public int getTopicid() {
		return topicid;
	}
	public void setTopicid(int topicid) {
		this.topicid = topicid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getPosttext() {
		return posttext;
	}
	public void setPosttext(String posttext) {
		this.posttext = posttext;
	}
	public String getPostdate() {
		return postdate;
	}
	public void setPostdate(String date) {
		this.postdate = date;
	}
}
