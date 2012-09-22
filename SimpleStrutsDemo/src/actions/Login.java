package actions;

import java.util.Map;

import javassist.expr.NewArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.omg.CORBA.Request;

import DAO.dao.UserDAO;
import DAO.impl.UserDAOImpl;
import DAO.vo.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class Login extends ActionSupport{
	private HttpSession session = null;
	private String username = null;
	private String password = null;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String check(){
		ActionContext ac = ActionContext.getContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		this.session =  request.getSession();
		if(this.username != null && this.username.length() > 0){
			UserDAO userDAO = new UserDAOImpl();
			User user = userDAO.get(this.username, this.password);
			if(user != null) {
				this.session.setAttribute("userName", this.username);
				this.session.setAttribute("isAdmin", user.getIsAdmin());
				ac.put("userName", this.username);
				return "success";
			}else{
				this.session.setAttribute("userName", "");	//登陆失败，删除登陆信息
				ac.put("msg", "用户名或密码错误!");
				return "error";
			}
		}else{
			this.session.setAttribute("userName", "");
			ac.put("msg", "请填写用户名和密码!");
			return "error";
		}
	}
}
