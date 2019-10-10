<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" session="false"%>
<%@ include file="/admin/common_header.jsp" %>
<c:import url="/admin/common_top.jsp"></c:import>
<c:import url="/admin/common_left.jsp">
<c:param name="menu_id" value="201"></c:param>

</c:import>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <script language="javascript">
$(function(){
      
 $("#button").click(function(){
 
       var formData = $("#form1").serializeArray();
       var saveURL = "<%=path%>/servlet/ClassInfoServlet?task=save";
       var refreshURL = "<%=path%>/servlet/ClassInfoServlet?task=refresh";
       $.post(saveURL,formData,function(jsonData){
       
       
       $("#button").attr("disabled",true);
       
       if(jsonData != null){
       		var flag = jsonData.flag;
       		var message = jsonData.message;

	 	 if(flag==true){
	 	 window.alert("操作成功");
	 	 $("#button").attr("disabled",false);
	 	  var parentid = $("#parentid");
	 	 $("option:gt(0)").remove();
	 	 $.post(refreshURL,function(jsonData){
	 	 
	 	 if(jsonData!=null){
	 	    var classlist = jsonData;
	 	  for (var i=0;i<classlist.length;i++)
          {
        	 	
         $("#parentid").append("<option value='"+classlist[i].classid+"'>"+classlist[i].classname+"</option>")

        }	 	 
	 	 
	 	 }
	 	 
	 	 },"json");
	 	
	 	
	 	
        
	 	     
   
         
	 	 
	 	 
	 	 
	 	 
	 	 
	 	 }
	 	 else{
	 	 window.alert("操作失败,原因为"+message);
	 	  $("#button").attr("disabled",false);
	 	 
	 	 }
       
       
       
       
       
       }
       
       
       
       
       
       
       
       
       },"json");
     
 
 
 });
 
 
 
 });
 
 </script>
 
  
  <body>
<div id="dcWrap">

<!-- dcHead 结束 --> 
 <div id="dcMain">
   <!-- 当前位置 -->
<div id="urHere">DouPHP 管理中心<b>></b><strong>添加分类</strong> </div>   <div class="mainBox" style="height:auto!important;height:550px;min-height:550px;">
            <h3><a href="product_category.php" class="actionBtn">商品分类</a>添加分类</h3>
    <form  id= "form1" action="product_category.php?rec=insert" method="post">
     <table width="100%" border="0" cellpadding="8" cellspacing="0" class="tableBasic">

      <tr>
       <td width="80" align="right">请输入要修改的名称</td>
       <td>
        <input type="text" name="classname" id="classname" value="" size="40" class="inpMain" />
       </td>
      </tr>
      
      <tr>
       <td width="80" align="right">请输入要修改的ID</td>
       <td>
        <input type="text" name="classname" id="classname" value="" size="40" class="inpMain" />
       </td>
      </tr>
     
      <tr>
       <td></td>
       <td>
        <input type="hidden" name="token" value="b9439ae8" />
        <input type="hidden" name="cat_id" value="" />
        <input name="button"id="button" class="btn" type="button" value="提交"  onclick="";  />

        
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