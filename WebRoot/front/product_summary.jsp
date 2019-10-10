<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/front/front_head.jsp" %>
<%@include file="/front/front_top.jsp" %>



<!-- Header End====================================================================== -->
<div id="mainBody">
	<div class="container">
	<div class="row">
<!-- Sidebar ================================================== -->
<%@ include file="/front/front_left.jsp" %>
<!-- Sidebar end=============================================== -->
	<div class="span9">
    <ul class="breadcrumb">
		<li><a href="index.html">主页</a> <span class="divider">/</span></li>
		<li class="active"> 购物车</li>
    </ul>
	<h3>  购物车 [ <small><c:if test="${sessionScope.cartBean != null}">${sessionScope.cartBean.totalcount}</c:if><c:if test="${sessionScope.cartBean == null}">0</c:if>件</small>]<a href="products.html" class="btn btn-large pull-right"><i class="icon-arrow-left"></i> Continue Shopping </a></h3>	
	<hr class="soft"/>

			
	<table class="table table-bordered">
              <thead>
                <tr>
                  <th>商品</th>
                  <th>分类</th>
                  <th>描述</th>
                  <th colspan="6">增加/减少</th>
				  <th>价格</th>
				
                  <th>总和</th>
				</tr>
              </thead>
              <tbody id="testbody">
				<c:forEach var="itembean" items="${sessionScope.cartBean.itemlist}">
				<tr  id="${itembean.itemid}">
                  <td> 	<c:if test="${itembean.filepath != null && itembean.filepath != ''}">
			<img width="60" src="${cpath}/uploadfiles/${itembean.filepath}" alt=""/>
			</c:if>
		    <c:if test="${itembean.filepath == null || itembean.filepath == ''}">
			<img  width="60"  src="${cpath}/images/noPHOTO.png" alt=""/>
			</c:if></td>
			      <td>${itembean.bigname}/${itembean.smallname}</td>
                  <td>${itembean.itemname}<br/>${itembean.temp}</td>
				  <td colspan="6">
					<div class="input-append"><input id="count_summary_${itembean.itemid}" class="span1" style="max-width:34px" placeholder="${itembean.count}"  size="16" type="text" onkeyup="changeCount(this,${itembean.itemid});"><button class="btn" type="button" onclick="sub(${itembean.itemid});"><i class="icon-minus"></i></button><button class="btn" type="button"  onclick="add(${itembean.itemid});"   ><i class="icon-plus"></i></button><button class="btn btn-danger" type="button"   onclick="dele(${itembean.itemid});" ><i class="icon-remove icon-white"></i></button></div>
				  </td>
                  <td>$<fmt:formatNumber value="${itembean.itemprice}" pattern="#,###.00"></fmt:formatNumber></td>
                  <td id="sumprice_summary_${itembean.itemid}" >$<fmt:formatNumber value="${itembean.sumprice}" pattern="#,###.00"></fmt:formatNumber></td>
                </tr>
                
                </c:forEach>
             </tbody>
                <tr>
                  <td><button type="button" class="btn" onclick="deleall();">清空购物车</button> </td>
                
                  <td colspan="9" style="text-align:right">Total Price:</td>
                  <td id="totalprice_summary">$<fmt:formatNumber value="${sessionScope.cartBean.totalprice}" pattern="#,###.00"></fmt:formatNumber></td>
            
                </tr>
		
            </table>
		
	
		
	<a href="javascript:void(0);" onclick="window.history.back();"  class="btn btn-large"><i class="icon-arrow-left"></i>继续购物</a>
	
	<c:if test="${sessionScope.userBean !=null}"> 
		<a href="${cpath}/servlet/CartServlet?task=placeorder" class="btn btn-large pull-right">下一步<i class="icon-arrow-right"></i></a>
	  </c:if>
	  	<c:if test="${sessionScope.userBean ==null}"> 
		<a href="javascript:void(0);" onclick="window.alert('请先登录!');"  class="btn btn-large pull-right">下一步<i class="icon-arrow-right"></i></a>
	  </c:if>
	
