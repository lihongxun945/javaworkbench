<%@ page language="java" contentType="text/html; charset=utf8"
    import="java.util.*, DAO.vo.*, DAO.impl.UserDAOImpl, DAO.dao.UserDAO"
    pageEncoding="utf8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Insert title here</title>
</head>
<body>

<%
	out.println("<h3>所有用户：</h3>");
	UserDAO userDAO = new UserDAOImpl();
	List<User> users = userDAO.getAll();
	if(users.size() == 0){
		out.println("暂时没有用户");
	}else {
		StringBuffer sb = new StringBuffer();	
		sb.append("<table><tr><th>username</td><td>password</th><th>bio</th><th>topics</th></tr>");
		for(int i=0, len=users.size(); i < len; i++) {
	User user = users.get(i);
	Set<Integer> topics = user.getTopics();
	sb.append("<tr><td>"+user.getUsername()+"</td><td>"+user.getPassword()+"</td><td>"+
		user.getBio()+"</td><td>"+(topics == null ? 0 : topics)+"</td></tr>");	
		}
		sb.append("</table>");
		out.println(sb);
	}
%>
</body>
</html>