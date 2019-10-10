<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="<%=path%>/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/global.js"></script>


<html>
<head>
<script language="javascript">
function refreshCode(){
	var codeImage = document.getElementById("codeImage");
	codeImage.src= "<%=path%>/admin/image.jsp?data="+Math.random();
}

function doLogin(){	
	 $("#button1").attr("disabled",true); 	

	   var formData = $("#form1").serializeArray();
       var loginURL = "<%=path%>/servlet/LoginServlet?task=login&background=0";

		

       $.post(loginURL,formData,function(jsonData){
       if(jsonData != null){
       
       		var flag = jsonData.flag;
       		var message = jsonData.message;

	 	 if(flag==true){ 
		var mainURL = "<%=path%>/admin/index.jsp";
		window.location.href=mainURL;		
		  $("#button1").attr("disabled",false); 	
		
				
	 	 }
	 	 else{ 
	 	 window.alert("操作失败,原因为"+message);
	 	 
	 	  $("#button1").attr("disabled",false); 
	 	  $("#codeImage").click();
	 	  } 
	 	  
	 	 }
 
       },"json");


}

</script>

    <meta charset="UTF-8">
    <title>后台管理</title>
    <link href="<%=path%>/css/admin_login.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="admin_login_wrap">
    <h1>后台管理</h1>
    <div class="adming_login_border">
        <div class="admin_input">
            <form id="form1" name="form1" action="index.html" method="post">
                <ul class="admin_items">
                    <li>
                        <label for="user">用户名：</label>
                        <input type="text" name="username"  id="username" size="40" class="admin_input_style" />
                    </li>
                    <li>
                        <label for="pwd">密码：</label>
                        <input type="password" name="password" id="password" size="40" class="admin_input_style" />
                    </li>
                     <li>
                        <label for="pwd">验证码：</label>
                        <input type="text" name="validata_code" id="validata_code" size="20" class="admin_input_style" />
                        <img src="<%=path%>/admin/image.jsp" style="cursor: pointer;" id="codeImage"  onclick="refreshCode();"/>
                    </li>
                    <li>
                        <input type="button" id="button1" name="button1" tabindex="3" value="登录" class="btn btn-primary"  onclick="doLogin();"/>
                    </li>
                </ul>
            </form>
        </div>
    </div>
    <p class="admin_copyright"><a tabindex="5" href="<%=path%>/index.jsp">返回首页</a></p>
</div>
</body>
</html>