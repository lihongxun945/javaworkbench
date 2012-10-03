package DAO.vo;

import java.util.ArrayList;
import java.util.HashSet;

import java.util.*;

public class User {
	private int id;
	private String username;
	private String password;
	private String bio;
	private Set<Integer> topics = new HashSet<Integer>();


	public User(){
		
	}
	public User(String username,String password, String bio, Set<Integer> topics){
		this.username = username;
		this.password = password;
		this.bio = bio;
		this.topics = topics;
	}
		
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Set<Integer> getTopics() {
		return topics;
	}
	public void setTopics(Set<Integer> topics) {
		this.topics = topics;
	}

}
