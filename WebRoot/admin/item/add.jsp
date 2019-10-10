<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/admin/common_header.jsp" %>
<c:import url="/admin/common_top.jsp"></c:import>
<c:import url="/admin/common_left.jsp">

<c:param name="menu_id" value="202"></c:param>

</c:import>

<script language="javascript" type="text/javascript" src="<%=path%>/js/datepicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript" src="<%=path%>/js/jquery-form.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script language="javascript">
function changeSmall(parentid){
    	
    	var loadURL ="${cpath}/servlet/ItemServlet?task=loadsmall&parentid="+parentid;
    	$.get(loadURL,function(jsonData){
    	
    	
    	if(jsonData != null){
    
			 $("#smallclass > option").remove();
$("#smallclass").append("<option value='0'></option>");
			 
    	    for(var i = 0;i<jsonData.length;i++){
    	    $("#smallclass").append("<option value='"+jsonData[i].classid+"'>"+jsonData[i].classname+"</option>");
    	    }
   	    
    	}
    	
    	},"json");
  
}
 function saveData(){
    
    
      
      $("#form1").ajaxSubmit({
      url : "${cpath}/servlet/ItemServlet?task=save&data="+Math.random(),
      type :"post",		
      dataType : "json",
      async : true,
      success : function (data){
      
      var flag  = data.flag;
      var message = data.message;
      if(flag == true)
      {
            var confirmFlag = window.confirm("数据保存成功,是否继续添加数据?");
      		if(confirmFlag == true){
      		$("#btn_submit").attr("disabled",true);
      		
      		$("#itemname").val("");
      		$("#bigclass").val("0");
      		$("#smallclass").html("");
      		$("#itemprice").val("");
      		$("#temp").val("");
      		$("#itemfile").val("");



      		}
      		else
      		{
      		var listURL = "${cpath}/servlet/ItemServlet?task=list";
      		
      		window.location.href = listURL;
      		}
      		      		
      		

      }
else{
         $("#btn_submit").attr("disabled",true);
     
      window.alert("数据保存出错，错误原因"+message);
}
      }
      
      ,
      error : function(){
      }
      });
      
	
} 

</script>



</div>
<!-- dcHead 结束 -->

 <div id="dcMain">
   <!-- 当前位置 -->
<div id="urHere">DouPHP 管理中心<b>></b><strong>添加商品</strong> </div>   <div class="mainBox" style="height:auto!important;height:550px;min-height:550px;">
            <h3><a href="${pageContext.request.contextPath}/servlet/ItemServlet?task=list" class="actionBtn">商品列表</a>添加商品</h3>
    <form action="${pageContext.request.contextPath}/servlet/ItemServlet?task=list" method="post" id="form1" enctype="multipart/form-data">
     <table width="100%" border="0" cellpadding="8" cellspacing="0" class="tableBasic">
     
     <tr>
     <td width="90" align="right">商品名称</td>
     
     <td>
     <input type="text" name="itemname" id="itemname" value="" class="inpMain">
     </td>
     
     
     
     </tr>
      <tr>
       <td width="90" align="right">商品大类</td>
       <td>
               <select name= "bigclass" id="bigclass" style="width: 200px;" onchange="changeSmall(this.value);">
                  <option value="0"></option>
               <c:forEach var="classinfo" items="${requestScope.biglist}">
           
               <option value="${classinfo.classid}">${classinfo.classname}</option>
               
               </c:forEach>
               
               
               </select>
               
       
       </td>
      </tr>
      <tr>
       <td align="right" >商品小类</td>
       <td>
        <select name="smallclass" id="smallclass" style="width: 200px;">
       </select>
       </td>
      </tr>
      <tr>
       <td align="right">商品价格</td>
       <td>
        <input type="text" name="itemprice" id="itemprice" value="0" size="40" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}" 
        class="inpMain" />
        
       </td>
      </tr>
            <tr>
       <td align="right" valign="top">商品描述</td>
       <td>
        <textarea id="temp" name="temp"  class="textArea" rows="3" clos="40"></textarea>
       </td>
      </tr>
      <tr>
       <td align="right">商品图片</td>
       <td>
        <input type="file" name="itemfile" id="itemfile" size="38" class="inpFlie" />
        <img src="images/icon_no.png"></td>
      </tr>
      <tr>
       <td align="right">添加时间</td>
       <td>
        <input type="text" name="addtime" onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd'})" value="${requestScope.addtime}" size="50" class="inpMain Wdate" />
       </td>
      </tr>
     
      <tr>
       <td></td>
       <td>
       
        <input type="hidden" name="id" value="">
        <input name="submit" id="btn_submit" class="btn" type="button" onclick="saveData();" value="保存数据" />
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
</html>
