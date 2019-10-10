<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<body>
<div id="header">
<div class="container">
<div id="welcomeLine" class="row">
	<div class="span6">欢迎!<strong>${sessionScope.userBean.truename}</strong></div>
	<div class="span6">
	<div class="pull-right">
		<span class="btn btn-mini"  id="price">$
		<c:if test="${sessionScope.cartBean != null}">
		${sessionScope.cartBean.totalprice_str}
		</c:if>
		<c:if test="${sessionScope.cartBean == null}">
		0
		</c:if>
		
		</span>
		<a href="${pageContext.request.contextPath}/servlet/CartServlet?task=list"><span class="btn btn-mini btn-primary" id="count"><i class="icon-shopping-cart icon-white"></i> [<c:if test="${sessionScope.cartBean != null}">${sessionScope.cartBean.totalcount}</c:if><c:if test="${sessionScope.cartBean == null}">
		0
		</c:if>] 物品在您的购物车</span> </a> 
	</div>
	</div>
</div>
<!-- Navbar ================================================== -->
<div id="logoArea" class="navbar">
<a id="smallScreen" data-target="#topMenu" data-toggle="collapse" class="btn btn-navbar">
	<span class="icon-bar"></span>
	<span class="icon-bar"></span>
	<span class="icon-bar"></span>
</a>
  <div class="navbar-inner">
    <a class="brand" href="${pageContext.request.contextPath}/servlet/FrontServlet?task=index"><img src="${pageContext.request.contextPath}/themes/images/logo.png" alt="Bootsshop"/></a>
		<form  class="form-inline navbar-search" method="post" action="products.html" >
		<input id="srchFld" class="srchTxt" type="text" />
		  <select class="srchTxt">
		  
		 <c:forEach var="classinfo" items="${sessionScope.classlist}" varStatus="classStatus">
			
			<option>${classinfo.classname}</option>
			
             </c:forEach>
		</select> 
		  <button type="submit" id="submitButton" class="btn btn-primary">Go</button>
    </form>
    <ul id="topMenu" class="nav pull-right">
	 <li class=""><a href="${pageContext.request.contextPath}/front/user/changeinfo.jsp">会员中心</a></li>
	 <li class=""><a href="${pageContext.request.contextPath}/admin/Login.jsp">后台管理</a></li>
	 <li class=""><a href="${pageContext.request.contextPath}/servlet/LoginServlet?task=logoutfront">退出登录</a></li>
	
	<div id="login" class="modal hide fade in" tabindex="-1" role="dialog" aria-labelledby="login" aria-hidden="false" >
		  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3>登录</h3>
		  </div>
		  <div class="modal-body">
			<form  id="form1" class="form-horizontal loginFrm">
			  <div class="control-group">								
				<input type="text" id="username" name="username" placeholder="账号">
			  </div>
			  <div class="control-group">
				<input type="password" id="password" name="password" placeholder="密码">
			  </div>
			  <div class="control-group">
   			   <input type="text" name="validata_code" id="validata_code" placeholder="验证码"/>
               <img src="${pageContext.request.contextPath}/admin/image.jsp" style="cursor: pointer;" id="codeImage"  onclick="refreshCode();"/>
			    </div>
			  <div class="control-group">
				<label class="checkbox">
				<input type="checkbox">	记住密码
				</label>    
			  </div>
			</form>		
			<button type="button"  onclick="doLogin();"  class="btn btn-success">登录</button>
			<button type="button"  onclick="doRegister();"  class="btn btn-success">注册</button>
			
			<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
		  </div>
	</div>
	</li>
    </ul>
  </div>
</div>
</div>
</div>

<script language="javascript">

function doLogin(){	
	 $("#button1").attr("disabled",true); 	

	   var formData = $("#form1").serializeArray();
       var loginURL = "${pageContext.request.contextPath}/servlet/LoginServlet?task=login";

       $.post(loginURL,formData,function(jsonData){
       if(jsonData != null){
       
       		var flag = jsonData.flag;
       		var message = jsonData.message;

	 	 if(flag==true){ 
		var mainURL = "${pageContext.request.contextPath}/index.jsp";
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

function doRegister(){	
	 $("#button1").attr("disabled",true); 	

	   var formData = $("#form1").serializeArray();
       var loginURL = "${pageContext.request.contextPath}/servlet/LoginServlet?task=register";

       $.post(loginURL,formData,function(jsonData){
       if(jsonData != null){
       
       		var flag = jsonData.flag;
       		var message = jsonData.message;

	 	 if(flag==true){ 
		var mainURL = "${pageContext.request.contextPath}/index.jsp";
		window.alert("注册成功，请登录后前往个人中心填写详细资料");
		
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




function refreshCode(){
	var codeImage = document.getElementById("codeImage");
	codeImage.src= "${pageContext.request.contextPath}/admin/image.jsp?data="+Math.random();
}

</script>





<!-- Header End====================================================================== -->