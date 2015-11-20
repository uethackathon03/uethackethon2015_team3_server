package com.unblievable.uetsupport.secimpl;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.unblievable.uetsupport.common.CommonUtils;
import com.unblievable.uetsupport.dao.AbstractDao;
import com.unblievable.uetsupport.models.Student;
import com.unblievable.uetsupport.respsec.TokenInfo;
import com.unblievable.uetsupport.respsec.TokenManager;

public class TokenManagerSingle extends AbstractDao implements TokenManager {
	
	public TokenManagerSingle(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	private TokenInfo tokenInfo = new TokenInfo();

	@Override
	public TokenInfo createNewToken(Long userId, String deviceId) {
		String token;
		do {
			token = generateToken();
			tokenInfo = (TokenInfo) getSession().createCriteria(TokenInfo.class).add(Restrictions.eq("token", token))
					.uniqueResult();
		} while (tokenInfo != null);
		tokenInfo = new TokenInfo(token, userId, deviceId);
		TokenInfo existToken = (TokenInfo) getSession().createCriteria(TokenInfo.class)
				.add(Restrictions
						.and(Restrictions.eq("userId", userId), Restrictions.eq("deviceId", deviceId)))
				.uniqueResult();
		if (existToken != null) {
			existToken.token = tokenInfo.token;
			existToken.deviceId = deviceId;
			existToken.timeOut = new Date(System.currentTimeMillis());
			getSession().update(existToken);
		} else {
			getSession().save(tokenInfo);
		}
		return tokenInfo;
	}

	private String generateToken() {
		return CommonUtils.convertStringToMD5(RandomStringUtils.randomAlphanumeric(12));
	}

	@Override
	public void removeAllUserById(Long userId) {
		Query query = sessionFactory.getCurrentSession()
				.createQuery("delete TokenInfo where userId = " + String.valueOf(userId));
		query.executeUpdate();
	}

	@Override
	public void removeToken(String token) {
		Query query = sessionFactory.getCurrentSession().createQuery("delete TokenInfo where token = " + token);
		query.executeUpdate();
	}

	@Override
	public Student getUser(String token) {
		TokenInfo tokenInfo = (TokenInfo) getSession().createCriteria(TokenInfo.class)
				.add(Restrictions.eq("token", token)).uniqueResult();
		Student student = (Student) getSession().createCriteria(Student.class).add(Restrictions.eq("userId", tokenInfo.userId))
				.uniqueResult();
		return student;
	}

	@Override
	public Collection<TokenInfo> getUserTokens(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Long> getValidUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}
