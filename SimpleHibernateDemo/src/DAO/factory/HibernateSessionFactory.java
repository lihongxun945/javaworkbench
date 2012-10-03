package DAO.factory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.omg.CORBA.PUBLIC_MEMBER;

public class HibernateSessionFactory {
	private static final String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	private static final ThreadLocal<Session> THREAD_LOCAL = new ThreadLocal<Session>();
	private static Configuration configuration = new Configuration();
	private static SessionFactory sessionFactory;
	private static String configFile = CONFIG_FILE_LOCATION;
	static {
		try{
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static Session getSession() throws Exception {
		Session session = (Session) THREAD_LOCAL.get();
		if(session == null || !session.isOpen()) {
			if(sessionFactory == null) {
				
			}
			session = (sessionFactory != null) ? sessionFactory.openSession() : null;
			THREAD_LOCAL.set(session);
		}
		return session;
	}
	public static void rebuildSessionFactory(){
		try{
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void closeSession() throws Exception {
		Session session = (Session) THREAD_LOCAL.get();
		THREAD_LOCAL.set(null);
		if(session != null){
			session.close();
		}
	}
	
}
