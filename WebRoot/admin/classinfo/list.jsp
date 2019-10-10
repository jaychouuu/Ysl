<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" session="false"%>
<%@ include file="/admin/common_header.jsp" %>
<c:import url="/admin/common_top.jsp"></c:import>
<c:import url="/admin/common_left.jsp">
<c:param name="menu_id" value="201"></c:param>

</c:import>
<script language="javascript">

function deleteData(classid,parentid){
var refreshURL = "<%=path%>/servlet/ClassInfoServlet?task=refresh";

 var message= "";
  if(parentid == 0){
  
  
     message = "删除大类别会级联删除小类别，确定执行删除类别的操作？";

  
  }
  else{
  message = "确定要删除选中的类别？";

  }
	var confirmFlag = window.confirm(message);
	if(confirmFlag == true)
	{
	  var delURL = "<%=path%>/servlet/ClassInfoServlet?task=delete&classid="+classid+"&parentid="+parentid;
	  jQuery.get(delURL,function(jsonData){
	
	        var flag = jsonData.flag;
	        var message = jsonData.message;
	        
	        if(flag == true){
	        
	        	     window.alert("删除成功");
	         $.post(refreshURL,function(jsonData){
	 	 
	 	 if(jsonData!=null){
	 	    var classlist = jsonData;
	 	     $("#table tr:gt(0)").remove();
	 	  for (var i=0;i<classlist.length;i++)
          {     	 	
         $("#tbody1").append("<tr><td align='left'>"+ classlist[i].classid+"</td><td>"+ classlist[i].classname+"</td><td align='center'>"+ classlist[i].parentid+"</td> <td align='center'><a href=\"javascript:void(0);\" onclick=\"disp_prompt('"+classlist[i].classname+"',"+classlist[i].classid+","+classlist[i].parentid+");\">编辑</a> | <a href=\"javascript:void(0);\" onclick=\"deleteData("+classlist[i].classid+","+classlist[i].parentid+");\">删除</a></td></tr>");
  
for(var j=0;j<classlist[i].childList.length;j++){
 $("#tbody1").append("<tr><td align='left'>"+classlist[i].childList[j].classid+"</td><td>"+classlist[i].childList[j].classname+"</td><td align='center'>"+classlist[i].childList[j].parentid+"</td><td align='center'><a href=\"javascript:void(0);\" onclick=\"disp_prompt('"+classlist[i].childList[j].classname+"',"+classlist[i].childList[j].classid+","+classlist[i].childList[j].parentid+");\">编辑</a> | <a href=\"javascript:void(0);\" onclick=\"deleteData("+classlist[i].childList[j].classid+","+classlist[i].childList[j].parentid+");\">删除</a></td></tr>");
         
         }
        }	 	 
	 	 }
	 	 
	 	 },"json"); 
	        
	        }
	     else{
	     
	     window.alert("删除操作失败,异常原因="+message);
	     }
	  
	  
	  
	  },"json");
	
	
	} 



}


function disp_prompt(oldclassname,oldclassid,parentid)

