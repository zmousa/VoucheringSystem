package com.jee.kernel.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

import com.jee.api.dao.Filter;
import com.jee.api.dao.HelperDao;
import com.jee.model.userAdmin.JeeUser;

public class HelperDaoImpl implements HelperDao {
	private static final Logger log = Logger.getLogger(HelperDaoImpl.class);

	private SessionFactory sessionFactory;


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Object save(Object o) throws Exception {
		log.debug("saving " + o.getClass().getSimpleName() + " instance");
		try {
			o = getSessionFactory().getCurrentSession().save(o);
			log.debug("save successful");
			return o;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw new Exception(re);
		}
	}

	@Override
	public Object update(Object o) throws Exception {
		log.debug("updating " + o.getClass().getSimpleName() + " instance");
		try {
			o = getSessionFactory().getCurrentSession().merge(o);
			log.debug("update successful");
			return o;
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw new Exception(re);
		}
	}

	@Override
	public void delete(Object o) throws Exception {
		log.debug("deleting " + o.getClass().getSimpleName() + " instance");
		try {
			getSessionFactory().getCurrentSession().delete(o);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw new Exception(re);
		}

	}

	@Override
	public Filter entity(Class<?> clazz) throws Exception {
		return new Filter(clazz,getSessionFactory().getCurrentSession());
	}

	@Override
	public List<?> search(String querySql) throws Exception {
		List<?> resutl= getSessionFactory().getCurrentSession().createQuery(querySql).list();
		return resutl;
	}
	
	@Override
	public void executeUpdate(String querSql){
	    getSessionFactory().getCurrentSession().createQuery(querSql).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<JeeUser> getUsersList(String username,boolean isEnabled, boolean isAdmin){
		try
		{
			String queryStr = "from JeeUser r where 1=1 ";
			if(username!=null && !username.equals("")) queryStr+= "and userName like'%" + username+"%'";
			if(isEnabled) queryStr+=" and enabled=1";
			if(isAdmin) queryStr+=" and isAdmin=1";

			List<JeeUser> results = getSessionFactory().getCurrentSession().createQuery(queryStr).list();
			return results;
		}
		catch(RuntimeException re)
		{
			log.error(" ", re);
			throw re;
		}

	}
	@SuppressWarnings("unchecked")
	public List<JeeUser> getAllUsers() {
		String queryStr = "from JeeUser";
		List<JeeUser> results = getSessionFactory().getCurrentSession().createQuery(queryStr).list();
		return results;
	}

}
