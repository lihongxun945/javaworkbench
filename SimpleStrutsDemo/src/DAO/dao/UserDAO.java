package DAO.dao;

import java.util.ArrayList;

import DAO.vo.User;

public interface UserDAO{
	public User get(String username);
	public User get(String username, String password);
	public ArrayList<User> getAll();
	public boolean delete();
	public boolean update();
	boolean add(User user);
}
