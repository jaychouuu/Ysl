<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="dcLeft"><div id="menu">
 <ul class="top">
    <li  ${param.menu_id == "200" ? "class='cur'" : ""}><a href="<%=path%>/admin/index.jsp"><i class="home"></i><em>管理首页</em></a></li>
 </ul>
 <ul>
  
 <c:if test="${sessionScope.userBean.is_super==1}">
  <li  ${param.menu_id == "208" ? "class='cur'" : ""}><a href="<%=path%>/servlet/UserServlet?task=adminmanage"><i class="system"></i><em>系统用户设置</em></a></li>
 </c:if>
  <li  ${param.menu_id == "207" ? "class='cur'" : ""}><a href="<%=path%>/servlet/UserServlet?task=usermanage"><i class="nav"></i><em>注册用户管理</em></a></li>
 </ul>
   <ul>
  <li   ${param.menu_id == "201" ? "class='cur'" : ""}         ><a href="<%=path%>/servlet/ClassInfoServlet?task=list"><i class="productCat"></i><em>商品分类</em></a></li>
  <li  ${param.menu_id == "202" ? "class='cur'" : ""}       ><a href="${pageContext.request.contextPath}/servlet/ItemServlet?task=list"><i class="product"></i><em>商品列表</em></a></li>
 </ul>
  <ul>
  <li ${param.menu_id == "203" ? "class='cur'" : ""}       ><a href="<%=path%>/servlet/OrderServlet?task=list"><i class="articleCat"></i><em>订单管理</em></a></li>
  <li  ${param.menu_id == "204" ? "class='cur'" : ""}       ><a href="<%=path%>/servlet/OrderServlet?task=edit"><i class="article"></i><em>订单审核</em></a></li>
 </ul>
   <ul class="bot">
  <li ${param.menu_id == "205" ? "class='cur'" : ""}    ><a href="<%=path%>/servlet/UserServlet?task=change"><i class="backup"></i><em>用户信息修改</em></a></li>
  <li ${param.menu_id == "206" ? "class='cur'" : ""}   ><a href="<%=path%>/servlet/UserServlet?task=editpass"><i class="mobile"></i><em>密码修改</em></a></li>
  <li ><a href="<%=path%>/servlet/LoginServlet?task=logout"><i class="theme"></i><em>退出系统</em></a></li>

 </ul>
</div></div>
