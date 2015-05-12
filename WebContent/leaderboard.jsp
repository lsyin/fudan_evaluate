<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>LeaderBoard</title>
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
 <!-- jQuery
    ====================================================================== -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    
</head>
<body>
<%@ include file="header.jspf"%>
<hr />
 <div id="testtable"></div>  
 
 <script>	 
 $(document).ready(function() {
		var _url = "getScore.do?type=seg";
		jQuery.ajax({
			url : _url,
			type : "get",
			dataType : "json",
			success : function(d) {				
				
					        	//动态生成table
						    var mongotable=$("<table border=\"1\"></table>");
						    mongotable.appendTo("#testtable"); 
						   for(var o in d){
								//生成tr,添加到table中
								var tr=$("<tr></tr>");
					                        tr.appendTo(mongotable);
								//生成td
								var td_userName=$("<td width=\"150\">"+d[o].userName+"</td>");
								var td_type=$("<td width=\"150\">"+d[o].type+"</td>");
								var td_or=$("<td width=\"150\">"+d[o].optionsRadios+"</td>");
								var td_fb1=$("<td width=\"150\">"+d[o].fb1+"</td>");
								
								//把td添加到tr中
								td_userName.appendTo(tr);
								td_type.appendTo(tr);
								td_or.appendTo(tr);
								td_fb1.appendTo(tr);
							}							
						}
					});
				})
	
 </script>
 
</body>
</html>