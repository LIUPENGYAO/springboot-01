<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script  src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
</head>

<body>

  <form id="codeform">

		<input type="text" placeholder="填写手机号" name="phone">
		<button type="button" id="sendCode">发送验证码</button><br>
		<font id="daojishi" color="red" ></font>
		<br>

		<input type="text" placeholder="填写验证码" name="verify_code">
		<button type="button" id="verifyCode">确定</button>
		<font id="result" color="green" ></font><font id="error" color="red" ></font>

	</form>


</body>
<script type="text/javascript">

var t=20;//设定倒计时的时间
var id;
function refer(){
    if(t==100) {
        $("#daojishi").text("发送不能超过三次");
        return;
	}
    $("#daojishi").text("请于"+t+"秒内填写验证码 "); // 显示倒计时
    t--; // 计数器递减 
    if(t<=0){
    	clearInterval(id);
    	$("#daojishi").text("验证码已失效，请重新发送！ ");
    }
} 

$(function(){
	$("#sendCode").click( function () {
	       
/*		   $.post("sendCode",$("#codeform").serialize(),function(data){
	    	 if(data=="success"){
	    		 id= setInterval("refer()",1000);//启动1秒定时
	   		 }
		  });*/
        $.ajax({
            url:"${pageContext.request.contextPath}/user/sendCode",
            type:"post",
            data:$("#codeform").serialize(),
            //dataType:'json',
            //contentType: "application/json",
            success:function(res) {
                console.log(res);
                // /if(res.code == 1) {
                if(res == "success") {
                    id= setInterval("refer()",1000);//启动1秒定时
                }else if(res == "threetimes") {
                   /* $("#result").attr("color","red");
                    $("#result").text("每天只能发送三次");
                    $("#daojishi").text("每天只能发送三次")*/

                    clearInterval(id);
                    t=100;
                } else {
                    layer.alert("fail");
                }
            },
            error: function() {
                alert("ajax错误");
            }
        });
    });
	
	$("#verifyCode").click( function () {
	    
	/*	   $.post("verifiCode",$("#codeform").serialize(),function(data){
	    	 if(data=="success"){
	    		 $("#result").attr("color","green");
	    		 $("#result").text("验证成功");
	    		 clearInterval(id);
	    		 $("#daojishi").text("")
	   		 }else{
	    		 $("#result").attr("color","red");
	    		 $("#result").text("验证失败");
	   		 }
		  }); */
        $.ajax({
            url:"${pageContext.request.contextPath}/user/verifiCode",
            type:"post",
            data:$("#codeform").serialize(),
            //dataType:'json',
            //contentType: "application/json",
            success:function(res) {
                console.log(res);
                if(res == "success") {
                    $("#result").attr("color","green");
                    $("#result").text("验证成功");
                    clearInterval(id);
                    $("#daojishi").text("")
                }   else {
                    $("#result").attr("color","red");
                    $("#result").text("验证失败");
                    $("#daojishi").text("")
                }
            },
            error: function() {
                alert("ajax错误");
            }
        });
    });
	
	
});
</script>
</html>