package DAO.vo;

import java.io.Serializable;

public class User implements Serializable{

	private String name = "";
	private String password = "";
	private String email = "";
	private short gender = 1;	//true male, false female
	private String bio = "";
	private short isAdmin = 0;
	private String avatarPath = "";
	
	public String getAvatarPath() {
		return avatarPath;
	}
	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public short getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(short isAdmin) {
		this.isAdmin = isAdmin;
	}
	public User() {
		
	}
	public User(String name, String password, String email, short gender, String bio, String avatarPath) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.bio = bio;
		this.avatarPath = avatarPath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public short getGender() {
		return gender;
	}
	public void setGender(short gender) {
		this.gender = gender;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}

}
