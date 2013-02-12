package com.javatraining.model;

import java.util.HashMap;
import java.util.Set;

public class TopicSet {
	
	//Represents a set of topics.
	//Stores topicid mapped to Topic objects
	private HashMap<Integer, Topic> topicMap;
	
	public TopicSet() {
		topicMap = new HashMap();
	}
	
	public void addTopic(int topicid, Topic topic) {
		topicMap.put(Integer.valueOf(topicid), topic);
	}
	
	public Set<Integer> getTopicIds() {
		return topicMap.keySet();
	}
	
	public Topic getTopic(int topicid) {
		return topicMap.get(topicid);
	}
	public HashMap<Integer, Topic> getTopicMap() {
		return topicMap;
	}
	
	/*
	 * Use this to get an array of the topics sorted in date order of lastpost
	 * The topic with the most recent lastpost is in the beginning of the array.
	 * The one with the latest lastpost is in the end of the array.
	 * Sorts by select sort.
	 */
	public Topic[] getTopicsInDateOrder() {
		Topic[] unsortedTopics = topicMap.values().toArray(new Topic[0]);		
		Topic[] sortedTopics = new Topic[unsortedTopics.length];
		
		//Find the minimum topic
		Topic lowestDateTopic = new Topic();
		lowestDateTopic.setLastpost("9999-99-99 99:99:99");
		
		for(int ii = 0; ii < sortedTopics.length; ii++) {
			for(int i = 0; i < unsortedTopics.length; i++) {
				if(unsortedTopics[i].isNewerThan(lowestDateTopic)) {
					lowestDateTopic = unsortedTopics[i];
				}
			}
			sortedTopics[ii] = lowestDateTopic.clone();
			lowestDateTopic.setLastpost("9999-99-99 99:99:99");
		}
		return sortedTopics;
	}
}
