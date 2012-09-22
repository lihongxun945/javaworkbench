<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
<style>

	.users th{
		background: #ddd;
		border: 1px solid #333;
	}
	.users td{
		border: 1px solid #bbb;
	}
	.users tr.odd{
		background: #f4f9ff;
	}
</style>
</head>
<body>
<h2>Admin Page</h2>
all user count <s:property value="#request.users.size()" />, here is the list:
<table class="users">
<tr><th>avatar</th><th>name</th><th>password</th><th>email<th>gender</th><th>bio</th><th>isadmin</th></tr>
<s:iterator value="#request.users" id="user" status="status">
	<tr <s:if test="#status.odd">class="odd"</s:if>>
	<td><img style="width:25px;height:25px" src="<%=path %>/avatars/<s:property value="#user.avatarPath"></s:property>"></td>
	<td><s:property value="#user.name"></s:property></td>
	<td><s:property value="#user.password"></s:property></td>
	<td><s:property value="#user.email"></s:property></td>
	<td><s:property value="#user.gender"></s:property></td>
	<td><s:property value="#user.bio"></s:property></td>
	<td><s:property value="#user.isAdmin"></s:property></td>
	</tr>
</s:iterator>
</table>

</body>
</html>