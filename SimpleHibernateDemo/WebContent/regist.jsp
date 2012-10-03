<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Insert title here</title>
</head>
<body>
	<form id="form" method="POST" action="regist_action.jsp">
		<label>用户名：</label><input type="text" name="username" /><br />
		<label>密码：</label><input type="password" name="password" /><br />
		<label>简介：</label><textarea type="text" name="bio" /></textarea><br />
		<input type="submit" value="注册" />
	</form>
</body>
</html>