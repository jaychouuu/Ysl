<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/common_header.jsp" %>
<c:import url="/admin/common_top.jsp"></c:import>
<c:import url="/admin/common_left.jsp">
<c:param name="menu_id" value="204"></c:param>

</c:import>

<script language="javascript" type="text/javascript" src="<%=path%>/js/datepicker/WdatePicker.js"></script>

<script language="javascript">
function resetForm(){
  jQuery("#username").val("");
  jQuery("#addtime_start").val("");
  jQuery("#addtime_end").val("");

}
function viewData(orderid){
	var orderiframe = $("#orderiframe")[0];
	var viewURL = "${cpath}/servlet/OrderServlet?task=viewDetail&orderid="+orderid;
	orderiframe.src = viewURL;
	



}






</script>

<!-- dcHead 结束 --> 
 <div id="dcMain">
   <!-- 当前位置 -->
<div id="urHere">DouPHP 管理中心<b>></b><strong>订单列表</strong> </div> 
  <div class="mainBox" style="height:auto!important;height:550px;min-height:550px;">
    <div class="filter">
    <form action="${pageContext.request.contextPath}/servlet/OrderServlet?task=list" method="post" style="width:80%;">
    
    <table width="100%" border=0>
    <tr>
  
    <td width="15%" align="right">下单日期:</td> <td width="45%" ><input class="inpMain Wdate" id="addtime_start" value="" style='width:140px;height=15px;' type="text" name="addtime_start" onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>--<input type="text"  id="addtime_end" name="addtime_end" class="inpMain Wdate"  style='width:140px;' value="${param.addtime_end}"onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/> </td>
    </tr>
      <tr>
    <td   align="right">用户名:</td> <td width="25%"> <input type="text"  name="username"  id="username"  class="inpMain" value=""  style='width:180px;'/></td>
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
        <th width="40" align="center">订单号</th>
        <th align="left"width="80">用户名</th>
        <th width="80" align="center">真实姓名</th>
       <th width="80" align="center">发货方式</th>
       <th width="150" align="center">下单时间</th>
       <th width="80" align="center">汇款方式</th>
       <th width="80" align="center">审核情况</th>
        <th width="80" align="center">操作</th>
      </tr>
      <c:forEach var="orderBean" items="${requestScope.orderlist}">
        <tr>
        <td align="center">${orderBean.orderid}</td>
        <td>${orderBean.username}</td>
        <td align="center">${orderBean.orderuser}</td>
        <td align="center">${orderBean.sendtype}</td>
        <td align="center">${orderBean.in_time}</td>
        <td align="center">${orderBean.paytype}</td>
        <td align="center">${orderBean.review == 0 ? "未审核":orderBean.review == 1 ? "审核通过":"审核失败"}</td>
        <td align="center">
        <a href="${cpath}/servlet/OrderServlet?task=pass&orderid=${orderBean.orderid}">通过审核</a>|<a href="${cpath}/servlet/OrderServlet?task=fail&orderid=${orderBean.orderid}">审核失败</a>
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
    
    