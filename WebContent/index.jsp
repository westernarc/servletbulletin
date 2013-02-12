<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import='java.util.*'%>
<% 
	int userid;
	String username;
	
	//Get the userid
	if(session.getAttribute("userid") != null) {
		 userid = (Integer)session.getAttribute("userid");
	} else {
		userid = 0;
		session.setAttribute("userid", userid);
		//This means anonymous
	}
	
	//Get the username
	if(session.getAttribute("username") != null) {
		username = session.getAttribute("username").toString();
	} else {
		username = "anonymous";
		session.setAttribute("username", username);
	}
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel = "stylesheet" type = "text/css" href = "css/index.css"/>
<title>ServletBulletin</title>

<script src = "javascript/jquery-1.9.1.js"></script>
<script>
	$(document).ready(function() {                        // When the HTML DOM is ready loading, then execute the following function...
	    $('#somebutton').click(function() {               // Locate HTML DOM element with ID "somebutton" and assign the following function to its "click" event...
	        $.get('BulletinController', function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
	            $('#bodyDiv').text(responseText);         // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
	        });
	    });
	});
	function loadTopics() {
		$.get('GetTopicListServlet', function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
            $('#bodyDiv').html(responseText);                 // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
        });
	}
	function loadPosts(id) {
		$.post('GetTopicPostsServlet', {topicid:id},function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
            $('#bodyDiv').html(responseText);                 // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
            $('#txtTopicToReply').val(id);
        });
	}
	function addTopic(uid, title, text) {
		$.post('AddTopicServlet', {topictitle:title, topictext:text, userid:uid},function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
            //$('#bodyDiv').html(responseText);                 // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
            loadTopics();
            $('#txtTopicTitle').val('');
            $('#txtTopicText').val('');
        });
	}
	function addPost(uid, topicid, text) {
		$.post('AddPostServlet', {userid:uid, topicid:topicid, posttext:text},function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
			loadPosts(topicid);                 // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
			$('#txtPostText').val('');			//Clears the text field
        });
	}
	function addToPost(text) {
		$('#txtPostText').val( $('#txtPostText').val() + text );
	}
	
	function login(uname, pword) {
		$.post('LoginServlet', {username:uname, password:pword},function(responseText) {
			<% 
				if(session.getAttribute("userid") != null) {
					userid = (Integer)session.getAttribute("userid");
					
				} else {
					userid = 0;
					//This means anonymous
				}
				System.out.println("userid set to " + userid);
				if(session.getAttribute("username") != null) {
					username = session.getAttribute("username").toString();
				} else {
					username = "anonymous";
				}
				System.out.println("username set to " + username);
			%>
			//Set the logged in user as session variables
			location.reload(true);
        });
	}
	function hashchange() {
		var hash = window.location.hash;
		hash = hash.replace("#", "");
		if(hash == "") {
			hash = "all";
			location.href = location.href + "#all";
		}
		//If the hash is 'all', load topics
		if(hash == "all")
			loadTopics();
		//If the has is a number, load posts for that topic.
		if(hash == parseInt(hash)) 
			loadPosts(hash);
		
		//Set the title to something new
		window.document.title = "SB#" + hash;
	}

</script>
</head>


<body onload = "hashchange();" onhashchange = "hashchange();">

<div id= "bodyDiv"></div>

<div id = "rightDiv">
<div id = "headerDiv">
<center>
	<h1 class = "gray">s e r v l e t B u l l e t i n</h1>
</center>
</div>
topics : 
<a href = "#all" onclick='javascript:loadTopics();'>view all</a> | <a>view today</a> | <a>view top 10</a>
<div id= "loginDiv">
	<input type = "text" id = "usernameDisplay" value ='Posting as <%=username %>@<%=userid%>' style = 'border:0px solid #000;' readonly></input>

	<table>
		<tr>
			<td  colspan = "2"><b># login</b></td>
		</tr>
		<%
			if(userid == 0) {  //Show login dialog
				out.print(
				"<tr>"+
				"<td>username</td>"+
				"<td><input type = 'text' id = 'txtUsername'/></td>"+
				"</tr>"
				);
				out.print(
				"<tr>"+
				"<td>password</td>"+
				"<td><input type = 'text' id = 'txtPassword'/></td>"+
				"</tr>"
				);
			} else {  //Show logout

			}
		%>
		<tr>
			<td><button id="btnLogin" onclick = "javascript:login( $('#txtUsername').val() , $('#txtPassword').val() );"><%= userid == 0 ? "login" : "logout"%></button></td>
		</tr>
	</table>
</div>

<div id = "topicDiv">
	<table>
		<tr>
			<td colspan = "2"><b>+ create topic</b></td>
		</tr>
		<tr>
			<td>topic title</td>
			<td><input type = 'text' id = 'txtTopicTitle'/></td>
		</tr>
		<tr>
			<td>topic text</td>
			<td><textarea id = 'txtTopicText'></textarea></td>
		</tr>
		<tr>
			<td><button id="btnCreateTopic" onclick = "javascript:addTopic(<%=userid %>, $('#txtTopicTitle').val(), $('#txtTopicText').val());">create topic</button></td>
		</tr>
	</table>
</div>

<div id = "postDiv">
	<table>
		<tr>
			<td colspan = "2"><b>+ create post</b></td>
		</tr>
		<tr>
			<td>topic id</td>
			<td><input type = 'text' id = 'txtTopicToReply'/></td>
		</tr>
		<tr>
			<td>post text</td>
			<td><textarea id = 'txtPostText'></textarea></td>
		</tr>
		<tr>
			<td><button id="btnCreatePost" onclick = "javascript:addPost(<%=userid %>, $('#txtTopicToReply').val(), $('#txtPostText').val());">create post</button></td>
		</tr>
	</table>
</div>

</div>
</body>

</html>