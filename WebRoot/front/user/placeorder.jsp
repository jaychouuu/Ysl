<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/front/front_newhead.jsp" %>
<c:import url="/front/user/front_top.jsp"></c:import>



<script src="${pageContext.request.contextPath}/js/location/layui/layui.js" type="text/javascript"></script>
<script src="https://static.runoob.com/assets/jquery-validation-1.14.0/lib/jquery.js"></script>
<script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
<script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>

<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/js/datepicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-form.js"></script>



<div id="mainBody">
	<div class="container">
	<div class="row">
<!-- Sidebar ================================================== -->
<%@ include file="/front/front_left.jsp" %>
<!-- Sidebar end=============================================== -->
	<div class="span9">
    <ul class="breadcrumb">
		<li><a href="${pageContext.request.contextPath}">主页</a> <span class="divider">/</span></li>
		<li class="active">订单信息</li>
    </ul>
	<h3>订单信息</h3>	
	<hr class="soft"/>
	
	<div class="row">
		<div class="span9" style="min-height:900px">
			<div class="well">
			
			
    <form action="${pageContext.request.contextPath}/servlet/UserServlet?task=form"     method="post" id="form4">
      <div class="control-group">
    <input type="hidden" name="userid" value="${sessionScope.userBean.userid}"/>
    

		<label class="control-label" for="inputEmail1">订单号：</label>  
						<div class="controls">
     <input type="text" name="orderid"    id="orderid"  readonly='readonly'   value="${requestScope.orderBean.orderid}" class="span3" required>
 					  </div>
	<label class="control-label" for="inputEmail1">收货人姓名：</label>
	<div class="controls">    
   <input type="text" name="receivename" id="receivename" minlength="2" maxlength="5" value="${requestScope.orderBean.receivename}" class="span3"  required>
     </div>
   
      <label class="control-label" for="inputEmail1">商品总件数：</label>   
           <div class="controls">  
        <input type="text" name="totalcount" id="totalcount"   readonly='readonly'   value="${requestScope.orderBean.totalcount}"  
        class="span3" />
          </div>

        <label class="control-label" for="inputEmail1">商品总金额：</label>   
          <div class="controls">  
        <input type="text" name="totalprice" id="totalprice" readonly='readonly' value="<fmt:formatNumber value="${requestScope.orderBean.totalprice}" pattern="#,##0.00"></fmt:formatNumber>"  
        class="span3"     />
        </div>
      <label class="control-label" for="inputEmail1">地址：</label>   
        <div class="controls">  
        <input type="text" name="receiveaddress" id="receiveaddress"      value="${requestScope.orderBean.receiveaddress}"  
       class="span3" required/>
       </div>
       <label class="control-label" for="inputEmail1">下单日期：</label>  
         <div class="controls">   
        <input type="text" name="in_time" id="in_time"  readonly='readonly'   class="span3"     value="${requestScope.orderBean.in_time}"    />
  		</div>
      <label class="control-label" for="inputEmail1">收货人邮箱：</label>   
     <div class="controls">  
        <input type="text" name="receivemail" id="receivemail"    value="${requestScope.orderBean.receivemail}"       class="span3" required/>
        </div>
        
        
         <label class="control-label" for="inputEmail1">收货人电话：</label> 
            <div class="controls">  
        <input type="text" name="receivephone" id="receivephone"    value="${requestScope.orderBean.receivephone}"       class="span3" required/>
        </div>
         <label class="control-label" for="inputEmail1">付款方式：</label> 
            <div class="controls">  
        
        <select id="paytype" name="paytype">
        <option value="支付宝" >支付宝</option>
        <option value="微信">微信</option>
        <option value="网银">网银</option>
      
        
        </select>
        
        
        </div>
           <label class="control-label" for="inputEmail1">送货方式：</label> 
            <div class="controls">  
       
              <select id="sendtype" name="sendtype">
        <option value="快递" >快递</option>
        <option value="物流">物流</option>
        <option value="EMS">EMS</option>
      
        
        </select>
        
       
       
        </div>
      
         </div>
         <div class="controls">
    
        <input name="submit" id="btn_submit" class="btn" type="button"  onclick="savedata();"  value="确认订单" />
     <input name="submit" class="btn" type="reset" value="取消订单" />
    </div>
    </form>
		</div>
		</div>
	</div>	
	
</div>
</div>
</div>
</div>
<!-- MainBody End ============================= -->
<!-- Footer ================================================================== -->
<%@include file="/front/front_foot.jsp" %>

<script>

function validform(){
 return $("#form4").validate({
  rules : {
  
   receivename :{
    	 required : true,
    	 rangelength:[2,5]
   },
  receivephone :{
    	 required : true,
    	 rangelength:[11,11]
   },
  receivemail :{
    	 required : true,
    	 email:true
   } ,
  receiveaddress :{
    	 required : true,
    	 
   }
  },
  messages : {
   receivename : {
    required : '收货人必填'
   },
    receivephone : {
    required : '收货人电话必填'
   },
    receivemail : {
    required : '收货人邮箱必填'
   },
    mail :{
     required : '邮箱必填'
   },
    receiveaddress : {
    required : '收货地址必填'
   }
   }
 });
}


function savedata(){

if(validform().form()) {
  //通过表单验证，以下编写自己的代码

 	  $("#btn_submit").attr("disabled",true);
      var formData =  $("#form4").serializeArray();
      var editformURL = "<%=path%>/servlet/CartServlet?task=submitorder";
        $.post(editformURL,formData,function(jsonData){
    	if(jsonData != null){
       		var flag = jsonData.flag;
		
	 	 if(flag==true){
	 	 window.alert("提交成功,即将跳到订单管理页面");
 	 	  $("#btn_submit").attr("disabled",false);
 	 	  window.location.href="${cpath}/servlet/FrontServlet?task=orderlist";
				}	
          else{
        	var message = jsonData.message;
	 	 window.alert("操作失败,原因为"+message);
	 	  $("#btn_submit").attr("disabled",false);
	 	 }	
				}
        },"json");
     }
     else {
  //校验不通过，什么都不用做，校验信息已经正常显示在表单上
 }
$("#btn_submit").attr("disabled",false);

}





</script>

	