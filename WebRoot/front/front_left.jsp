<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

	   <div id="sidebar" class="span3">
		<div class="well well-small"><a  href="${cpath}/servlet/CartServlet?task=list" id="count1"><img src="${cpath}/themes/images/ico-cart.png" alt="cart">[<c:if test="${sessionScope.cartBean != null}">${sessionScope.cartBean.totalcount}</c:if><c:if test="${sessionScope.cartBean == null}">
		0
		</c:if>] 物品在您的购物车 <span class="badge badge-warning pull-right" id="price1">$	<c:if test="${sessionScope.cartBean != null}">
		${sessionScope.cartBean.totalprice_str}
		</c:if>
		<c:if test="${sessionScope.cartBean == null}">
		0
		</c:if>
		</span></a></div>
		<ul id="sideManu" class="nav nav-tabs nav-stacked">
		
		
		
		
		<c:forEach var="classinfo" items="${sessionScope.classlist}" varStatus="classStatus">
			<li class="subMenu"><a>${classinfo.classname}</a>	
			<c:forEach var="childinfo" items="${classinfo.childList}" varStatus="childStatus">
			<ul>
				<li><a  href="${cpath}/servlet/FrontServlet?task=product&classid=${childinfo.classid}_${childinfo.parentid}"><i class="icon-chevron-right"></i>${childinfo.classname}</a></li>
				</ul>
			</c:forEach>
				
			</li>`
		</c:forEach>
		
		
		</ul>
		<br/>
		  
			<div class="thumbnail">
				<img src="${cpath}/themes/images/payment_methods.png" title="Bootshop Payment Methods" alt="Payments Methods">
				<div class="caption">
				  <h5>支持付款方式</h5>
				</div>
			  </div>
	</div>