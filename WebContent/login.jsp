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
<title>Login</title>
<script language="javascript">
   function validateLogin(){
    var sUserName = document.frmLogin.userName.value ;
    var sPassword = document.frmLogin.passWord.value ;
    if (sUserName ==""){
    	//alertify.confirm("Please enter your username", function (e) {});
    	alert("Please enter your username");
     return false ;
    }
    
    if (sPassword ==""){
    	//alertify.confirm("Please enter your password", function (e) { });
    	alert("Please enter your password");
     return false ;
    }
   }
   
   var msg = "<%=session.getAttribute("message")%>";
   <%session.setAttribute("message", "");%>
   if(msg != null && msg != "" && msg != "null"){	  
 	//  alertify.alert(msg);
 	    alert(msg);
   }
  </script>
</head>
<body>
<div class="container">
	  <form class="form-horizontal form-signin" method="POST" name="frmLogin" action="<%=response.encodeURL("login.do")%>">
	  	<legend class="text-center">sign in</legend>
	  	<div class="control-group">
			<label class="control-label">
				username
			</label>
			<div class="controls">
				<input type="text" name="userName" size="20" maxlength="20"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">
				password
			</label>
			<div class="controls">
				<input type="password" name="passWord" size="20" maxlength="20"/>
			</div>
		</div>
		  <div class="form-actions">
			  <button class="btn btn-primary" type="submit" name="Submit" onClick="return validateLogin()">Login</button>
			  <button class="btn" type="button" name="Cancel" onClick="window.location.href='index.jsp'">cancel</button>
		  </div>
	  </form>
	</div>
</body>
</html>