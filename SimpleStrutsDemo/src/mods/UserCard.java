package mods;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import DAO.dao.UserDAO;
import DAO.impl.UserDAOImpl;
import DAO.vo.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserCard extends ActionSupport{
	public String execute(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String userName = (String) request.getAttribute("userName");
		UserDAO userDAO = new UserDAOImpl();
		User user = userDAO.get(userName);
		request.setAttribute("user", user);
		return "success";
	}
}
