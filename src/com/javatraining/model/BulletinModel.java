package com.javatraining.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BulletinModel {
	Connection connection = null;
	Statement statement = null;
	String databaseUrl = "jdbc:mysql://localhost:3306/bulletinDb";
	String user = "root";
	String pass = "catherineliou104";
	
	String postTableName = "posts";
	String userTableName = "users";
	String topicTableName = "topics";
	String recordsTableName = "records";

	public BulletinModel() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			System.out.println("Driver not found");
			e.printStackTrace();
			return;
		}
		
		try {
			connection = DriverManager.getConnection(databaseUrl, user, pass);	
			statement = connection.createStatement();

		} catch(SQLException e) {
			e.printStackTrace();
			try {
				if(statement != null) statement.close();
			} catch(SQLException se2) {}
			try {
				if(connection != null) connection.close();
			} catch(SQLException se3) {}
		}
	}
	public TopicSet getTopicSet() {
		TopicSet result = new TopicSet();
		
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery("select * from " + topicTableName);
			
			while(resultSet.next()) {
				int topicid = resultSet.getInt("topicid");
				int userid = resultSet.getInt("userid");
				String topictitle = resultSet.getString("topictitle");
				String topictext = resultSet.getString("topictext");
				String topicdate = resultSet.getString("topicdate");
				int views = resultSet.getInt("views");
				String lastpost = resultSet.getString("lastpost");
				
				Topic newTopic = new Topic();
				newTopic.setTopicid(topicid);
				newTopic.setUserid(userid);
				newTopic.setTopictitle(topictitle);
				newTopic.setTopictext(topictext);
				newTopic.setTopicdate(topicdate);
				newTopic.setViews(views);
				newTopic.setLastpost(lastpost);
				
				result.addTopic(topicid, newTopic);
			} 
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result; 
	}
	public PostSet getPostSetByTopic(Topic topic) {
		PostSet result = new PostSet();
		
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery("select * from " + postTableName + " where topicid=" + topic.getTopicid());
			
			while(resultSet.next()) {
				int postid = resultSet.getInt("postid");
				int topicid = resultSet.getInt("topicid");
				int userid = resultSet.getInt("userid");
				String posttext = resultSet.getString("posttext");
				String postdate = resultSet.getString("postdate");
				
				Post newPost = new Post();
				newPost.setPostid(postid);
				newPost.setTopicid(topicid);
				newPost.setUserid(userid);
				newPost.setPosttext(posttext);
				newPost.setPostdate(postdate);
				
				result.addPost(postid, newPost);
			} 
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public void storeTopic(Topic topic) {
		int topicid = topic.getTopicid();
		int userid = topic.getUserid();
		String topictitle = topic.getTopictitle();
		String topictext = topic.getTopictext();
		String topicdate = topic.getTopicdate();
		int views = topic.getViews();
		String lastpost = topic.getLastpost();
		
		if(statement != null) {
			try {
				statement.executeUpdate("insert into "+ topicTableName + " values ("+topicid+", "+userid+", '"+topictext+"','"+topicdate+"', "+views+", '"+topictitle+"', '"+lastpost+"' )");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	public int getHighestPostId() {
		int highestId = 0;
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery("select postid from " + postTableName);
			
			while(resultSet.next()) {
				int postid = resultSet.getInt("postid");
				if(postid > highestId) {
					highestId = postid;
				}
			} 
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return highestId;
	}
	public int getHighestTopicId() {
		int highestId = 0;
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery("select * from " + topicTableName);
			
			while(resultSet.next()) {
				int topicid = resultSet.getInt("topicid");
				if(topicid > highestId) {
					highestId = topicid;
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return highestId;
	}
	public void addViewToTopic(Topic topic) {
		int topicid = topic.getTopicid();
		if(statement != null) {
			try {
				statement.executeUpdate("update "+ topicTableName + " set views=views+1 where topicid="+topicid);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void storePost(Post post) {
		int postid = post.getPostid();
		int topicid = post.getTopicid();
		int userid = post.getUserid();
		String posttext = post.getPosttext();
		String postdate = post.getPostdate();
		
		if(statement != null) {
			try {
				statement.executeUpdate("insert into "+ postTableName + " values ("+postid+", "+topicid+", "+userid+", '"+posttext+"','"+postdate+"' )");
				
				//Update the topic's lastpost 
				statement.executeUpdate("update "+ topicTableName + " set lastpost= '"+ postdate +"' where topicid='"+topicid+"'");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	public User getUserById(User user) {
		ResultSet resultSet = null;
		User resultUser = new User();
		try {
			resultSet = statement.executeQuery("select * from " + userTableName + " where userid="+ user.getUserid());
			
			if(resultSet.next()) {
				resultUser.setUsername(resultSet.getString("username"));
				resultUser.setUserid(resultSet.getInt("userid"));
				resultUser.setPassword(resultSet.getString("password"));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return resultUser;
	}
	public User getUserByUsername(User user) {
		ResultSet resultSet = null;
		User resultUser = new User();
		try {
			resultSet = statement.executeQuery("select * from " + userTableName + " where username='"+ user.getUsername() + "'");
			
			if(resultSet.next()) {
				resultUser.setUsername(resultSet.getString("username"));
				resultUser.setUserid(resultSet.getInt("userid"));
				resultUser.setPassword(resultSet.getString("password"));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return resultUser;
	}
	public Topic getTopic(Topic topic) {
		ResultSet resultSet = null;
		Topic resultTopic = new Topic();
		try {
			resultSet = statement.executeQuery("select * from " + topicTableName + " where topicid="+ topic.getTopicid());
			
			if(resultSet.next()) {
				resultTopic.setTopicid(resultSet.getInt("topicid"));
				resultTopic.setUserid(resultSet.getInt("userid"));
				resultTopic.setTopictext(resultSet.getString("topictext"));
				resultTopic.setTopicdate(resultSet.getString("topicdate"));
				resultTopic.setViews(resultSet.getInt("views"));
				resultTopic.setTopictitle(resultSet.getString("topictitle"));
				resultTopic.setLastpost(resultSet.getString("lastpost"));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return resultTopic;
	}
	public void storeUser(User user) {
		int userid = user.getUserid();
		String username = user.getUsername();
		String password = user.getPassword();
		
		if(statement != null) {
			try {
				statement.executeUpdate("insert into "+ userTableName + " values ("+userid+", '"+username+"','"+password+"' )");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void removeUser(User user) {
		int userid = user.getUserid();
		if(statement != null) {
			try {
				statement.executeUpdate("remove from "+ userTableName + " where userid = " + userid);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean validLogin(User user) {
		boolean valid = false;
		
		ResultSet resultSet = null;
		try {
			System.out.println("QUERY:" + "select * from " + userTableName + " where username='"+ user.getUsername() + "' and password='" + user.getPassword()+"';");
			resultSet = statement.executeQuery("select * from " + userTableName + " where username='"+ user.getUsername() + "' and password='" + user.getPassword()+ "'");

			if(resultSet.next()) {
				//If there is a match simply set valid to true
				valid = true;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return valid;
	}
	public void close() {
		try {
			if(statement != null) statement.close();
			if(connection != null) connection.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(statement != null) statement.close();
			} catch(SQLException se2) {}
			try {
				if(connection != null) connection.close();
			} catch(SQLException se3) {}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("JDBC: Inserting to database:");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			System.out.println("Driver not found");
			e.printStackTrace();
			return;
		}
		
		Connection connection = null;
		Statement statement = null;
		String databaseUrl = "jdbc:mysql://localhost:3306/bulletinDb";
		String user = "root";
		String pass = "catherineliou104";
		
		String postTableName = "posts";
		String userTableName = "users";
		String topicTableName = "topics";
		try {
			connection = DriverManager.getConnection(databaseUrl, user, pass);
			
			statement = connection.createStatement();
			//statement.executeUpdate("insert into "+ userTableName + " values (0, 'anonymous','' )");
			//statement.executeUpdate("insert into "+ topicTableName + " values (0, 0, 'First Topic', '2013-02-07 09:07:00', 0)");
			//statement.executeUpdate("insert into "+ postTableName + " values (0, 0, 0, 'First post.','2013-02-07 09:07:00' )");
			
			statement.close();
			connection.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(statement != null) statement.close();
			} catch(SQLException se2) {}
			try {
				if(connection != null) connection.close();
			} catch(SQLException se3) {}
		}
	}
	
	
}
