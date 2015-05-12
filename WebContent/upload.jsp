<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload your file</title>
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
<link href="css/fine-uploader-new.min.css" rel="stylesheet">
<link href="css/square/blue.css" rel="stylesheet">
 
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- jQuery
    ====================================================================== -->
   <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>

    <!-- Fine Uploader jQuery JS file
    ====================================================================== -->
    <script src="js/jquery.fine-uploader.min.js"></script> 
    
     <!-- iCheck plugin
    ====================================================================== --> 
    <script src="js/icheck.min.js"></script>
    
      <!-- alertify  plugin,make the ugly alert to beauty
 
    <script src="js/alertify.min.js"></script>
    <link rel="stylesheet" href="css/alertify.core.css" />
    <link rel="stylesheet" href="css/alertify.default.css" />
    ====================================================================== --> 
                      
    <!-- Fine Uploader Thumbnails template w/ customization
     ====================================================================== -->
     
   <script type="text/template" id="qq-template-manual-trigger">
        <div class="qq-uploader-selector qq-uploader" qq-drop-area-text="Drop files here">
            <div class="qq-total-progress-bar-container-selector qq-total-progress-bar-container">
                <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-total-progress-bar-selector qq-progress-bar qq-total-progress-bar"></div>
            </div>
            <div class="qq-upload-drop-area-selector qq-upload-drop-area" qq-hide-dropzone>
                <span class="qq-upload-drop-area-text-selector"></span>
            </div>
            <div class="buttons">
                <div class="qq-upload-button-selector qq-upload-button">
                    <div>Select files</div>
                </div>
                <button id="trigger-upload" class="btn btn-primary">
                    <i class="icon-upload icon-white"></i> Upload
                </button>
            </div>
            <span class="qq-drop-processing-selector qq-drop-processing">
                <span>Processing dropped files...</span>
                <span class="qq-drop-processing-spinner-selector qq-drop-processing-spinner"></span>
            </span>
            <ul class="qq-upload-list-selector qq-upload-list" aria-live="polite" aria-relevant="additions removals">
                <li>
                    <div class="qq-progress-bar-container-selector">
                        <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-progress-bar-selector qq-progress-bar"></div>
                    </div>
                    <span class="qq-upload-spinner-selector qq-upload-spinner"></span>
                    <img class="qq-thumbnail-selector" qq-max-size="100" qq-server-scale>
                    <span class="qq-upload-file-selector qq-upload-file"></span>
                    <span class="qq-edit-filename-icon-selector qq-edit-filename-icon" aria-label="Edit filename"></span>
                    <input class="qq-edit-filename-selector qq-edit-filename" tabindex="0" type="text">
                    <span class="qq-upload-size-selector qq-upload-size"></span>
                    <button class="qq-btn qq-upload-cancel-selector qq-upload-cancel">Cancel</button>
                    <button class="qq-btn qq-upload-retry-selector qq-upload-retry">Retry</button>
                    <button class="qq-btn qq-upload-delete-selector qq-upload-delete">Delete</button>
                    <span role="status" class="qq-upload-status-text-selector qq-upload-status-text"></span>
                </li>
            </ul>

            <dialog class="qq-alert-dialog-selector">
                <div class="qq-dialog-message-selector"></div>
                <div class="qq-dialog-buttons">
                    <button class="qq-cancel-button-selector">Close</button>
                </div>
            </dialog>

            <dialog class="qq-confirm-dialog-selector">
                <div class="qq-dialog-message-selector"></div>
                <div class="qq-dialog-buttons">
                    <button class="qq-cancel-button-selector">No</button>
                    <button class="qq-ok-button-selector">Yes</button>
                </div>
            </dialog>

            <dialog class="qq-prompt-dialog-selector">
                <div class="qq-dialog-message-selector"></div>
                <input type="text">
                <div class="qq-dialog-buttons">
                    <button class="qq-cancel-button-selector">Cancel</button>
                    <button class="qq-ok-button-selector">Ok</button>
                </div>
            </dialog>
        </div>
    </script>
 
     <style>
        #trigger-upload {
            color: white;
            background-color: #00ABC7;
            font-size: 14px;
            padding: 7px 20px;
            background-image: none;
        }

        #fine-uploader-manual-trigger .qq-upload-button {
            margin-right: 15px;
        }

        #fine-uploader-manual-trigger .buttons {
            width: 36%;
        }

        #fine-uploader-manual-trigger .qq-uploader .qq-total-progress-bar-container {
            width: 60%;
        }
        
         .boxs{
	       margin-bottom:25px;
		   margin-left:40px;		   	    	
		}
		.txtarea{	
		   margin-left:20px;  
		   width:50%;
		   height:71px; 
		}	
    </style> 

