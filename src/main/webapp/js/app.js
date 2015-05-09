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
	$(document).ready(function() {
		userid = $('#userid').html();
		getUser();
		$('#myModal').on('show.bs.modal', function (event) {
			  var button = $(event.relatedTarget) // Button that triggered the modal
			  var modal = $(this)
			  //modal.find('.modal-title').text('New message to ' + recipient)
			  //modal.find('.modal-body input').val(recipient)
			})
		searchFunc = getAllBleets;
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
			url : "bleets/" + bid + "/block",
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
				getAllBleets();
			},
			failure: function(err) {
				console.log("There is an error with retrieving user: " + err);
			}
		});	
	}
	
	getAllBleets = function() {
		if(page <0)
			page=0;
		$.ajax({
			url : "bleets",
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
		$('tr').remove();
		table = $('table');
		table.append('<tr id="headerRow"> <th>Username</th> <th>Name</th> <th>Email</th><th>Admin</th></tr>');
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
			searchFunc();
		}
		else{
			searchFunc();
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
							bleetStr += '></td></tr>';
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
	
	search = function() {
		usr = $('#searchUsername').val();
		beforeDate = $('#beforeDate').val();
		afterDate = $('#afterDate').val();
		if (usr || beforeDate || afterDate){
			searchUserRange(usr, beforeDate, afterDate);
		}
		else {
			getAllBleets();
		}
	}
	
	searchUserRange = function(usr, beforeDate, afterDate) {
		if (page < 0)
			page = 0;
		$.ajax({
			url : "bleets",
			dataType : 'json',
			data: { page: page, username:usr, before:beforeDate, after:afterDate},
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
