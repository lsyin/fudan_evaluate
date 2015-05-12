<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">

  <!-- jQuery
    ====================================================================== -->
    <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
     <script src="js/jquery.validate.min.js"></script> 

<style type="text/css">
		body {
	        padding-top: 40px;
	        padding-bottom: 40px;
	        background-color: #f5f5f5;
	     }
		.form-signin {
	        max-width: 500px;
	        padding: 19px 29px 29px;
	        margin: 0 auto 20px;
	        background-color: #fff;
	        border: 1px solid #e5e5e5;
	        -webkit-border-radius: 5px;
	           -moz-border-radius: 5px;
	                border-radius: 5px;
	        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
	           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
	                box-shadow: 0 1px 2px rgba(0,0,0,.05);
	      }
      </style>
<title>Register</title>

<script>
  var msg = "<%=session.getAttribute("message")%>";
  <%session.setAttribute("message", "");%>
  if(msg != null && msg != "" && msg != "null"){	  
	//  alertify.alert(msg);
	    alert(msg);
  }
  </script>

<script>

$.validator.setDefaults({
	submitHandler: function() {
		alert("submitted!");
		form.submit();
	}
});

$().ready(function() {
	
	// validate signup form on keyup and submit
	$("#signupForm").validate({
		rules: {
			userName: {
				required: true,
				minlength: 3
			},
			passWord: {
				required: true,
				minlength: 5
			},
			confirmPassWord: {
				required: true,
				minlength: 5,
				equalTo: "#passWord"
			},
			emailAddress: {
				required: true,
				email: true
			},
			company:{
				required:true,
				minlength:5				
			}
						
		},
		messages: {		
			userName: {
				required: "Please enter a username",
				minlength: "Your username must consist of at least 3 characters"
			},
			passWord: {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long"
			},
			confirmPassWord: {
				required: "Please provide a password",
				minlength: "Your password must be at least 5 characters long",
				equalTo: "Please enter the same password as above"
			},
			emailAddress: "Please enter a valid email address",			
			company:{
				required: "Please provide your company",
				minlength: "The company must be at least 5 characters long"
			}
		
			
		}
	});
	
	
});

</script>
</head>
<body>
<div class="container">
		<form class="form-horizontal form-signin" id="signupForm" method="POST" name="frmRegister" action="<%=response.encodeURL("register.do")%>">
			<legend class="text-center">Sign up</legend>
			<div class="control-group">
				<label class="control-label">
					username
				</label>
				<div class="controls">
					<input type="text" id="userName" name="userName" size="20" maxlength="20"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">
					password
				</label>
				<div class="controls">
					<input type="password" id="passWord" name="passWord" size="20" maxlength="20"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">
					confirm password
				</label>
				<div class="controls">
					<input type="password" id="confirmPassWord" name="confirmPassWord" size="20" maxlength="20"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">
					email
				</label>
				<div class="controls">
					<input type="text" id="emailAddress" name="emailAddress" size="20" maxlength="20" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">
					company
				</label>
				<div class="controls">
					<input type="text" id="company" name="company" size="20" maxlength="20" />
				</div>
			</div>
			<div class="form-actions">
				<button class="btn btn-primary" type="submit" name="Submit">submit</button>
				<button class="btn" type="button" name="Cancel"  onClick="window.location.href='index.jsp'">cancel</button>
			</div>
		</form>
</body>
</html>