{

var classname=prompt("请更改商品名称",oldclassname);
var classid =prompt("请更改id",oldclassid);
var refreshURL = "<%=path%>/servlet/ClassInfoServlet?task=refresh";
if (classname!=null&&classname!=""&&classid!=null&&classid!="")

{

  var changeURL = "<%=path%>/servlet/ClassInfoServlet?task=change";
  
  
   var jsontodata = {
  
  "classname":classname,
  "classid":classid,
  "oldclassname":oldclassname,
  "oldclassid":oldclassid,
  "parentid":parentid

  }; 
  
  
 $.post(changeURL,jsontodata,function(jsonData){
if(jsonData != null){
       		var flag = jsonData.flag;
       		var message = jsonData.message;
 if(flag==true){
	 	 window.alert("操作成功");
	 	  
	   $.post(refreshURL,function(jsonData){
	 	 
	 	 if(jsonData!=null){
	 	    var classlist = jsonData;
	 	    $("#table tr:gt(0)").remove();
	 	  for (var i=0;i<classlist.length;i++)
          {     	 	
         $("#tbody1").append("<tr><td align='left'>"+ classlist[i].classid+"</td><td>"+ classlist[i].classname+"</td><td align='center'>"+ classlist[i].parentid+"</td> <td align='center'><a href=\"javascript:void(0);\" onclick=\"disp_prompt('"+classlist[i].classname+"',"+classlist[i].classid+","+classlist[i].parentid+");\">编辑</a> | <a href=\"javascript:void(0);\" onclick=\"deleteData("+classlist[i].classid+","+classlist[i].parentid+");\">删除</a></td></tr>");
  
   for(var j=0;j<classlist[i].childList.length;j++){
     $("#tbody1").append("<tr><td align='left'>"+classlist[i].childList[j].classid+"</td><td>"+classlist[i].childList[j].classname+"</td><td align='center'>"+classlist[i].childList[j].parentid+"</td><td align='center'><a href=\"javascript:void(0);\" onclick=\"disp_prompt('"+classlist[i].childList[j].classname+"',"+classlist[i].childList[j].classid+","+classlist[i].childList[j].parentid+");\">编辑</a> | <a href=\"javascript:void(0);\" onclick=\"deleteData("+classlist[i].childList[j].classid+","+classlist[i].childList[j].parentid+");\">删除</a></td></tr>");
         }



        }	 	 
        

	 	 
	 	 }
	 	 
	 	 },"json"); 
	 	 
 
  }
	 	 else{
	 	 window.alert("操作失败,原因为"+message);
	 	 }
 }
 },"json");


}
}



</script>


<!-- dcHead 结束 --> 
 <div id="dcMain">
   <!-- 当前位置 -->
   <!-- 当前位置 --> 
   <div id="urHere">DouPHP 管理中心<b>></b><strong>商品分类</strong> </div> 
     <div class="mainBox" style="height:auto!important;height:550px;min-height:550px;">
   
    <h3><a href="<%=path%>/servlet/ClassInfoServlet?task=add" class="actionBtn add">添加分类</a>商品分类</h3>
    <table name="table" id="table" width="100%" border="0" cellpadding="8" cellspacing="0" class="tableBasic">
    <tbody  name="tbody1" id="tbody1">      <tr>
        <th width="60" align="left">分类编码</th>
        <th align="left">分类名称</th>
        
       <th width="60" align="center">级别</th>
        <th width="80" align="center">操作</th>
        <c:forEach var="classinfo" items="${requestScope.classlist}">
              <tr>
        <td align="left"> <a href="product.php?cat_id=1">${classinfo.classid}</a></td>
        <td>${classinfo.classname}</td>
        <td align="center">${classinfo.parentid}</td>
        <td align="center"><a href="javascript:void(0);" onclick="disp_prompt('${classinfo.classname}',${classinfo.classid},${classinfo.parentid});">编辑</a> | <a href="javascript:void(0);" onclick="deleteData(${classinfo.classid},${classinfo.parentid});">删除</a></td>
     </tr>

    <c:forEach var="smallInfo" items="${classinfo.childList}">
              <tr>
        <td align="left"> <a href="product.php?cat_id=1">${smallInfo.classid}</a></td>
        <td>${smallInfo.classname}</td>
        <td align="center">${smallInfo.parentid}</td>
        <td align="center"><a href="javascript:void(0);" onclick="disp_prompt('${smallInfo.classname}',${smallInfo.classid},${smallInfo.parentid});" >编辑</a> | <a href="javascript:void(0);" onclick="deleteData(${smallInfo.classid},${smallInfo.parentid});">删除</a></td>
     </tr>

        </c:forEach>


        </c:forEach>
        
        
           
        
        
     </tr>
     
     </tbody>
     
        
          </table>
           </div>
           
             <div class="pager">
        ${requestScope.pageTool}
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