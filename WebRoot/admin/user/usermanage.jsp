<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/common_header.jsp" %>
<c:import url="/admin/common_top.jsp"></c:import>
<c:import url="/admin/common_left.jsp">
<c:param name="menu_id" value="207"></c:param>
</c:import>

<script language="javascript" type="text/javascript" src="<%=path%>/js/datepicker/WdatePicker.js"></script>


<script language="javascript">
function resetForm(){
  jQuery("#username").val("");
  jQuery("#addtime_start").val("");
  jQuery("#addtime_end").val("");

}
function changeData(userid){	
	
	var freezeURL = "<%=path%>/servlet/UserServlet?task=frezze&userid="+userid;
	var refreshUR = "<%=path%>/servlet/UserServlet?task=usermanage;"
	  $.post(freezeURL,function(jsonData){
	  		if(jsonData != null){
	  		var flag =jsonData.flag;
	  		var message = jsonData.message;
	  		if(flag == true){
			
			window.alert("操作成功");
			window.location.href="<%=path%>/servlet/UserServlet?task=usermanage";	
					  		
	  		}
	  		else{
	  		  window.alert("操作失败原因为："+message);
	  		
	  		}
	  }
	  },"json");
		



}






</script>

<!-- dcHead 结束 --> 
 <div id="dcMain">
   <!-- 当前位置 -->
<div id="urHere">DouPHP 管理中心<b>></b><strong>注册用户管理</strong> </div> 
  <div class="mainBox" style="height:auto!important;height:550px;min-height:550px;">
    <div class="filter">
    <form action="${pageContext.request.contextPath}/servlet/UserServlet?task=usermanage" method="post" style="width:80%;">
    
    <table width="100%" border=0>
      <tr>
      
    <td  align="right">用户名:</td> <td width="25%"> <input align="left"   type="text"  name="username"  id="username"  class="inpMain" value="${param.username}"  style='width:180px;'/></td>
   </tr>
   <tr>
   <td ></td>
   <td align="left" ><input name="submit" id="submit" class="btnGray" type="submit" value="查询" />
    <input name="button2" class="btnGray" type="button" onclick="resetForm();" value="重置"/> 
    </td>
   
   
   
   </tr>
   

    
    </table>
    
 
    </form>
    
    </div>
        <div id="list">
    <form name="action" method="post" action="product.php?rec=action">
    <table width="100%" border="0" cellpadding="8" cellspacing="0" class="tableBasic">
      <tr>
        <th align="left"width="80">用户名</th>
        <th width="80" align="center">真实姓名</th>
       <th width="80" align="center">性别</th>
       <th width="80" align="center">冻结情况</th>

        <th width="80" align="center">操作</th>
      </tr>
      <c:forEach var="userBean" items="${requestScope.userlist}">
        <tr>
        <td>${userBean.username}</td>
        <td align="center">${userBean.truename}</td>
        <td align="center">${userBean.usersex == 0 ? "男":"女"}</td>
        <td align="center"  id="is_freeze${userBean.userid}" name="is_freeze${userBean.userid}" >${userBean.is_freeze == 0 ? "正常":"已冻结"}  </td>
        

        <td align="center">
        <a href="javascript:void(0);"  id="edit${userBean.userid}" name="edit${userBean.userid}"  onclick="changeData(${userBean.userid});">${userBean.is_freeze == 0 ? "冻结":"解冻"} </a> 
        </td>
      </tr>
       </c:forEach>

          </table>
    <div class="clear"></div>
    <div class="pager">
    ${requestScope.pageTool}
    </div>
    
    
 </div>

</body>
</html>
    
    