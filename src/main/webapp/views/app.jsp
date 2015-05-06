<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<title>Users</title>
	<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
	<link href="<spring:url value="/bleets.css"/>" rel="stylesheet">	
	<script src="https://code.jquery.com/jquery-2.1.3.js"></script>	
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	<script src="<spring:url value="/bleets.js"/>"></script>
</head>
<body background="http://img.wallpaperstock.net:81/vista-grass-2-wallpapers_2289_1920x1200.jpg">
	<div class="container">
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#navbar" aria-expanded="false"
						aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">Bleeter</a>
				</div>

				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav navbar-right">
						<li><a href="/logout">Logout</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li><a>Welcome <span id="usernameSpan">${user.username}</span></a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
			<!--/.container-fluid -->
		</nav>
		<span style="display:none;" id="userid">${user.id}</span>
		<div class="row">
			<div class="avatar-circle pull-left"><img src="users/${user.id}/avatar" id="avatarImage" class="avatar"></div>	
			<div>	
				<input type="file" id="avatar" name="avatar"> <br>
				<div class="btn btn-sm btn-primary" onclick="addAvatar()">Add avatar</div>
			</div>
		</div>
		
		<div class="page-header">
			<h2>Bleeter</h2>
		</div>		
		

		<div class="row">
			Bleet description: <input type="text" id="bleet"> Make comment private?: 
			<input type="checkbox" id="privateComment">
			<div class="btn btn-sm btn-primary" onclick="addBleet()">Add bleet</div>

		</div>

		<div id="imageList">
		</div>
	</div>
</body>
</html>