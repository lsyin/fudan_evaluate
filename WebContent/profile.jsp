<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>profile</title>
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
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
</head>
<body>
	<div class="container">
		<form class="form-horizontal form-signin" method="POST" name="frmProfile" action="<%=response.encodeURL("profile.do")%>">
			<legend class="text-center">profile</legend>
			<div class="control-group">
				<label class="control-label">
					userName
				</label>
				<div class="controls">
					<label class="control-label">
						<span class="input-xlarge undeitable-input">
							${sessionScope.userName}
						</span>
					</label>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">
					realName
				</label>
				<div class="controls">
					<input type="text" name="realName" size="20" maxlength="20" value="${requestScope.realName}"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">
					email
				</label>
				<div class="controls">
					<input type="text" name="emailAddress" size="20" maxlength="20" value="${requestScope.emailAddress}"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">
					company
				</label>
				<div class="controls">
					<input type="text" name="company" size="20" maxlength="20" value="${requestScope.company}"/>
				</div>
			</div>
	
			<div class="control-group">
				<label class="control-label">
					telephone
				</label>
				<div class="controls">
					<input type="text" name="telephone" size="20" maxlength="20" value="${requestScope.telephone}"/>
				</div>
			</div>
			
			<div class="form-actions">
				<button class="btn btn-primary" type="submit" name="Submit" onClick="return validate()">submit update</button>
				<button class="btn" type="button" name="Cancel"  onClick="window.location.href='<%=response.encodeURL("index.jsp")%>'">cancel</button>
			</div>
		</form>
   	</div>

</body>
</html>