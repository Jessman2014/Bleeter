<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<title>Users</title>
	<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-2.1.3.js"></script>	
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	<style>
		body { padding-top: 70px; background-repeat: no-repeat; background-color: #C6E2EE; }
		.row { margin-bottom: 1em; } 
		img.thumb { max-width: 120px; max-height: 100px; border : 1px solid gray;} 
		.media { border-radius: 5px; padding: .5em; margin-bottom: 1em; background-color: rgb(250,250,250);}
		.avatar-circle { border-radius: 50%; display : inline-block; width:150px; height: 150px; overflow: hidden; margin-right: 3em;}
		.avatar-circle img { display: block; width: 100%; }	
	</style>	
	<script>
	user = {};
	users = {};
	bleets = {};
	page = 0;
	sort = "username";
	order = "asc";
	isAdmin = false;
	isUser = false;
	mode = "home";
	$(document).ready(function() {
		userid = $('#userid').html();
		getUser();
	});
	
	layoutHome = function() {
		resetPage();
		getUser();
	}
	
	layoutUser = function() {
		$('.navbar-header')
			.append('<a class="navbar-brand" href="#" onclick="getUsersBleets()"><span class="glyphicon glyphicon-tasks"></span>My Bleets</a>' +
					'<a class="navbar-brand" href="#" onclick="layoutUpdate()"><span class="glyphicon glyphicon-pencil"></span>Edit Profile</a>');
	}
	
	layoutAdmin = function() {
		$('.navbar-header').append('<a class="navbar-brand" href="#" onclick="resetPage(); layoutUsers();"><span class="glyphicon glyphicon-user"></span>Edit Users</a>')
		$('.nav-sidebar').append('<li>Admin</li>');
		$('#headerRow').append('<th>Block Bleet</th>');
	}
	
	layoutAdminBleets = function() {
		bleetRows = $('.blockCell');
		bleetRows.css('visibility', 'visible');
	}
	
	resetPage = function() {
		page = 0;
		sort = "username";
		order = "asc";
	}
	
	blockFunc = function(bid) {
		bid = bid.trim();
		ch = $('#'+bid+' input')[0].checked;
		$.ajax({
			url : "bleets/" + bid + "/blck",
			dataType : 'json',
			type : 'put',
			data : "block=" + ch + "&page=" + page + "&sort=" + sort + "&order=" + order,
			success : function(data) {				
				bleets = data;
				updateBleets();
			},
			failure: function(err) {
				console.log("There is an error with updating a bleet: " + err);
			}
		});	
	}
	
	function flipOrder() {
		if (order == "asc")
			order = "desc";
		else
			order = "asc";
	}
	
	authority = function() {
		$(user.authorities) 
		.each(
				function (key, val) {
					if(val == "ROLE_ADMIN")
						isAdmin = true;
					if(val == "ROLE_USER")
						isUser = true;
				});
		if (isUser)
			layoutUser();
		if (isAdmin)
			layoutAdmin();
	}
	
	getUser = function() {
		$.ajax({
			url : "users/" + userid,
			dataType : 'json',
			type : 'get',
			success : function(data) {				
				user = data;
				authority();
				getBleets();
			},
			failure: function(err) {
				console.log("There is an error with retrieving user: " + err);
			}
		});	
	}
	
	getBleets = function() {
		if (page < 0)
			page = 0;
		$.ajax({
			url : "users/" + userid + "/bleets",
			dataType : 'json',
			data: { page: page, sort: sort, order: order},
			type : 'get',
			success : function(data) {
				bleets = data;
				updateBleets();
				
			},
			failure: function(jqXHR, textStatus, errorThrown) {
				console.log("There is an error with adding a bleet: " + errorThrown);
			}
		});		
	}

	addBleet = function() {		
		bleet = $('#bleet').val();
		checked = $('input:checked').length === 1;
		//isPrivate = $('#privateComment').attr('checked') === "checked";
		clearForm();			
		$.ajax({
			url : "users/" + userid + "/bleets",
			dataType : 'json',
			type : 'post',
			data : { bleet : bleet, privatecomment : checked },
			success : function(data) {				
				bleets = data;
				updateBleets();
			},
			failure: function(jqXHR, textStatus, errorThrown) {
				console.log("There is an error with adding a bleet: " + errorThrown);
			}
		});					
	}

	updateBleet = function(id) {		
		bleet = $('#bleet').val();
		privateCommment = $('#privateComment').val();
		clearForm();			
		$.ajax({
			url : "users/" + userid + "/bleets" + id,
			dataType : 'json',
			type : 'put',
			data : { bleet : bleet, privateComment : privateComment },
			success : function(data) {				
				user = data;
				updateBleets();
			},
			failure: function(err) {
				console.log("There is an error with updating a bleet: " + err);
			}
		});					
	}

	addAvatar = function() {
		imageFile = document.getElementById('avatar').files[0];
		var data = new FormData();
		data.append('avatar', imageFile);
		$('#avatar').replaceWith( $('#avatar').clone(true) );
		
		$.ajax({
			url : 'users/' + userid + '/avatar',
			type : 'POST',
			data : data,
			cache : false,
			dataType : 'text',
			processData : false, 
			contentType : false, 
			success : function(data, textStatus, jqXHR) {
				d = new Date();
				var src = $("#avatarImage").attr("src");
				$('#avatarImage').attr('src', src + '?' + d.getTime());
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log('ERRORS: ' + textStatus);
			}
		});
					
	}

	deleteBleet = function(id) {
		clearForm();
		$.ajax({
			url : "users/" + userid + "/bleets/" + id,
			dataType : 'json',
			type : 'delete',
			success : function(data) {
				user = data;
				updateBleets();
			},
			failure : function(err) {
				console.log("There is an error with adding a bleet: " + err);
			}
		});
	}
	
	layoutUsers = function() {
		userForm = '<div class="form-group"><label for="firstname">Firstname</label><input type="text" id="firstname">' +
		'</div><div class="form-group"><label for="lastname">Lastname</label><input type="text" id="lastname"></div><div class="form-group">' +
		'<label for="username">Username</label><input type="text" id="username"></div><div class="form-group"><label for="email">Email</label>' +
		'<input type="email" id="email"></div><div class="form-group"><label for="password">Password</label><input type="password" id="password">' +
		'</div><div class="btn btn-sm btn-primary" onclick="addUser()">Add user</div>'; 
		
		$('#addBleets').children().remove();
		$('#addBleets').addClass('form-inline');
		$('#addBleets').append(userForm);
		$('tr').remove();
		table = $('table');
		table.append('<tr id="headerRow"> <th>Username</th> <th>Name</th> <th>Email</th><th>Admin</th></tr>');
		getUsers();
	}
	
	updateUsers = function() {
		userRows = $('tr ~ tr');
		userRows.remove();
		table = $('table');
		$(users.content)
			.each(
				function(key,val){
					uid = "'"+val.id+"'";
					str = '<tr id=' + val.id + '> <td>' + val.username + '</td><td>' + val.firstName + ' ' + val.lastName + '</td><td>' + val.email +
					'</td><td><input onclick="changeAdmin('+uid+')" type="checkbox" ';
					$(val.authorities).each(function(key,val){
							if (val == "ROLE_ADMIN")
								str += 'checked';
						});
					str += '></tr>';
					table.append(str);
				});
	}
	
	addUser= function(){
		username = $('#username').val();
		password = $('#password').val();
		firstname = $('#firstname').val();
		lastname = $('#lastname').val();
		email = $('#email').val();
		clearUserForm();			
		$.ajax({
			url : "users",
			dataType : 'json',
			type : 'post',
			data : { username: username, password:password, firstname:firstname, lastname:lastname, email:email, page:page },
			success : function(data) {				
				users = data;
				updateUsers();
			},
			failure: function(err) {
				console.log("There is an error with updating a bleet: " + err);
			}
		});			
	}
	
	clearUserForm = function(){
		$('#username').val("");
		$('#password').val("");
		$('#firstname').val("");
		$('#lastname').val("");
		$('#email').val("");
	}
	
	changeAdmin = function(uid) {
		$.ajax({
			url : "users/" + uid + "/authorities",
			dataType : 'json',
			type : 'put',
			success : function(data) {				
				users = data;
				updateUsers();
			},
			failure: function(err) {
				console.log("There is an error with updating a bleet: " + err);
			}
		});	
	}
	
	getUsers = function() {
		if (page < 0)
			page = 0;
		$.ajax({
			url : "users",
			dataType : 'json',
			data: {page:page},
			type : 'get',
			success : function(data) {				
				users = data;
				updateUsers();
			},
			failure: function(err) {
				console.log("There is an error with retrieving user: " + err);
			}
		});	
	}
	
	pagination = function() {
		if(mode == "home") {
			getBleets();
		}
		else{
			getUsers();
		} 
			
	}

	clearForm = function() {
		$('#bleet').val("");
		$('#privateComment').attr('checked', true);
	}

	updateBleets = function() {
		var bleetRows = $('tr ~ tr');
		bleetRows.remove();
		bleetTable = $('table');
		$(bleets.content)
				.each(
						function(key, val) {
							bleet = val;
							bid = "'"+bleet.id+"'";
							bleetStr = '<tr id=' + bleet.id + '> <td>' + bleet.username + '</td><td>' + bleet.bleet + '</td><td>' + 
							bleet.timestamp + '</td> <td class="blockCell" style="visibility: hidden"><input type="checkbox" onclick="blockFunc('+bid+');"';
							if (bleet.blocked)
								bleetStr += 'checked';
							bleetStr += '></td></tr>';
							bleetTable.append(bleetStr);
						});
		if (isAdmin)
			layoutAdminBleets();
	}
	</script>
</head>
<body background="https://abs.twimg.com/images/themes/theme2/bg.gif">
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/bleeter/home"><span class="glyphicon glyphicon-home"></span>Bleeter</a>
          <span style="display:none;" id="userid">${user.id}</span>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="/Bleeter/login.jsp">Logout</a></li>
            <li><a>Welcome <span id="usernameSpan">${user.username}</span></a></li>
          </ul>
          <form class="navbar-form navbar-right">
            <input type="text" class="form-control" placeholder="Search...">
          </form>
        </div>
      </div>
    </nav>
    
    <div class="container-fluid">
    	<div class="row">
	        <div class="col-md-2">
			    <ul class="nav nav-sidebar">
			      <li class="avatar-circle"><img src="users/${user.id}/avatar" id="avatarImage" class="avatar"></li>
			      <li><h3 id="userid">${user.username}</h3></li>
			    </ul>
	        </div>
			<div class="col-md-10">
				<div class="row" id="addBleets">
					<div class="col-md-2"></div>
					<div class="col-md-10 form-inline" >
						<div class="form-group">
							<label for="bleet">Bleet description</label>
							<input type="text" class="input-lg" id="bleet">
						</div>
						<div class="form-group">
							<label for="privateComment">Make comment private?</label>
							<input type="checkbox" id="privateComment">
						</div>
						<div class="btn btn-sm btn-primary" onclick="addBleet()">Add bleet</div>
						
					</div>
				</div>
				<div class="table-responsive">
					<table class="table table-hover" id="bleetTable">
						<tr id="headerRow"> <th onclick="sort='username'; flipOrder(); getBleets();" >Username</th> <th>Bleet</th> <th onclick="sort='timestamp'; flipOrder(); getBleets();">Date</th> </tr>
					</table>
				</div>
				<div id="footer">
					<nav>
					  <ul class="pagination">
					    <li>
					      <button onclick="page--; pagination();" aria-label="Previous">
					        <span aria-hidden="true">&laquo;</span>
					      </button>
					    </li>
					    <li>
					      <button onclick="page++; pagination();" aria-label="Next">
					        <span aria-hidden="true">&raquo;</span>
					      </button>
					    </li>
					  </ul>
					</nav>
				</div>
			</div>
		</div>
	</div>
</body>
</html>