package admin;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import DAO.dao.UserDAO;
import DAO.impl.UserDAOImpl;
import DAO.vo.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Admin extends ActionSupport{
	public String execute(){
		ActionContext ac = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session =  request.getSession();
		UserDAO userDAO = new UserDAOImpl();
		ArrayList<User> users = userDAO.getAll();
		ac.put("allUsers", users);
		ac.put("usersCount", users.size() + "");
		request.setAttribute("users", users);
		return "success";
	}
}
