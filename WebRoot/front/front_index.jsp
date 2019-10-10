<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/front/front_head.jsp" %>
<c:import url="/front/front_top.jsp"></c:import>
<div id="mainBody">
	<div class="container">
	<div class="row">
<!-- Sidebar ================================================== -->
<%@ include file="/front/front_left.jsp" %>
<!-- Sidebar end=============================================== -->
	<div class="span9">
    <ul class="breadcrumb">
		<li><a href="index.html">主页</a> <span class="divider">/</span></li>
		<li class="active"></li>
    </ul>
	<h3><small class="pull-right"></small></h3>	

	<hr class="soft"/>
	<form class="form-horizontal span6" action="${cpath}/servlet/FrontServlet?task=index" method="post">
		<div class="control-group">
		  <input type="hidden" id="classid" name="classid" value="${requestScope.classid}">
		  <label class="control-label alignR">关键字:</label>
		  <input id="keyWord"  name="keyWord" class="srchTxt" value="${requestScope.keyWord}" type="text"/>
		  <input type="submit" class="btn" value="查询商品"></> 
			
		</div>
	  </form>
	  
<div id="myTab" class="pull-right">
 <a href="#listView" data-toggle="tab"><span class="btn btn-large"><i class="icon-list"></i></span></a>
 <a href="#blockView" data-toggle="tab"><span class="btn btn-large btn-primary"><i class="icon-th-large"></i></span></a>
</div>
<br class="clr"/>
<div class="tab-content">
	<div class="tab-pane" id="listView">
	
	<c:forEach var="iteminfo" items="${requestScope.itemlist}">
			<div class="row">	  
			<div class="span2">
			<c:if test="${iteminfo.filepath != null && iteminfo.filepath != ''}">
			<img src="${cpath}/uploadfiles/${iteminfo.filepath}"  style="width:90PX;height:90PX;" alt=""/>
			</c:if>
		    <c:if test="${iteminfo.filepath == null || iteminfo.filepath == ''}">
			<img src="${cpath}/images/noPHOTO.png" style="width:90PX;height:90PX;" alt=""/>
			</c:if>
			</div>
			<div class="span4">
				<h3>${iteminfo.itemname}</span></h3>				
				<hr class="soft"/>
				<h5>${iteminfo.temp}</h5>
		
				<br class="clr"/>
			</div>
			<div class="span3 alignR">
				<form class="form-horizontal qtyFrm">
				<h3>$<fmt:formatNumber value="${iteminfo.itemprice}" pattern="#,###.00"></fmt:formatNumber></h3>
			
				
				<a  href="javascript:void(0);" onclick="addToCart(${iteminfo.itemid});"class="btn btn-large btn-primary"> 添加到 <i class=" icon-shopping-cart"></i></a>
				
				</form>
			</div>
		</div>
		</c:forEach>
		
		
		<hr class="soft"/>
	</div>
	<div class="tab-pane  active" id="blockView">
		<ul class="thumbnails">
  <c:forEach var="iteminfo" items="${requestScope.itemlist}">
			<li class="span3">
			  <div class="thumbnail">
				<a href="product_details.html">
					<c:if test="${iteminfo.filepath != null && iteminfo.filepath != ''}">
			<img src="${cpath}/uploadfiles/${iteminfo.filepath}" style="width:90PX;height:90PX;" alt=""/>
			</c:if>
		    <c:if test="${iteminfo.filepath == null || iteminfo.filepath == ''}">
			<img src="${cpath}/images/noPHOTO.png" style="width:90PX;height:90PX;" alt=""/>
						</c:if>
			  </a>
				<div class="caption">
				  <h5>${iteminfo.itemname}</h5>
				  <p> 
			   ${iteminfo.temp}
				  </p>
				    <h4 style="text-align:center"> <a class="btn" href="javascript:void(0);" onclick="addToCart(${iteminfo.itemid});">添加到 <i class="icon-shopping-cart"></i></a> <a class="btn btn-primary" href="#">$<fmt:formatNumber value="${iteminfo.itemprice}" pattern="#,###.00"></fmt:formatNumber></a></h4>
				</div>
			  </div>
			</li>
			</c:forEach>
			
			
			
			
		  </ul>
	<hr class="soft"/>
	</div>
</div>

	<a href="compair.html" class="btn btn-large pull-right">Compair Product</a>

	
	<div class="pagination">
${requestScope.pageTool}
	</div>
	
	
	
<!-- 	<div class="pagination">
			<ul>
			<li><a href="#">&lsaquo;</a></li>
			<li><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">...</a></li>
			<li><a href="#">&rsaquo;</a></li>
			</ul>
			</div>
			<br class="clr"/>
</div> -->
</div>
</div>
</div>
<script language="javascript">

	function addToCart(itemid){
		var addURL = "${cpath}/servlet/CartServlet?task=add&itemid="+itemid+"&date="+Math.random();
		$.get(addURL,function(jsonData){
				
				if(jsonData != null){
				
					var flag = jsonData.flag;
					if(flag==true){
					
					var dataobj = jsonData.data;
					var totalcount = dataobj.totalcount;
					var totalprice_str = dataobj.totalprice_str;
					$("#price").html("$"+totalprice_str);
					$("#count").html("<i class=\"icon-shopping-cart icon-white\"></i>"+"["+totalcount+"]物品在您的购物车");
					$("#count1").html("<img src=\"${cpath}/themes/images/ico-cart.png\" alt=\"cart\">["+totalcount+"] 物品在您的购物车 <span class=\"badge badge-warning pull-right\" id=\"price1\">$"+totalprice_str+"</span>");
					$("#price1").html("$"+totalprice_str);
			
					}else{
					var message = jsonData.message;
					window.alert("添加失败，原因为"+message);
					}

				}
		
		},"json");
	
	
	}




</script>



<!-- Footer ================================================================== -->
<%@include file="/front/front_foot.jsp" %>
