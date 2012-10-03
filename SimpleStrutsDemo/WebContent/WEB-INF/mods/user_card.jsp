<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<% String path = request.getContextPath(); %>
<h3>
我的名片
</h3>
<ul>
<li><img src="<%=path %>/avatars/<s:property value='#request.user.avatarPath' />" /></li>
<li>name: <s:property value="#request.user.name" /></li>
<li>email: <s:property value="#request.user.email" /></li>
<li>gender: <s:property value="#request.user.gender" /></li>
<li>bio: <s:property value="#request.user.bio" /></li>
</ul>