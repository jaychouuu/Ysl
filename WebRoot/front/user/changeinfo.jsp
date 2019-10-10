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
<%@ include file="/front/user/front_left.jsp" %>
<!-- Sidebar end=============================================== -->
	<div class="span9">
    <ul class="breadcrumb">
		<li><a href="${pageContext.request.contextPath}">主页</a> <span class="divider">/</span></li>
		<li class="active">用户信息修改</li>
    </ul>
	<h3>用户信息修改</h3>	
	<hr class="soft"/>
	
	<div class="row">
		<div class="span9" style="min-height:900px">
			<div class="well">
			
			
    <form action="${pageContext.request.contextPath}/servlet/UserServlet?task=form"     method="post" id="form3">
      <div class="control-group">
    <input type="hidden" name="userid" value="${sessionScope.userBean.userid}"/>
    

		<label class="control-label" for="inputEmail1">用户名</label>  
						<div class="controls">
     <input type="text" name="username"    id="username" minlength="5" maxlength="10"  value="${sessionScope.userBean.username}" class="span3" required>
 					  </div>
	<label class="control-label" for="inputEmail1">真实姓名</label>
	<div class="controls">    
   <input type="text" name="truename" id="truename" minlength="2" maxlength="5" value="${sessionScope.userBean.truename}" class="span3"  required>
     </div>
   <label class="control-label" for="inputEmail1">性别</label>   
     		<div class="controls">  												
	<input type="radio" ${sessionScope.userBean.usersex == 0 ? "checked='checked'":""} name="usersex" id="usersex"value="0"  />男
	<input type="radio" ${sessionScope.userBean.usersex == 1 ? "checked='checked'":""} name="usersex" id="usersex" value="1" />女
   </div>
      <label class="control-label" for="inputEmail1">年龄</label>   
           <div class="controls">  
        <input type="text" name="userage" id="userage"    value="${sessionScope.userBean.userage}"  
        class="span3" />
          </div>

        <label class="control-label" for="inputEmail1">电话</label>   
          <div class="controls">  
        <input type="text" name="telphone" id="telphone" minlength="11" maxlength="11"    value="${sessionScope.userBean.telphone}"  
        class="span3" required/>
        </div>
      <label class="control-label" for="inputEmail1">地址</label>   
        <div class="controls">  
        <input type="text" name="address" id="address"    value="${sessionScope.userBean.address}"  
       class="span3" required/>
       </div>
       <label class="control-label" for="inputEmail1">出生日期</label>  
         <div class="controls">   
        <input type="text" name="birthday" id="birthday"   onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd'})" onchange="jsGetAge();" class="span3 Wdate"     value="${sessionScope.userBean.birthday}"    />
  		</div>
      <label class="control-label" for="inputEmail1">邮箱</label>   
     <div class="controls">  
        <input type="text" name="mail" id="mail"    value="${sessionScope.userBean.mail}"       class="span3" required/>
        </div>
        <input type="hidden" name="id" value="">
      
         </div>
         <div class="controls">
    
        <input name="submit" id="btn_submit" class="btn" type="button"  onclick="savedata();"  value="保存数据" />
     <input name="submit" class="btn" type="reset" value="重置数据" />
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
 return $("#form3").validate({
  rules : {
   username : {
    required : true,
     	rangelength:[5,10]
   },
   truename :{
    	 required : true,
    	 rangelength:[2,5]
   },
  telphone :{
    	 required : true,
    	 rangelength:[11,11]
   },
  mail :{
    	 required : true,
    	 email:true
   }
  },
  messages : {
   username : {
    required : '用户名必填'
   },
    truename : {
    required : '真实姓名必填'
   },
    telphone : {
    required : '电话必填'
   },
    mail :{
     required : '邮箱必填'
   }
   }
 });
}


function savedata(){

if(validform().form()) {
  //通过表单验证，以下编写自己的代码

 	  $("#btn_submit").attr("disabled",true);
      var formData =  $("#form3").serializeArray();
      var editformURL = "<%=path%>/servlet/UserServlet?task=form";
        $.post(editformURL,formData,function(jsonData){
    	if(jsonData != null){
       		var flag = jsonData.flag;
       		var message = jsonData.message;
		
	 	 if(flag==true){
	 	 window.alert("修改成功");
 	 	  $("#btn_submit").attr("disabled",false);
				}	
          else{
	 	 window.alert("操作失败,原因为"+message);
	 	  $("#btn_submit").attr("disabled",false);
	 	 
	 	 }	
				}
        },"json");
     $("#btn_submit").attr("disabled",false);
     }
     else {
  //校验不通过，什么都不用做，校验信息已经正常显示在表单上
 }

}




function jsGetAge()
{       
    var returnAge;
    
    
    var strBirthday =	 document.getElementById("birthday").value;
    var strBirthdayArr=strBirthday.split("-");
    var birthYear = strBirthdayArr[0];
    var birthMonth = strBirthdayArr[1];
    var birthDay = strBirthdayArr[2];
    
    d = new Date();
    var nowYear = d.getFullYear();
    var nowMonth = d.getMonth() + 1;
    var nowDay = d.getDate();
    
    if(nowYear == birthYear)
    {
        returnAge = 0;//同年 则为0岁
    }
    else
    {
        var ageDiff = nowYear - birthYear ; //年之差
        if(ageDiff > 0)
        {
            if(nowMonth == birthMonth)
            {
                var dayDiff = nowDay - birthDay;//日之差
                if(dayDiff < 0)
                {
                    returnAge = ageDiff - 1;
                }
                else
                {
                    returnAge = ageDiff ;
                }
            }
            else
            {
                var monthDiff = nowMonth - birthMonth;//月之差
                if(monthDiff < 0)
                {
                    returnAge = ageDiff - 1;
                }
                else
                {
                    returnAge = ageDiff ;
                }
            }
        }
        else
        {
            returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天
        }
    }
    
  			 document.getElementById("userage").value = returnAge;//返回周岁年龄
    
}

</script>

	