<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/common_header.jsp" %>


<script language="javascript" type="text/javascript" src="<%=path%>/js/datepicker/WdatePicker.js"></script>


      <div id="list">
    <form name="action" method="post" action="product.php?rec=action">
    <table width="100%" border="0" cellpadding="8" cellspacing="0" class="tableBasic">
      <tr>
        <th width="40" align="center">单一订单号</th>
        <th align="center"width="80">商品ID</th>
        <th width="80" align="center">商品姓名</th>
       <th width="80" align="center">价格</th>
       <th width="150" align="center">数量</th>
       <th width="80" align="center">总和</th>
      </tr>
      <c:forEach var="orderDetail" items="${requestScope.detaillist}">
        <tr>
        <td align="center">${orderDetail.detailid}</td>
        <td align="center">${orderDetail.itemid}</td>
        <td align="center">${orderDetail.itemname}</td>
        <td align="center"><fmt:formatNumber value="${orderDetail.itemprice}" pattern="#,###.00"></fmt:formatNumber></td>
        <td align="center">${orderDetail.itemcount}</td>
        <td align="center"><fmt:formatNumber value="${orderDetail.sumprice}" pattern="#,###.00"></fmt:formatNumber></td>

      
      </tr>
       </c:forEach>

          </table>
  
    
    
    
    
 </div>

</body>
</html>
    
    