/**
 * 
 */

user = {};
$(document).ready(function() {
	userid = $('#userid').html();
	$.ajax({
		url : "users/" + userid,
		dataType : 'json',
		type : 'get',
		success : function(data) {				
			user = data;
			updateBleets();
		},
		failure: function(err) {
			console.log("There is an error with retrieving user: " + err);
		}
	});	
});

addBleet = function() {		
	bleet = $('#bleet').val();
	privateCommment = $('#privateComment').val();
	clearForm();			
	$.ajax({
		url : "users/" + userid + "/bleets",
		dataType : 'json',
		type : 'post',
		data : { bleet : bleet, privateComment : privateComment },
		success : function(data) {				
			user = data;
			updateBleets();
		},
		failure: function(err) {
			console.log("There is an error with adding a bleet: " + err);
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

clearForm = function() {
	$('#bleet').val("");
	$('#privateComment').val("");
}

updateBleets = function() {
	var bleetList = $('#bleetList');
	bleetList.empty();
	$(user.bleets)
			.each(
					function(key, val) {
						var bleetDiv = $('<div class="">');
						bleetDiv
								.append('<div class="media-left media-top"><a href="' + val.url + '"><img class="thumb media-object" src="' + val.url + '"></a></div>');
						bleetDiv
								.append('<div class="media-body">'
										+ val.comment
										+ '<div onclick="deletebleet(\''
										+ val.id
										+ '\')" class="pull-right btn btn-xs btn-warning">&times;</div></div>');
						bleetDiv.appendTo(bleetList);
					});
}