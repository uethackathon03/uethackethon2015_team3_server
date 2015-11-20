package com.unblievable.uetsupport.respsec;

import java.util.Collection;
import java.util.Map;

import com.unblievable.uetsupport.models.Student;

/**
 * Manages tokens - separated from {@link AuthenticationService}, so we can
 * implement and plug various policies.
 */
public interface TokenManager {

	/**
	 * Creates a new token for the user and returns its {@link TokenInfo}. It
	 * may add it to the token list or replace the previous one for the user.
	 * Never returns {@code null}.
	 */
	TokenInfo createNewToken(Long userId, String devideId);
	
	/** Removes all tokens for user. */
	void removeAllUserById(Long userId);
	
	/** Removes a single token. */
	void removeToken(String token);
	
	/** Returns user details for a token. */
	Student getUser(String token);
	
	/** Returns a collection with token information for a particular user. */
	Collection<TokenInfo> getUserTokens(Long userId);

	/** Returns a map from valid tokens to users. */
	Map<String, Long> getValidUsers();
	
}
