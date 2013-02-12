package com.javatraining.model;

import java.util.HashMap;
import java.util.Set;

public class PostSet {
	
	//Represents a set of topics.
	//Stores topicid mapped to Topic objects
	private HashMap<Integer, Post> postMap;
	
	public PostSet() {
		postMap = new HashMap<Integer, Post>();
	}
	
	public void addPost(int postid, Post post) {
		postMap.put(Integer.valueOf(postid), post);
	}
	
	public Set<Integer> getPostIds() {
		return postMap.keySet();
	}
	
	public Post getPost(int postid) {
		return postMap.get(postid);
	}
	public HashMap<Integer, Post> getPostMap() {
		return postMap;
	}
}
