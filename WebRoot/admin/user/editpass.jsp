<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/common_header.jsp" %>
<c:import url="/admin/common_top.jsp"></c:import>
<c:import url="/admin/common_left.jsp">
<c:param name="menu_id" value="206"></c:param>

</c:import>

<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery-form.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
      var formData =  $("#form1").serializeArray();
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



</div>
<!-- dcHead 结束 --> 

 <div id="dcMain">
   <!-- 当前位置 -->
<div id="urHere">DouPHP 管理中心<b>></b><strong>修改密码</strong> </div>   <div class="mainBox" style="height:auto!important;height:550px;min-height:550px;">
    <form action="${pageContext.request.contextPath}/servlet/ItemServlet?task=list" method="post" id="form1" enctype="multipart/form-data">
    
    <input type="hidden" name="itemid" value="${requestScope.itemBean.itemid}"/>
    
     <table width="100%" border="0" cellpadding="8" cellspacing="0" class="tableBasic">
     
     
     
     
     <tr>
     <td width="90" align="right">请输入原密码</td>
     
     <td>
     <input type="password" name="password" id="password" value="" class="inpMain">
     </td>

     </tr>
         <tr>
     <td width="90" align="right">新密码：</td>
     
     <td>
     <input type="password" name="newpassword" id="newpassword" value="" class="inpMain">
     </td>

     </tr>
              <tr>
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
 <div class="clear"></div>
<div id="dcFooter">
 <div id="footer">
  <div class="line"></div>
  <ul>
   版权所有 © 2013-2015 漳州豆壳网络科技有限公司，并保留所有权利。
  </ul>
 </div>
</div><!-- dcFooter 结束 -->
<div class="clear"></div> </div>
</body>
</html>
