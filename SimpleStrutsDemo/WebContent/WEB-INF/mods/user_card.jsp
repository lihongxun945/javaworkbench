<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<% String path = request.getContextPath(); %>
<h3>
ÎÒµÄÃûÆ¬
</h3>
<ul>
<li><img src="<%=path %>/avatars/<s:property value='#request.user.avatarPath' />" /></li>
<li>name: <s:property value="#request.user.name" /></li>
<li>email: <s:property value="#request.user.email" /></li>
<li>gender: <s:property value="#request.user.gender" /></li>
<li>bio: <s:property value="#request.user.bio" /></li>
</ul>