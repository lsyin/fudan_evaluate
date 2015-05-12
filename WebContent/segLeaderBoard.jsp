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
   <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
    
</head>
<body>
<%@ include file="header.jspf"%>
<hr />
<div style="margin-left:20px;margin-top:30px;">
<h4>We use the standard SIGHAN bake-off scoring program to calculate precision, recall, F1-score and out-of-vocabulary (OOV) word recall.</h4>
<h5>The LeaderBoard show the result of your evaluation.You will find your seg_evaluation result at here.</h5>   
</div>
 <div id="testtable"></div>  
 
 <script>	 
 $(document).ready(function() {
		var _url = "<%=response.encodeURL("getScore.do?type=seg")%>";
		jQuery.ajax({
			url : _url,
			type : "get",
			dataType : "json",
			success : function(d) {				

	        	//动态生成table
		    var mongotable=$("<table  class=\"table table-bordered\" contenteditable=\"false\"></table>");
		    mongotable.appendTo("#testtable"); 
		    //生成标题
		    	var thead=$("<thead></thead>");
	            thead.appendTo(mongotable);	            
		    //生成tr,添加到table中
				var tr=$("<tr></tr>");
	            tr.appendTo(thead);
				//生成td
				var td_userName=$("<th>"+"UserName"+"</th>");
				var td_type=$("<th>"+"Type"+"</th>");
				var td_or=$("<th>"+"Track"+"</th>");
				var td_precision=$("<th>"+"Precision"+"</th>");
				var td_recall=$("<th>"+"Recall"+"</th>");
				var td_fb1=$("<th>"+"F1"+"</th>");
				var td_oov=$("<th>"+"Oov"+"</th>");
				var td_description=$("<th>"+"Description"+"</th>");
				var td_submitTime=$("<th>"+"SubmitTime"+"</th>");
				
				//把td添加到tr中
				td_userName.appendTo(tr);
				td_type.appendTo(tr);
				td_or.appendTo(tr);
				td_precision.appendTo(tr);
				td_recall.appendTo(tr);
				td_fb1.appendTo(tr);
				td_oov.appendTo(tr);
				td_description.appendTo(tr);
				td_submitTime.appendTo(tr);		
			//生成内容   
			var tbody=$("<tbody></tbody>");
			tbody.appendTo(mongotable);	
		   for(var o in d){
				//生成tr,添加到table中
				var tr=$("<tr class=\"info\"></tr>");
	                        tr.appendTo(tbody);
				//生成td
				var td_userName=$("<td width=\"150\">"+d[o].userName+"</td>");
				var td_type=$("<td width=\"150\">"+d[o].type+"</td>");
				var td_or=$("<td width=\"150\">"+d[o].optionsRadios+"</td>");
				var td_precision=$("<td width=\"150\">"+d[o].precision+"</td>");
				var td_recall=$("<td width=\"150\">"+d[o].recall+"</td>");
				var td_fb1=$("<td width=\"150\">"+d[o].fb1+"</td>");
				var td_oov=$("<td width=\"150\">"+d[o].oov+"</td>");
				var td_description=$("<td width=\"150\">"+d[o].description+"</td>");
				var td_submitTime=$("<td width=\"150\">"+d[o].submitTime+"</td>");
				
				//把td添加到tr中
				td_userName.appendTo(tr);
				td_type.appendTo(tr);
				td_or.appendTo(tr);
				td_precision.appendTo(tr);
				td_recall.appendTo(tr);
				td_fb1.appendTo(tr);
				td_oov.appendTo(tr);
				td_description.appendTo(tr);
				td_submitTime.appendTo(tr);			
							}							
						}
					});
				})
	
 </script>
 
</body>
</html>