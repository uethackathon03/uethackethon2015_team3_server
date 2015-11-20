package com.unblievable.uetsupport.ws.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.unblievable.uetsupport.respsec.TokenInfo;

public class BaseController {
	
	@Autowired
	MessageSource messageResource;
	
	protected TokenInfo token;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	protected boolean checkToken(HttpSession httpSession) {
		token = (TokenInfo) getSession()
				.createCriteria(TokenInfo.class)
				.add(Restrictions.eq("token", httpSession.getAttribute("token")))
				.uniqueResult();
		if (token != null) {
			return true;
		}
		return false;
	}
	
	protected String getMessage(String key, HttpSession httpSession) {
		return messageResource.getMessage(key, new Object[0], new Locale((String) httpSession.getAttribute("language")));
	}
}
