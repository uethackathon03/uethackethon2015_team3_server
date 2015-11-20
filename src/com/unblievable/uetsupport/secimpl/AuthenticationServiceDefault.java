package com.unblievable.uetsupport.secimpl;

import org.springframework.ldap.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.unblievable.uetsupport.respsec.AuthenticationService;

public class AuthenticationServiceDefault implements AuthenticationService {
	
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationServiceDefault(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public void authenticate(String username, String password) {
		System.out.println(" *** AuthenticationServiceImpl.authenticate");
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
		try {
			authentication = authenticationManager.authenticate(authentication);
			// Here principal=UserDetails (UserContext in our case), credentials=null (security reasons)
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (AuthenticationException e) {
			System.out.println(" *** AuthenticationServiceImpl.authenticate - FAILED: " + e.toString());
		}
	}

	@Override
	public boolean checkToken(String token) {
	
		return true;
	}

	@Override
	public void logout(String token) {
		// TODO Auto-generated method stub
		
	}

}
