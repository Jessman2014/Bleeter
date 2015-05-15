<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-2.1.3.js"></script>
<script	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script>
$(document).ready(function() {
	if(window.location.href.indexOf("?error=1") > -1) {
		$('span.label-warning').show();
	}
	else {
		$('span.label-warning').hide();
	}
});
</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<h1>Please sign in</h1>
				<span class="label label-warning">Incorrect username or password. Please try again.</span>
				<form action="login" method="POST">
					<input type="text" name="username" id="username" class="form-control">
					<input type="password" name="password" id="password" class="form-control">
					<input class="btn btn-sm btn-primary" type="submit">
				</form>
			</div>
		</div>
	</div>
</body>
</html>