</div>
</div></div>
</div>
<!-- MainBody End ============================= -->
<!-- Footer ================================================================== -->
<%@include file="/front/front_foot.jsp" %>
</body>
<script language="javascript">
		function changeCount(count,itemid){
			
			var updataURL = "${cpath}/servlet/CartServlet?task=updata&data="+Math.random()+"";
			var paramobj = {
			"itemid" : itemid,
			"new_count": count.value 
			};
					$.post(updataURL,paramobj,function(jsondata){
					
						if(jsondata != null){
							var flag = jsondata.flag;
					    	if(flag == true){
							var dataobj = jsondata.data.cartBean;		
							var sumprice_str=jsondata.data.sumprice_str;
							var totalcount = dataobj.totalcount;
							var totalprice_str = dataobj.totalprice_str;
							$("#price").html("$"+totalprice_str);
						
							$("#count").html("<i class=\"icon-shopping-cart icon-white\"></i>"+"["+totalcount+"]物品在您的购物车");
							$("#count1").html("<img src=\"${cpath}/themes/images/ico-cart.png\" alt=\"cart\">["+totalcount+"] 物品在您的购物车 <span class=\"badge badge-warning pull-right\" id=\"price1\">$"+totalprice_str+"</span>");
							$("#price1").html("$"+totalprice_str);
							$("#count_summary_"+itemid).attr("placeholder",count);
				            $("#totalprice_summary").html("$"+totalprice_str); 	
				            $("#sumprice_summary_"+itemid).html("$"+sumprice_str);
                       							
						
						
						
						}
						
						
						
						}
					
					
					
					
					},"json");	
			
		
		
		}
				function sub(itemid){
				
					if($("#count_summary_"+itemid).attr("placeholder")!=1){
					
					var newcount = $("#count_summary_"+itemid).attr("placeholder")-1;
				    $("#count_summary_"+itemid).attr("placeholder",newcount);
					var updataURL = "${cpath}/servlet/CartServlet?task=updata&data="+Math.random()+"";
					var paramobj = {
					"itemid" : itemid,
					"new_count": newcount 
		   			};
					$.post(updataURL,paramobj,function(jsondata){
					
						if(jsondata != null){
							var flag = jsondata.flag;
					    	if(flag == true){
							var dataobj = jsondata.data.cartBean;		
							var sumprice_str=jsondata.data.sumprice_str;
							var totalcount = dataobj.totalcount;
							var totalprice_str = dataobj.totalprice_str;
							$("#price").html("$"+totalprice_str);
						
							$("#count").html("<i class=\"icon-shopping-cart icon-white\"></i>"+"["+totalcount+"]物品在您的购物车");
							$("#count1").html("<img src=\"${cpath}/themes/images/ico-cart.png\" alt=\"cart\">["+totalcount+"] 物品在您的购物车 <span class=\"badge badge-warning pull-right\" id=\"price1\">$"+totalprice_str+"</span>");
							$("#price1").html("$"+totalprice_str);
				            $("#totalprice_summary").html("$"+totalprice_str); 	
				            $("#sumprice_summary_"+itemid).html("$"+sumprice_str);
                       		
						
						}
						
						}
					},"json");	
				
					
					
					
					}
				}
	function add(itemid){
				
					
					
					var newcount = parseInt($("#count_summary_"+itemid).attr("placeholder"))+1;
				    $("#count_summary_"+itemid).attr("placeholder",newcount);
					var updataURL = "${cpath}/servlet/CartServlet?task=updata&data="+Math.random()+"";
					var paramobj = {
					"itemid" : itemid,
					"new_count": newcount 
		   			};
					$.post(updataURL,paramobj,function(jsondata){
					
						if(jsondata != null){
							var flag = jsondata.flag;
					    	if(flag == true){
							var dataobj = jsondata.data.cartBean;		
							var sumprice_str=jsondata.data.sumprice_str;
							var totalcount = dataobj.totalcount;
							var totalprice_str = dataobj.totalprice_str;
							$("#price").html("$"+totalprice_str);
						
							$("#count").html("<i class=\"icon-shopping-cart icon-white\"></i>"+"["+totalcount+"]物品在您的购物车");
							$("#count1").html("<img src=\"${cpath}/themes/images/ico-cart.png\" alt=\"cart\">["+totalcount+"] 物品在您的购物车 <span class=\"badge badge-warning pull-right\" id=\"price1\">$"+totalprice_str+"</span>");
							$("#price1").html("$"+totalprice_str);
				            $("#totalprice_summary").html("$"+totalprice_str); 	
				            $("#sumprice_summary_"+itemid).html("$"+sumprice_str);
                       		
						
						}
						
						}
					},"json");	
				
					
					
					
					
				}
				function dele(itemid){
				
					var newcount = $("#count_summary").attr("placeholder")+1;
				    $("#count_summary").attr("placeholder",newcount);
					var updataURL = "${cpath}/servlet/CartServlet?task=remove&data="+Math.random()+"";
					var paramobj = {
					"itemid" : itemid,
		   			};
					$.post(updataURL,paramobj,function(jsondata){
					
						if(jsondata != null){
							var flag = jsondata.flag;
					    	if(flag == true){
							var dataobj = jsondata.data;		
							var totalcount = dataobj.totalcount;
							var totalprice_str = dataobj.totalprice_str;
							$("#price").html("$"+totalprice_str);
						
							$("#count").html("<i class=\"icon-shopping-cart icon-white\"></i>"+"["+totalcount+"]物品在您的购物车");
							$("#count1").html("<img src=\"${cpath}/themes/images/ico-cart.png\" alt=\"cart\">["+totalcount+"] 物品在您的购物车 <span class=\"badge badge-warning pull-right\" id=\"price1\">$"+totalprice_str+"</span>");
							$("#price1").html("$"+totalprice_str);
				            $("#totalprice_summary").html("$"+totalprice_str); 	

							$("#"+itemid).remove();                    		
						
						}
						
						}
					},"json");	
				
					
					
					
					
				}
				
					function deleall(){
				
			
					var updataURL = "${cpath}/servlet/CartServlet?task=removeall&data="+Math.random()+"";
			
					$.post(updataURL,function(jsondata){
					
						if(jsondata != null){
							var flag = jsondata.flag;
					    	if(flag == true){
							$("#price").html("$0.00");
							$("#count").html("<i class=\"icon-shopping-cart icon-white\"></i>"+"[0]物品在您的购物车");
							$("#count1").html("<img src=\"${cpath}/themes/images/ico-cart.png\" alt=\"cart\">[0] 物品在您的购物车 <span class=\"badge badge-warning pull-right\" id=\"price1\">$0.00</span>");
							$("#price1").html("$0.00");
							$("#totalprice_summary").html("$0.00"); 	
							
							$("#testbody tr").remove();                    		
						}
						else{
						var message = jsondata.message;
						window.alert("操作失败，原因为："+message);
						}
						
						}
					},"json");	
				
					
					
					
					
				}
				
			
			
			


</script>


</html>