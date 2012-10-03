package DAO.dao;

import java.util.List;

import DAO.vo.User;

public interface UserDAO {
	public void save(User user);
	public User get(String name);
	public List<User> getAll();
}
