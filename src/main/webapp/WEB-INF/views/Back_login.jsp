<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/login.css"/>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/static/js/jquery-1.11.3.js"></script>
<!-- 导入easyui类库 -->
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/static/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/static/js/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/static/js/easyui/jquery.easyui.min.js"></script>
<title>login</title>
</head>
<script type="text/javascript">
	function cleanUsername(){
		$(":text").val("") ;
	}
	function cleanPasswd(){
		$(":password").css("background","white");
	}
	function validate(){
		var login=$(".username").val();
	    var pwd=$(".password").val();
	    if(login=="" || login == null){
	    	$.messager.alert("提示","用户名不能为空")
	        return false;
	    }
	    if(pwd=="" || pwd == null){
	    	$.messager.alert("提示","请填写登录密码");
	        return false;
	    }
		return true
	}
	
</script>
<body>
	<b style="color:red;position:absolute;top:45%;left:40%">${msg }</b>
	<form  action="${pageContext.request.contextPath}/toLogin/toMainBack" method="post" onsubmit="return validate();">
			<table>
				<thead>
					<tr>
						<td colspan="2" rowspan="3" style="font-size:35px;color:#205BB8"><span style="font-size:26px">智晟</span>|后台管理系统<br/><br/></td>
					</tr>
					<tr></tr>
					<tr></tr>
					<tr>
						<td><input type="text" name="username" value="用户名" class="username" onfocus="cleanUsername();"/></td>
					</tr>
					<tr>
						<td><input type="password" name="password" value="" class="password" onfocus="cleanPasswd();"/></td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="登录" class="login" onmousemove="this.className='input_off'" onmouseout="this.className='input_out'" /></td>
					</tr>
				</thead>
			</table>
	</form>
</body>
</html>