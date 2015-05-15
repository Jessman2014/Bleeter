/**
 * 
 */
	user = {};
	users = {};
	bleets = {};
	page = 0;
	sort = "username";
	order = "asc";
	isAdmin = false;
	isUser = false;
	mode = "home";
	length = 0;
	userid= "";
	$(document).ready(function() {
		userid = $('#userid').html();
		$('#newUserButton').hide();
		getUser();
		$('#changeUser').on('show.bs.modal', function (event) {
			  var button = $(event.relatedTarget) // Button that triggered the modal
			  var modal = $(this);
			});
		$('#newUser').on('show.bs.modal', function (event) {
			  var button = $(event.relatedTarget) // Button that triggered the modal
			  var modal = $(this)
			});
		$('#changeBleet').on('show.bs.modal', function (event) {
			  var button = $(event.relatedTarget) // Button that triggered the modal
			  var modal = $(this);
			});
		$('#newBleet').on('show.bs.modal', function (event) {
			  var button = $(event.relatedTarget) // Button that triggered the modal
			  var modal = $(this)
			});
	});
	
	layoutUser = function() {
		
		$('.navbar-header')
			.append('<a class="navbar-brand" href="#" onclick="layoutUserBleets()"><span class="glyphicon glyphicon-tasks"></span>My Bleets</a>');
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
			url : "bleets/" + bid + "/block",
			dataType : 'json',
			type : 'put',
			success : function(data) {				
				pagination();
			},
			failure: function(err) {
				console.log("There is an error with updating a bleet: " + err);
			}
		});	
	}
	
	function flipOrder(sort) {
		sort = sort;
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
				getAllBleets();
			},
			failure: function(err) {
				console.log("There is an error with retrieving user: " + err);
			}
		});	
	}

	addBleet = function() {		
		bleet = $('#bleet').val();
		checked = $('#privateComment:checked').length === 1;
		clearForm();			
		$.ajax({
			url : "users/" + userid + "/bleets",
			dataType : 'json',
			type : 'post',
			data : { bleet : bleet, privatecomment : checked },
			success : function(data) {				
				pagination();
			},
			failure: function(jqXHR, textStatus, errorThrown) {
				console.log("There is an error with adding a bleet: " + errorThrown);
			}
		});					
	}

	changeBleet = function(id) {		
		bleet = $('#bleet').val();
		checked = $('#changePrivateComment:checked').length === 1;
		clearForm();			
		$.ajax({
			url : "users/" + userid + "/bleets" + id,
			dataType : 'json',
			type : 'put',
			data : { bleet : bleet, privateComment : checked },
			success : function(data) {				
				pagination();
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
				pagination();
			},
			failure : function(err) {
				console.log("There is an error with adding a bleet: " + err);
			}
		});
	}
	
	layoutUsers = function() {
		mode = "users";
		$('#newUserButton').show();
		$('#newBleetButton').hide();
		resetPage();
		$('tr').remove();
		table = $('table');
		table.append('<tr id="headerRow"> <th>Username</th> <th>Name</th> <th>Email</th><th>Admin</th></tr>');
		getUsers();
	}
	
	layoutUsersBleets = function() {
		mode = "usersbleets";
		$('#newUserButton').hide();
		$('#newBleetButton').show();
		resetPage();
		$('tr').remove();
		table = $('table');
		table.append('<tr id="headerRow"> <th onclick="flipOrder(username); pagination();" >Username</th>'+
				' <th>Bleet</th> <th onclick="flipOrder(timestamp); pagination();">Date</th> </tr>');
		getUsers();
	}
	
	updateUsers = function() {
		userRows = $('tr ~ tr');
		userRows.remove();
		table = $('table');
		length = users.totalElements;
		calcPages();
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
					str += '></td><td><button type="button" class="btn btn-primary" onclick="setUser('+uid+')" data-toggle="modal" data-target="#changeUser"><span class="glyphicon glyphicon-pencil"></span>Edit</button></td></tr>';
					table.append(str);
				});
	}
	
	setUser = function(uid) {
		$.ajax({
			url : "users/" + uid,
			dataType : 'json',
			type : 'get',
			success : function(data) {				
				$('#changeFirstname').val(data.firstName);
				$('#changeLastname').val(data.lastName);
				$('#changeUsername').val(data.username);
				$('#changeEmail').val(data.email);
				$('#changePassword').val(data.password);
			},
		});	
	}
	
	setBleet = function(bid) {
		$.ajax({
			url: "users/" + userid + "/bleets/" + bid,
			dataType: "json",
			type: "get",
			success: function(data) {
				$('#changeBleet').val(data.bleet);
				$('#changePrivateComment').attr('checked', data.privateComment);
			}
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
			data : { username: username, password:password, firstname:firstname, lastname:lastname, email:email },
			success : function(data) {				
				pagination();
			},
			failure: function(err) {
				console.log("There is an error with updating a bleet: " + err);
			}
		});			
	}
	
	changeUser = function(uid) {
		username = $('#changeUsername').val();
		password = $('#changePassword').val();
		firstname = $('#changeFirstname').val();
		lastname = $('#changeLastname').val();
		email = $('#changeEmail').val();
		$.ajax({
			url: "users/" + uid,
			dataType: "json",
			type: "put",
			data: "firstname=" + firstname + "&lastname=" + lastname + "&email=" + email + 
			"&username=" + username + "&password=" + password,
			success: function(data) {
				pagination();
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
				pagination();
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
		if(mode == "users") {
			getUsers();
		}
		else if (mode == "usersbleets") {
			searchUserBleets();
		}
		else{
			searchAllBleets();
		} 
			
	}

	clearForm = function() {
		$('#bleet').val("");
		$('#privateComment').attr('checked', false);
	}

	updateBleets = function() {
		var bleetRows = $('tr ~ tr');
		bleetRows.remove();
		bleetTable = $('table');
		length = bleets.totalElements;
		calcPages();
		$(bleets.content)
				.each(
						function(key, val) {
							bleet = val;
							bid = "'"+bleet.id+"'";
							bleetStr = '<tr id=' + bleet.id + '> <td>' + bleet.username + '</td><td>' + bleet.bleet + '</td><td>' + 
							bleet.timestamp + '</td> <td class="blockCell" style="visibility: hidden"><input type="checkbox" onclick="blockFunc('+bid+');"';
							if (bleet.blocked)
								bleetStr += 'checked';
							bleetStr += '></td><td><button type="button" class="btn btn-primary" onclick="setBleet('+bid+')" data-toggle="modal" data-target="#changeBleet"><span class="glyphicon glyphicon-pencil"></span>Edit</button></td></tr>';
							bleetTable.append(bleetStr);
						});
		if (isAdmin)
			layoutAdminBleets();
	}
	
	calcPages = function() {
		$('ul.pagination li').not(':first').not(':last').remove();
		for(i=0;i<length/10;i+=1){
			$('ul.pagination li:last').before('<li><a href="#" onclick="page=' + i + ';pagination();">'+(i+1)+'</a><li>');
		}
	}
	
	searchAllBleets = function() {
		usr = $('#searchUsername').val();
		beforeDate = $('#beforeDate').val();
		afterDate = $('#afterDate').val();
		if (page < 0)
			page = 0;
		$.ajax({
			url : "bleets",
			dataType : 'json',
			data: { page: page, username:usr, before:beforeDate, after:afterDate, sort:sort, order: order},
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
	
	searchUserBleets = function () {
		beforeDate = $('#beforeDate').val();
		afterDate = $('#afterDate').val();
		if (page < 0)
			page = 0;
		$.ajax({
			url : "users/" + userid + "/bleets",
			dataType : 'json',
			data: { page: page, before:beforeDate, after:afterDate, sort:sort, order: order},
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
	