</head>
<body>
<%@ include file="header.jspf"%>
<hr />

<div style="margin-left:20px;margin-top:30px;">
<h4>This is the upload page.You can upload your test file on this page.Please regist/login before your upload.Thanks. </h4>  
</div>

    <!-- Fine Uploader DOM Element
      ====================================================================== -->
<form id="qq-form">
    <div class="container-fluid">
    	<div class="row-fluid">
	    	<div class="span12">
		    	<div class="alert alert-error">				
						 <strong>Attention:</strong> Please select the track.
		 	   </div>
		    </div>
	   </div>
    </div>	
    <div class="boxs">	
  		<input type="radio" name="optionsRadios"  value="open" />open		
  		<input type="radio" name="optionsRadios"  value="semi-open" />semi-open 		 
  		<input type="radio" name="optionsRadios"  value="closed" />closed
    </div>
	    
    <div class="container-fluid">
    	<div class="row-fluid">
	    	<div class="span12">    
		    	<div class="alert alert-error">				
						 <strong>Attention:</strong> Please select the type.
		 	   </div>
		   </div>
	   </div>
    </div> 
 	<div class="boxs">  
  		 <input type="radio" name="type"  value="seg" />seg
   		 <input type="radio" name="type"  value="pos" />pos
     </div>
	
    <div class="container-fluid">
    	<div class="row-fluid">
	    	<div class="span12">     
		    	<div class="alert alert-info">				
						 <strong>Tips:</strong>Descriptions of your method.
		 	   </div>
		   </div>
	   </div>
    </div>

    <textarea class="txtarea" name="description"  ></textarea>
    	   
</form>
   
    <div id="my-uploader"></div>  
    <div id="fine-uploader-manual-trigger"></div> 
    
                 
    <!-- Your code to create an instance of Fine Uploader and bind to the DOM/template
    ====================================================================== -->
    <script>  
    $(document).ready(function(){
  	  $('input').iCheck({
  	    checkboxClass: 'icheckbox_square-blue',
  	    radioClass: 'iradio_square-blue',
  	    increaseArea: '20%' // optional
  	  });
  	});      
    
        $('#fine-uploader-manual-trigger').fineUploader({
        	element: document.getElementById('my-uploader'),
            template: 'qq-template-manual-trigger',
            request: {
                endpoint: '<%=response.encodeURL("UploadReceiver.do")%>',
                params: {
                	userName:'<%=session.getAttribute("userName")%>',
                	userId:'<%=session.getAttribute("userid")%>'
                }
            },
            thumbnails: {
                placeholders: {
                    waitingPath: 'source/placeholders/waiting-generic.png',
                    notAvailablePath: 'source/placeholders/not_available-generic.png'
                }
            },
            callbacks: {
                onError: function(id, name, errorReason, xhrOrXdr) {               	 
                	 alert(qq.format("Error upload file {}.  Reason: {}",  name, errorReason));
                }
            },
            autoUpload: false
        });

        $('#trigger-upload').click(function() {  
        	if("<%=session.getAttribute("userName")%>"=="null")
        	{      	
        		alert("Please login");       		   
        		window.location.href="login.jsp";        		          	   	
            	return;	
        	}
        	
        	var checkRadio1=$('input:radio[name="optionsRadios"]:checked').val();
        	if (typeof(checkRadio1) == "undefined") { 
        		 alert("please select option"); 
        		   return;
        		} 
        	
        	var checkRadio2=$('input:radio[name="type"]:checked').val();
        	if (typeof(checkRadio2) == "undefined") {        		   
        		   alert("please select type");
        		   return;
        		}
        	
        	$('#fine-uploader-manual-trigger').fineUploader('uploadStoredFiles');
        });
    </script>        
</body>
</html>