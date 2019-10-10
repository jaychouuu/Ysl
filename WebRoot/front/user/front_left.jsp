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
		
	
			<li class="subMenu"><a>会员中心</a>	

			<ul>
				</ul>
					<ul>
				<li><a  href="${cpath}/servlet/UserServlet?task=frontchange"><i class="icon-chevron-right"></i>信息修改</a></li>
				<li><a  href="${cpath}/servlet/UserServlet?task=frontchangepass"><i class="icon-chevron-right"></i>密码修改</a></li>
				<li><a  href="${cpath}/servlet/FrontServlet?task=orderlist"><i class="icon-chevron-right"></i>订单管理</a></li>
				</ul>
				
				
			</li>
			</ul>
		<br/>
		  <div class="thumbnail">
			<img src="${cpath}/themes/images/products/panasonic.jpg" alt="Bootshop panasonoc New camera"/>
			<div class="caption">
			  <h5>Panasonic</h5>
				<h4 style="text-align:center"><a class="btn" href="product_details.html"> <i class="icon-zoom-in"></i></a> <a class="btn" href="#">Add to <i class="icon-shopping-cart"></i></a> <a class="btn btn-primary" href="#">$222.00</a></h4>
			</div>
		  </div><br/>
			<div class="thumbnail">
				<img src="${cpath}/themes/images/products/kindle.png" title="Bootshop New Kindel" alt="Bootshop Kindel">
				<div class="caption">
				  <h5>Kindle</h5>
				    <h4 style="text-align:center"><a class="btn" href="product_details.html"> <i class="icon-zoom-in"></i></a> <a class="btn" href="#">Add to <i class="icon-shopping-cart"></i></a> <a class="btn btn-primary" href="#">$222.00</a></h4>
				</div>
			  </div><br/>
			<div class="thumbnail">
				<img src="${cpath}/themes/images/payment_methods.png" title="Bootshop Payment Methods" alt="Payments Methods">
				<div class="caption">
				  <h5>Payment Methods</h5>
				</div>
			  </div>
	</div>