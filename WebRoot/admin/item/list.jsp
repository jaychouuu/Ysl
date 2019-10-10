<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/common_header.jsp" %>
<c:import url="/admin/common_top.jsp"></c:import>
<c:import url="/admin/common_left.jsp">

<c:param name="menu_id" value="202"></c:param>

</c:import>

<script language="javascript" type="text/javascript" src="<%=path%>/js/datepicker/WdatePicker.js"></script>

<script language="javascript">
function resetForm(){
  jQuery("#item_name").val("");
  jQuery("#addtime_start").val("");
  jQuery("#addtime_end").val("");
  jQuery("#price_begin").val("");
  jQuery("#price_end").val("");

}

function deleteData(itemid){
var refreshURL = "<%=path%>/servlet/ItemServlet?task=refresh";

 var message= "确定要删除选中的类别？";


	var confirmFlag = window.confirm(message);
	if(confirmFlag == true)
	{
	  var delURL = "<%=path%>/servlet/ItemServlet?task=delete&itemid="+itemid;
	  jQuery.get(delURL,function(jsonData){
	
	        var flag = jsonData.flag;
	        var message = jsonData.message;
	        
	        if(flag == true){
	        
	        	     window.alert("删除成功");
	        	 
	        	   $("#submit").click();
	        	 
	        	     
	        }
	     else{
	     
	     window.alert("删除操作失败,异常原因="+message);
	     }
	 
	  
	  
	  },"json");
	
	
	} 



}



</script>

<!-- dcHead 结束 --> 
 <div id="dcMain">
   <!-- 当前位置 -->
<div id="urHere">DouPHP 管理中心<b>></b><strong>商品列表</strong> </div> 
  <div class="mainBox" style="height:auto!important;height:550px;min-height:550px;">
        <h3><a href="${pageContext.request.contextPath}/servlet/ItemServlet?task=add" class="actionBtn add">添加商品</a>商品列表</h3>
    <div class="filter">
    <form action="${pageContext.request.contextPath}/servlet/ItemServlet?task=list" method="post" style="width:80%;">
    
    <table width="100%" border=0>
    <tr>
    <td width="15%" align="right">商品名称:</td> <td width="25%"> <input id="item_name" type="text"  name="item_name" class="inpMain" value="${param.item_name}"style="width:180px;"/> </td>
    <td width="15%" align="right">添加日期:</td> <td width="45%" ><input class="inpMain Wdate" id="addtime_start" value="${param.addtime_start}" style='width:140px;height=15px;' type="text" name="addtime_start" onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd'})"/>--<input type="text"  id="addtime_end" name="addtime_end" class="inpMain Wdate"  style='width:140px;' value="${param.addtime_end}"onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd'})"/> </td>
    </tr>
      <tr>
    <td   align="right">商品分类:</td> <td> <select name="item_class" id="item_class" style="width:190px;">
       				       <option>	</option>
       				       

         <c:forEach var="classInfo"  items="${requestScope.classlist}">
         <c:set var="a" value="${classInfo.classid}_${classInfo.parentid}">  
         </c:set>
    

                           <option  ${param.item_class == a ? "selected='selected'" : ""}   value=${a}>${classInfo.classname}</option>
          <c:forEach var="smallInfo" items="${classInfo.childList}">
               <c:set var="b" value="${smallInfo.classid}_${smallInfo.parentid}">  
               </c:set>
         
           <option   ${param.item_class == b ? "selected='selected'" : ""} value="${b}">&nbsp;&nbsp;&nbsp;&nbsp;${smallInfo.classname}</option>
          
          
          
          </c:forEach>         
          </c:forEach>
    
    </select> 
    </td>
    <td   align="right">商品价格:</td> <td > <input type="text"  name="price_begin"  id="price_begin"  class="inpMain" value="${param.price_begin}"  style='width:140px;'/>--<input type="text"  id="price_end"  name="price_end" class="inpMain" value="${param.price_end}" style='width:140px;'/></td>
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
        <th width="22" align="center"><input name='chkall' type='checkbox' id='chkall' onclick='selectcheckbox(this.form)' value='check'></th>
        <th width="40" align="center">编号</th>
        <th align="left">商品名称</th>
        <th width="150" align="center">商品分类</th>
       <th width="80" align="center">添加日期</th>
       <th width="80" align="center">价格</th>
        <th width="80" align="center">操作</th>
      </tr>
      <c:forEach var="itemBean" items="${requestScope.itemlist}">
      
        <tr>
        <td align="center"><input type="checkbox" name="itemid" value="${itemBean.itemid}" /></td>
        <td align="center">${itemBean.itemid}</td>
        <td><a href="product.php?rec=edit&id=15">${itemBean.itemname}</a></td>
        <td align="center"><a href="product.php?cat_id=3">${itemBean.bigname}/${itemBean.smallname}</a></td>
        <td align="center">${itemBean.addtime}</td>
        <td align="center"><fmt:formatNumber value="${itemBean.itemprice}" pattern="#,##.00"></fmt:formatNumber></td>
         
        <td align="center">
        <a href="${cpath}/servlet/ItemServlet?task=edit&itemid=${itemBean.itemid}">编辑</a> | <a href="javascript:void(0);" onclick="deleteData(${itemBean.itemid});">删除</a>
        </td>
      </tr>
       </c:forEach>
       

           
     
      </tr>
          </table>
   
    <div class="clear"></div>
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
<script type="text/javascript">


</script>
</body>
</html>
    