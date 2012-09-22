package actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import DAO.dao.UserDAO;
import DAO.impl.UserDAOImpl;
import DAO.vo.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Regist extends ActionSupport {
	private String username = null;
	private String password = null;
	private String repassword = null;
	private String bio = null;
	private String email = null;
	private short gender = 1;
	
	private String savePath = "/avatars";
	public File getAvatar() {
		return avatar;
	}
	public void setAvatar(File avatar) {
		this.avatar = avatar;
	}
	private File avatar = null;
	

	public String getSavePath() {
		return ServletActionContext.getRequest().getRealPath(savePath);
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public short getGender() {
		return gender;
	}
	public void setGender(short gender) {
		this.gender = gender;
	}
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
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	public String regist() throws Exception{
		//upload file
		Date date = new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		String time=df.format(date);
		String uploadName = getAvatar().getName();
		String type = uploadName.substring(uploadName.lastIndexOf(".")+1);
		String token = time + "-" + ((int) (Math.random() * 10000) + 10000) + "." + type;
		String fileName = getSavePath() + "/" + token;
		File newFile = new File(fileName);
		if(!newFile.exists()) {
			newFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(newFile);
		FileInputStream fis = new FileInputStream(getAvatar());
		byte[] b = new byte[1024];
		int len = 0;
		while((len=fis.read(b)) > 0) {
			fos.write(b, 0, len);
		}
		ActionContext ac = ActionContext.getContext();
		UserDAO userDAO = new UserDAOImpl();
		User user = new User(this.getUsername(), this.getPassword(), this.email, this.getGender(), this.getBio(), token);
		if(userDAO.add(user)){
			ac.put("msg", "注册成功，请登陆！");
			return "success";
		}
		ac.put("msg", "注册失败，未知错误！");
		return "input";
	}
	public void validate(){
		if(!password.equals(repassword)) {
			addFieldError("repassword", "两次密码输入不一致！");
		}
		UserDAO userDAO = new UserDAOImpl();
		if(userDAO.get(this.username) != null) {
			addFieldError("username", "该用户名已经被注册");
		}
		if(this.getAvatar() == null){
			addFieldError("avatar", "请选择正确的头像图片");
		}
	}
}
