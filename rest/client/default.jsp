<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 

<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
    <title>Email</title>
    
    <style type="text/css">
	*{margin: 0;padding: 0;}
	form{margin: 0 auto;padding:15px; width: 300px;height:400px;text-align: center;}
	#submit{padding: 10px}
	#submit input{width: 50px;height: 24px;}
    </style>

    </head>
    
    <body>
        <div class="wrapper">
		<form action="<%=request.getContextPath()%>/WebContent" method="post">
			<label>邮箱地址:</label>
				<input type="text" name="address"  value="${param.userName}"/><br><br>
			<label>信息：</label>
				<textarea type="text"  name="msg" style="height:420px;width:400px;"></textarea><br>
				
			
			<font color="red">
				<%
					if(request.getAttribute("message")!= null){
						out.print(request.getAttribute("message"));
					}
				%>
			</font>
			
			<div id="submit">
				<input type="submit" value="发送"/>
			</div>
				<div id="add">
				<input type="submit" value="添加地址"/>
			</div>
		</form>
	</div>

    </body>
</html>