<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/front/front_head.jsp" %>
<c:import url="/front/user/front_top.jsp"></c:import>



<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-form.js"></script>


<script language="javascript">

	
		function checkpass(){
			pass = $("#newpassword").val();
			pass1 = $("#newpassword1").val();
			var span_pass = document.getElementById("span_pass");
			if(pass!=pass1)
			{
				span_pass.innerHTML ="<font color='red'>密码不一致请重新输入</font>";
			}
			else if(pass==""||pass.length==0||(pass1==""||pass1.length==0)){
				span_pass.innerHTML = "<font color='red'>密码不能为空";
			}
			else
			span_pass.innerHTML = "";
		}


 function saveData(){
    
    
    
    if($("#span_pass").html()== null||$("#span_pass").html()==""){
    
     $("#btn_submit").attr("disabled",true);
      var formData =  $("#form2").serializeArray();
      var editformURL = "<%=path%>/servlet/UserServlet?task=editform";
        $.post(editformURL,formData,function(jsonData){
    	if(jsonData != null){
       		var flag = jsonData.flag;
       		var message = jsonData.message;
		
	 	 if(flag==true){
	 	 window.alert("修改成功");
	 	 
	 	 window.location.href="<%=path%>/servlet/LoginServlet?task=logout";
	 	 
 	 	  $("#button").attr("disabled",false);
 			
				}	
          else{
	 	 window.alert("操作失败,原因为"+message);
	 	  $("#button").attr("disabled",false);
	 	 
	 	 }	
				}
        },"json");
    
    
    
    }
    
    
    
   
	
} 

</script>




<div id="mainBody">
	<div class="container">
	<div class="row">
<!-- Sidebar ================================================== -->
<%@ include file="/front/user/front_left.jsp" %>
<!-- Sidebar end=============================================== -->
	<div class="span9">
    <ul class="breadcrumb">
		<li><a href="${pageContext.request.contextPath}">主页</a> <span class="divider">/</span></li>
		<li class="active">用户密码修改</li>
    </ul>
	<h3>用户密码修改</h3>	
	<hr class="soft"/>
	
	<div class="row">
		<div class="span9" style="min-height:900px">
			<div class="well">
			
			
   <form action="${pageContext.request.contextPath}/servlet/ItemServlet?task=list" method="post" id="form2" enctype="multipart/form-data">
    
    <input type="hidden" name="itemid" value="${requestScope.itemBean.itemid}"/>
    
     <table width="100%" border="0" cellpadding="8" cellspacing="0" class="tableBasic">
 
     <tr>
     <td width="90" align="right">请输入原密码:</td>
     
     <td>
     <input type="password" name="password" id="password"  class="inpMain">
     </td>

     </tr>
         <tr>
     <td width="90" align="right">新密码：</td>
     
     <td>
     <input type="password" name="newpassword" id="newpassword"  class="inpMain">
     </td>

     </tr><tr>
     <td width="90" align="right">确认密码：</td>
     <td>
     <input type="password" name="newpassword1" id="newpassword1" value="" class="inpMain" onblur="checkpass();"  ><span id="span_pass"></span>
     </td>
     </tr>
      <tr>
       <td></td>
       <td>
        <input type="hidden" name="id" value="">
        <input name="submit" id="btn_submit" class="btn" type="button" onclick="saveData();" value="保存数据" />
                <input name="submit" class="btn" type="reset" value="重置数据" />
       </td>
      </tr>
     </table>
    </form>
		</div>
		</div>
	</div>	
	
</div>
</div>
</div>
</div>
<!-- MainBody End ============================= -->
<!-- Footer ================================================================== -->
<%@include file="/front/front_foot.jsp" %>


	