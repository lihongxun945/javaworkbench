<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"
    import="java.util.*, DAO.vo.User, DAO.impl.UserDAOImpl, DAO.dao.UserDAO"
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8">
<title>Insert title here</title>
</head>
<body>
<%!
	//转码
	public static String encode(String s) {
		String result = s;
		try{
			byte[] b = s.getBytes("iso-8859-1");
			result = new String(b, "utf-8");
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return result;
		}
	}
%>
<%
	String username = encode(request.getParameter("username"));
	String password = encode(request.getParameter("password"));
	String bio = encode(request.getParameter("bio"));
	UserDAO userDAO = new UserDAOImpl();
	User user = new User(username, password, bio, null);
	userDAO.save(user);
	user = userDAO.get(username);
	if(user == null){
		out.println("<h3>注册失败！</h3>");
	}else {
		out.println("<h3>注册成功</h3>");
		out.println("<ul><li>username: " + user.getUsername());
		out.println("</li><li>password: " + user.getPassword());
		out.println("</li><li>bio: " + user.getBio());
		out.println("</li></ul>");
		
	}
%>

</body>
</html>