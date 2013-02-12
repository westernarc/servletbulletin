package com.javatraining.model;

import java.util.StringTokenizer;

public class Topic {
	private int topicid;
	private int userid;
	private String topictitle;
	private String topictext;
	private String topicdate;
	private int views;
	private String lastpost;
	
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
	public String getTopictitle() {
		return topictitle;
	}
	public void setTopictitle(String topictitle) {
		this.topictitle = topictitle;
	}
	public String getTopictext() {
		return topictext;
	}
	public void setTopictext(String topictext) {
		this.topictext = topictext;
	}
	public String getTopicdate() {
		return topicdate;
	}
	public void setTopicdate(String newDate) {
		topicdate = newDate;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public String getLastpost() {
		return lastpost;
	}
	public void setLastpost(String date) {
		lastpost = date;
	}
	
	/*
	 * Use this to compare other topics
	 * and find which one is more recent.
	 * If the calling topic is newer, return true.
	 * If it is older or the same date, return false.
	 * 
	 * This expects topic date formats to follow:
	 * YYYY-MM-DD HH:MM:SS
	 */
	public boolean isNewerThan(Topic topic) {
		//Split the date by non-word character regex
		String[] dateComponents = getLastpost().split("\\W");  
		int thisYear = Integer.valueOf(dateComponents[0]);
		int thisMonth = Integer.valueOf(dateComponents[1]);
		int thisDay = Integer.valueOf(dateComponents[2]);
		
		int thisHour = Integer.valueOf(dateComponents[3]);
		int thisMinute = Integer.valueOf(dateComponents[4]);
		int thisSecond = Integer.valueOf(dateComponents[5]);
		
		String[] otherDateComponents = topic.getLastpost().split("\\W");
		int thatYear = Integer.valueOf(otherDateComponents[0]);
		int thatMonth = Integer.valueOf(otherDateComponents[1]);
		int thatDay = Integer.valueOf(otherDateComponents[2]);
		
		int thatHour = Integer.valueOf(otherDateComponents[3]);
		int thatMinute = Integer.valueOf(otherDateComponents[4]);
		int thatSecond = Integer.valueOf(otherDateComponents[5]);
		
		if(thisYear != thatYear) {
			return thisYear < thatYear ? true : false; 
		} else {  //Same year
			if(thisMonth != thatMonth) {
				return thisMonth < thatMonth ? true : false;
			} else {  //Same month
				if(thisDay != thatDay) {
					return thisDay < thatDay ? true : false;
				} else {  //Same day
					if(thisHour != thatHour) {
						return thisHour < thatHour ? true : false;
					} else {  //Same hour
						if(thisMinute != thatMinute) {
							return thisMinute < thatMinute ? true : false;
						} else { //Same minute
							if(thisSecond != thatSecond) {
								return thisSecond < thatSecond ? true : false;
							} else {  //Same second
								return true;//exact same time
							}
						}
					}
				}
			}
		}
	}
	
	public Topic clone() {
		Topic clone = new Topic();
		clone.setLastpost(lastpost);
		clone.setTopicdate(topicdate);
		clone.setTopicid(topicid);
		clone.setTopictext(topictext);
		clone.setTopictitle(topictitle);
		clone.setUserid(userid);
		clone.setViews(views);
		return clone;
	}
}
