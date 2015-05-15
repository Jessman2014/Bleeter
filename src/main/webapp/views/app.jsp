<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<title>Users</title>
	<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
	<link href="/bleeter/css/app.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-2.1.3.js"></script>	
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	<script src="/bleeter/js/app.js"></script>
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
            <li><a href="/bleeter/login.jsp">Logout</a></li>
            <li><a>Welcome <span id="usernameSpan">${user.username}</span></a></li>
          </ul>
          
        </div>
      </div>
    </nav>
    
    <div class="container-fluid">
    	<div class="row">
	        <div class="col-md-2">
			    <ul class="nav nav-sidebar">
			      <li class="avatar-circle"><img src="users/${user.id}/avatar" id="avatarImage" class="avatar"></li>
			      <li><h3 id="userid">${user.username}<span class="glyphicon glyphicon-pencil"></span></h3></li>
			    </ul>
	        </div>
			<div class="col-md-10">
				<div class="table-responsive">
					<table class="table table-hover" id="bleetTable">
						<tr id="headerRow"> <th onclick="sort='username'; flipOrder(); pagination();" >Username</th> <th>Bleet</th> <th onclick="sort='timestamp'; flipOrder(); pagination();">Date</th> </tr>
					</table>
				</div>
				
				<form class="navbar-form navbar-right">
		        	<input type="text" id="searchUsername" class="form-control" placeholder="Search Usernames...">
		        	<input type="date" id="beforeDate" class="form-control" placeholder="Before Date...">
		        	<input type="date" id="afterDate" class="form-control" placeholder="After Date...">
		        	<span class="glyphicon glyphicon-search" onclick="search();"></span>
		        </form>
				<!-- Button trigger modal -->
				<button type="button" id="newUserButton" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#newUser">
				  Create New User
				</button>
				<button type="button" id="newBleetButton" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#newBleet">
				  Create New Bleet
				</button>
				
				<div class="modal fade" id="changeUser" tabindex="-1" role="dialog" aria-labelledby="changeUserLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="changeUserLabel">Modal title</h4>
				      </div>
				      <div class="modal-body">
				      	<form>
				      		<div class="form-group">
				      			<label for="changeFirstname">Firstname</label>
				      			<input type="text" id="changeFirstname">
				      		</div>
				      		<div class="form-group">
				      			<label for="changeLastname">Lastname</label>
				      			<input type="text" id="changeLastname">
				      		</div>
				      		<div class="form-group">
				      			<label for="changeUsername">Username</label>
				      			<input type="text" id="changeUsername">
				      		</div>
				      		<div class="form-group">
				      			<label for="changeEmail">Email</label>
				      			<input type="email" id="changeEmail">
				      		</div>
				      		<div class="form-group">
					      		<label for="changePassword">Password</label>
					      		<input type="password" id="changePassword">
					      	</div>
					      	<div class="form-group">
				      			<label for="changeAvatar">Change Avatar</label>
				      			<input type="file" id="changeAvatar" name="avatar">
				      		</div>
					      	<div class="btn btn-primary" onclick="changeUser(); addAvatar();">Submit</div>
				      	</form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				        <button type="button" class="btn btn-primary">Save changes</button>
				      </div>
				    </div>
				  </div>
				</div>
				
				<div class="modal fade" id="newUser" tabindex="-1" role="dialog" aria-labelledby="newUserLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="newUserLabel">Modal title</h4>
				      </div>
				      <div class="modal-body">
				      	<form>
				      		<div class="form-group">
				      			<label for="firstname">Firstname</label>
				      			<input type="text" id="firstname">
				      		</div>
				      		<div class="form-group">
				      			<label for="lastname">Lastname</label>
				      			<input type="text" id="lastname">
				      		</div>
				      		<div class="form-group">
				      			<label for="username">Username</label>
				      			<input type="text" id="username">
				      		</div>
				      		<div class="form-group">
				      			<label for="email">Email</label>
				      			<input type="email" id="email">
				      		</div>
				      		<div class="form-group">
					      		<label for="password">Password</label>
					      		<input type="password" id="password">
					      	</div>
					      	<div class="form-group">
				      			<label for="avatar">Change Avatar</label>
				      			<input type="file" id="avatar" name="avatar">
				      		</div>
					      	<div class="btn btn-primary" onclick="addUser(); addAvatar();">Submit</div>
				      	</form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				        <button type="button" class="btn btn-primary">Save changes</button>
				      </div>
				    </div>
				  </div>
				</div>
				
				<div class="modal fade" id="changeBleet" tabindex="-1" role="dialog" aria-labelledby="changeBleetLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="changeBleetLabel">Modal title</h4>
				      </div>
				      <div class="modal-body">
				      	<form>
				      		<div class="form-group">
								<label for="bleet">Bleet description</label>
								<input type="text" class="input-lg" id="changeBleet">
							</div>
							<div class="form-group">
								<label for="privateComment">Make comment private?</label>
								<input type="checkbox" id="changePprivateComment">
							</div>
					      	<div class="btn btn-primary" onclick="changeBleet();">Submit</div>
				      	</form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				        <button type="button" class="btn btn-primary">Save changes</button>
				      </div>
				    </div>
				  </div>
				</div>
				
				<div class="modal fade" id="newBleet" tabindex="-1" role="dialog" aria-labelledby="newBleetLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="newBleetLabel">Modal title</h4>
				      </div>
				      <div class="modal-body">
				      	<form>
				      		<div class="form-group">
								<label for="bleet">Bleet description</label>
								<input type="text" class="input-lg" id="bleet">
							</div>
							<div class="form-group">
								<label for="privateComment">Make comment private?</label>
								<input type="checkbox" id="privateComment">
							</div>
					      	<div class="btn btn-primary" onclick="addBleet();">Submit</div>
				      	</form>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				        <button type="button" class="btn btn-primary">Save changes</button>
				      </div>
				    </div>
				  </div>
				</div>
				<div id="footer">
					<nav>
					  <ul class="pagination">
					    <li>
					      <a href="#" onclick="page--; pagination();" aria-label="Previous">
					        <span aria-hidden="true">&laquo;</span>
					      </a>
					    </li>
					    <li>
					      <a href="#" onclick="page++; pagination();" aria-label="Next">
					        <span aria-hidden="true">&raquo;</span>
					      </a>
					    </li>
					  </ul>
					</nav>
				</div>
			</div>
		</div>
	</div>
</body>
</html>