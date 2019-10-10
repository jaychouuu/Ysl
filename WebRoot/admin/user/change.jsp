<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/common_header.jsp" %>
<c:import url="/admin/common_top.jsp"></c:import>
<c:import url="/admin/common_left.jsp">
<c:param name="menu_id" value="205"></c:param>
</c:import>


<script language="javascript" type="text/javascript" src="<%=path%>/js/datepicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery-form.js"></script>


<script src="https://static.runoob.com/assets/jquery-validation-1.14.0/lib/jquery.js"></script>
<script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
<script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>









<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

</div>
<!-- dcHead 结束 --> 

 <div id="dcMain">
   <!-- 当前位置 -->
<div id="urHere">DouPHP 管理中心<b>></b><strong>用户信息修改</strong> </div>   <div class="mainBox" style="height:auto!important;height:550px;min-height:550px;">

    <form action="${pageContext.request.contextPath}/servlet/UserServlet?task=form"   class="cmxform"    method="post" id="form3">
    
    <input type="hidden" name="userid" value="${sessionScope.userBean.userid}"/>
    
     <table width="100%" border="0" cellpadding="8" cellspacing="0" class="tableBasic">
     
     
     
     
     <tr>
     <td width="90" align="right">用户名</td>
     <td>
     <input type="text" name="username"  minlength="5"  maxlength="10"  id="username" value="${sessionScope.userBean.username}" class="inpMain" required>
     </td>
     </tr>
      <tr>
       <td width="90" align="right">真实姓名</td>
       <td>
   <input type="text" name="truename" id="truename" minlength="2" maxlength="5" value="${sessionScope.userBean.truename}" class="inpMain"  required>
       </td>
      </tr>
      <tr>
       <td align="right" >性别</td>
       <td>															
	<input type="radio" ${sessionScope.userBean.usersex == 0 ? "checked='checked'":""} name="usersex" id="usersex"value="0"  />男
	<input type="radio" ${sessionScope.userBean.usersex == 1 ? "checked='checked'":""} name="usersex" id="usersex" value="1" />女
       </td>
      </tr>													
      <tr>
       <td align="right">年龄</td>
       <td>
        <input type="text" name="userage" id="userage"    value="${sessionScope.userBean.userage}" size="40" 
        class="inpMain" />
        
       </td>
      </tr>
            <tr>
        <td align="right">电话</td>
       <td>
        <input type="text" name="telphone" id="telphone"    value="${sessionScope.userBean.telphone}" size="40" 
        class="inpMain" />
        
       </td>
      </tr>
      <tr>
        <td align="right">地址</td>
       <td>
        <input type="text" name="address" id="address"    value="${sessionScope.userBean.address}" size="40" 
        class="inpMain" />
        
       </td>
        
      </tr>
      <tr>
       <td align="right">出生日期</td>
       <td>
        <input type="text" name="birthday" id="birthday"   onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd'})" onchange="jsGetAge();" class="inpMain Wdate"     value="${sessionScope.userBean.birthday}" size="40"   />
    
       </td>
      
      </tr>
      
         <tr>
       <td align="right">邮箱</td>
       <td>
        <input type="email" name="mail" id="mail"    value="${sessionScope.userBean.mail}"     size="40"  class="inpMain"  required/>
   
       </td>
      
      </tr>
     
      <tr>
       <td></td>
       <td>
       
        <input type="hidden" name="id" value="">
        <input name="submit" id="btn_submit" class="btn" type="button"  onclick="savedata();" value="保存数据" />
                <input name="submit" class="btn" type="reset" value="重置数据" />
        
       </td>
      </tr>
     </table>
    </form>
           </div>
 </div>
 <div class="clear"></div>
<div id="dcFooter">
 <div id="footer">
  <div class="line"></div>
  <ul>
   版权所有 © 2013-2015 漳州豆壳网络科技有限公司，并保留所有权利。
  </ul>
 </div>
</div><!-- dcFooter 结束 -->
<div class="clear"></div> </div>
</body>

<script language="javascript">

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



</html>
