<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'parse.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
<script language="javascript">
//解析XML文件
function parseXML1(){  
              
            try{  
                  
                xmlDoc= new ActiveXObject("Microsoft.XMLDOM");  
            }catch(e){  
                try{  
                      
                    xmlDoc= document.implementation.createDocument("","",null);  
                }catch(e){  
                      
                    alert(e.message);  
                    return;  
                }  
            }
            return xmlDoc;  
}
//解析XML的字符串。
var loadXML3;
if (typeof window.DOMParser != "undefined") {
    loadXML3 = function(xmlStr) {
        return ( new window.DOMParser() ).parseFromString(xmlStr, "text/xml");
    };
} else if (typeof window.ActiveXObject != "undefined" &&
       new window.ActiveXObject("Microsoft.XMLDOM")) {
    loadXML3 = function(xmlStr) {
        var xmlDoc = new window.ActiveXObject("Microsoft.XMLDOM");
        xmlDoc.async = "false";
        xmlDoc.loadXML(xmlStr);
        return xmlDoc;
    };
    //window.alert("aa = " + parseXml);
} else {
    throw new Error("No XML parser found");
}

//解析XML文件。
loadXML2 = function(xmlFile){
    var xmlDoc=null;
    var agent = navigator.userAgent.toLowerCase();

    //判断浏览器的类型
    //支持IE浏览器
    if(agent.indexOf("msie") > 0){
        //alert("22");
        var xmlDomVersions = ['MSXML.2.DOMDocument.6.0','MSXML.2.DOMDocument.3.0','Microsoft.XMLDOM'];
        for(var i=0;i<xmlDomVersions.length;i++){
            try{
                xmlDoc = new ActiveXObject(xmlDomVersions[i]);
                break;
            }catch(e){
            }
        }
    }
    //支持firefox浏览器
     else if(agent.indexOf("firefox") > 0){
        try{
            xmlDoc = document.implementation.createDocument('','',null);
        }catch(e){
        }
    } else{//谷歌浏览器
        var oXmlHttp = new XMLHttpRequest() ;
        oXmlHttp.open( "GET", xmlFile, false ) ;
        oXmlHttp.send(null) ; 
        return oXmlHttp.responseXML;
    }
     if(xmlDoc!=null){
        xmlDoc.async = false;
        xmlDoc.load(xmlFile);
    } 
    return xmlDoc;
}



function parseXML2(){
	var xmlFile = "<%=path%>/xml/user.xml";
	var xmlDoc = parseXML1();
	
	xmlDoc.async = false;  
    xmlDoc.load(xmlFile); 
	var studentList = xmlDoc.getElementsByTagName("student");
	window.alert(studentList.length);	
	
	
	//使用loadXML对象的方法
	//var xmlDoc = loadXML2(xmlFile);
	//studentList = xmlDoc.getElementsByTagName("student");
	//window.alert(studentList.length);
	
	//使用LoadXML3的方法,读取XML字符串，不是XML文件。
	//xmlDoc = loadXML3("<root><student id='1'></student><student id='2'></student></root>");
	//studentList = xmlDoc.getElementsByTagName("student");
	//window.alert(studentList.length);	
	
	//循环取出对应的值。
	for(var i=0;i<studentList.length;i++){
		var student = studentList[i];
		var id = student.getAttribute("id");
		//<stu_name>张三</stu_name>
		var stu_name = student.getElementsByTagName("stu_name")[0].firstChild.nodeValue;
		var stu_age = student.getElementsByTagName("stu_age")[0].firstChild.nodeValue;
		var stu_sex = student.getElementsByTagName("stu_sex")[0].firstChild.nodeValue;
		var stu_content = student.getElementsByTagName("stu_content")[0].firstChild.nodeValue;
		window.alert(id + "\t" + stu_name+ "\t" + stu_age+ "\t" + stu_sex+ "\t" + stu_content);
	}
}
</script>

  </head>
  
  <body>
   <input type="button" value="解析XML的数据" onclick="parseXML2();"/>
  </body>
</html>
