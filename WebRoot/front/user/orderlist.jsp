<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/front/front_head.jsp" %>
<c:import url="/front/user/front_top.jsp"></c:import>



<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-form.js"></script>


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



<div id="mainBody">
	<div class="container">
	<div class="row">
<!-- Sidebar ================================================== -->
<%@ include file="/front/user/front_left.jsp" %>
<!-- Sidebar end=============================================== -->
	<div class="span9">
    <ul class="breadcrumb">
		<li><a href="${pageContext.request.contextPath}">主页</a> <span class="divider">/</span></li>
		<li class="active">订单管理</li>
    </ul>
	<h3>订单管理</h3>	
	<hr class="soft"/>
	
	<div class="row">
		<div class="span9" style="min-height:900px">
			<div class="well">
			
			
 
       <div id="list">
    <form name="action" method="post" action="product.php?rec=action">
    <table width="100%" border="0" cellpadding="8" cellspacing="0" class="tableBasic">
      <tr>
        <th width="40" align="center">订单号</th>
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
        <td align="center">${orderBean.orderuser}</td>
        <td align="center">${orderBean.sendtype}</td>
        <td align="center">${orderBean.in_time}</td>
        <td align="center">${orderBean.paytype}</td>
        <td align="center">${orderBean.review == 0 ? "未审核":orderBean.review == 1 ? "审核通过":"审核失败"}  </td>
        

        <td align="center">
        <a href="javascript:void(0);" onclick="viewData(${orderBean.orderid});">查看</a> 
        </td>
      </tr>
       </c:forEach>

          </table>
 
 </div>
 
  
		</div>
			 <iframe id="orderiframe"  src="${cpath}/servlet/OrderServlet?task=viewDetail&orderid=${requestScope.max_orderID}";  style="width:100%;" frameborder='0'>
    </iframe>
		</div>
	
	</div>	


</div>

</div>

</div>

</div>
	
<!-- MainBody End ============================= -->
<!-- Footer ================================================================== -->
<%@include file="/front/front_foot.jsp" %>


	