package DAO.impl;

import java.security.spec.RSAKeyGenParameterSpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DAO.dao.UserDAO;
import DAO.factory.ConnectionFactory;
import DAO.vo.User;

public class UserDAOImpl implements UserDAO{
	private Connection con = null;
	private PreparedStatement pre = null;
	private ResultSet rs = null;
	
	private Connection getConnection() throws Exception{
		Connection connection = null;
		connection = ConnectionFactory.getInstance().getConnection();
		return connection;
	}
	public User get(String userName, String password) {
		User user = null;
		if(userName == null || userName.length() == 0 || password == null || password.length() == 0) return null;
		try{
			this.con = this.getConnection();
			String queryString = "select name, password, email, gender, bio, is_admin, avatar_path from users where name=? && password=?"; 
			this.pre = this.con.prepareStatement(queryString);
			pre.setString(1, userName);
			pre.setString(2, password);
			this.rs = pre.executeQuery();
			if(rs.next()){
				user = new User();
				user.setName(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setGender(rs.getShort(4));
				user.setBio(rs.getString(5));
				user.setIsAdmin(rs.getShort(6));
				user.setAvatarPath(rs.getString(7));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				con.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return user;
	}
	public User get(String userName) {
		User user = null;
		try{
			this.con = this.getConnection();
			String queryString = "select name, password, email, gender, bio, is_admin, avatar_path from users where name=?"; 
			this.pre = this.con.prepareStatement(queryString);
			pre.setString(1, userName);
			this.rs = pre.executeQuery();
			if(rs.next()){
				user = new User();
				user.setName(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setGender(rs.getShort(4));
				user.setBio(rs.getString(5));
				user.setIsAdmin(rs.getShort(6));
				user.setAvatarPath(rs.getString(7));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				con.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return user;
	}
	
	@Override
	public boolean add(User user) {
		// TODO Auto-generated method stub
		boolean result = false;
		try{
			this.con = this.getConnection();
			String queryString = "insert into users(name, password, email, gender, bio, avatar_path) values(?, ?, ?, ?, ?, ?)"; 
			this.pre = this.con.prepareStatement(queryString);
			pre.setString(1, user.getName());
			pre.setString(2, user.getPassword());
			pre.setString(3, user.getEmail());
			pre.setShort(4, user.getGender());
			pre.setString(5, user.getBio());
			pre.setString(6, user.getAvatarPath());
			result = pre.executeUpdate() == 0 ? false : true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = false;
			return false;
		}finally{
			try {
				this.con.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			return result;
		}
	}
	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public ArrayList<User> getAll() {
		// TODO Auto-generated method stub
		ArrayList<User> users = new ArrayList<User>();
		try{
			this.con = this.getConnection();
			String queryString = "select name, password, email, gender, bio, is_admin, avatar_path from users"; 
			this.pre = this.con.prepareStatement(queryString);
			this.rs = pre.executeQuery();
			while(rs.next()){
				User user = null;
				user = new User();
				user.setName(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setGender(rs.getShort(4));
				user.setBio(rs.getString(5));
				user.setIsAdmin(rs.getShort(6));
				user.setAvatarPath(rs.getString(7));
				users.add(user);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				con.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return users;
	}
}
