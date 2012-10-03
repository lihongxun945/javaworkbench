package DAO.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import DAO.dao.UserDAO;
import DAO.factory.HibernateSessionFactory;
import DAO.vo.User;

public class UserDAOImpl implements UserDAO{

	public Session getSession() throws Exception{
		return HibernateSessionFactory.getSession();
	}
	@Override
	public void save(User user) {
		try{
			Transaction tx = getSession().beginTransaction();
			getSession().save(user);
			tx.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public User get(String username) {
		// TODO Auto-generated method stub
		User user = null;
		Session session = null;
		try {
			String queryString = "from User as model where model.username=?"; 
			session = getSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, username);
			List<Object> list = query.list();
			if(!list.isEmpty()) {
				user = (User) list.get(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			session.close();
		}
		return user;
	}
	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<User>();
		Session session = null;
		try {
			String queryString = "from User as model"; 
			session = getSession();
			Query query = session.createQuery(queryString);
			users = query.list();
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			session.close();
		}
		return users;
	}

}
