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
						<tr id="headerRow"> <th onclick="sort='username'; flipOrder(); getAllBleets();" >Username</th> <th>Bleet</th> <th onclick="sort='timestamp'; flipOrder(); getAllBleets();">Date</th> </tr>
					</table>
				</div>
				
				<form class="navbar-form navbar-right">
		        	<input type="text" id="searchUsername" class="form-control" placeholder="Search Usernames...">
		        	<input type="date" id="beforeDate" class="form-control" placeholder="Before Date...">
		        	<input type="date" id="afterDate" class="form-control" placeholder="After Date...">
		        	<span class="glyphicon glyphicon-search" onclick="search=true; searchUsername();"></span>
		        </form>
				<!-- Button trigger modal -->
				<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
				  Launch demo modal
				</button>
				
				<!-- Modal -->
				<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
				      </div>
				      <div class="modal-body">
				        ...
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