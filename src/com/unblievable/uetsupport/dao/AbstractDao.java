package com.unblievable.uetsupport.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractDao {
	
	protected SessionFactory sessionFactory;
	
	public AbstractDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	protected void persist(Object entity) {
		getSession().persist(entity);
	}
}
