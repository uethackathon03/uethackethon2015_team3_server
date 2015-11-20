package com.unblievable.uetsupport.respsec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.crypto.codec.Base64;

@Transactional
public class TokenAuthenticationFilter extends GenericFilterBean {
	
	private static final String HEADER_TOKEN  = "token";
	private static final String HEADER_USERNAME = "username";
	private static final String HEADER_PASSWORD = "password";
	
	private final AuthenticationService authenticationService;
	
	public TokenAuthenticationFilter(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
	
	/** Request attribute that indicates that this filter will not continue with the chain. Handy after login/logout, etc. */
	private static final String REQUEST_ATTR_DO_NOT_CONTINUE = "MyAuthenticationFilter-doNotContinue";
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println(" *** MyAuthenticationFiler.doFilter *** ");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		// If we're not authenticated, we don't bother with logout at all.
		// Logout does not work in the same request with login - this does not make sense, because logout works with token and login returns it.
		
		// Login works just fine even when we provide token that is valid up to this request, because then we get a new one.
		
		checkLogin(httpRequest, httpResponse);
		HttpSession session = httpRequest.getSession();
		session.setAttribute("token", httpRequest.getHeader(HEADER_TOKEN));
		session.setAttribute("language", httpRequest.getHeader("Accept-Language"));
		
		chain.doFilter(request, response);
		
		System.out.println(" === AUTHENTICATION: " + SecurityContextHolder.getContext().getAuthentication());
	}
	
	private void checkLogin(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		String authorization = httpRequest.getHeader("Authorization");
		String username = httpRequest.getHeader(HEADER_USERNAME);
		String password = httpRequest.getHeader(HEADER_PASSWORD);
		
		if (authorization != null) {
			checkBasicAuthorization(authorization, httpResponse);
			doNotContinueWithRequestProcessing(httpRequest);
		} else if (username != null && password != null) {
			authenticationService.authenticate(username, password);
			doNotContinueWithRequestProcessing(httpRequest);
		}
	}
	
	
	private void checkBasicAuthorization(String authorization, HttpServletResponse httpResponse) throws IOException {
		StringTokenizer tokenizer = new StringTokenizer(authorization);
		if (tokenizer.countTokens() < 2) {
			return;
		}
		if (!tokenizer.nextToken().equalsIgnoreCase("Basic")) {
			return;
		}
		String base64 = tokenizer.nextToken();
		String loginPassword = new String(Base64.decode(base64.getBytes(StandardCharsets.UTF_8)));
		
		System.out.println("loginPassword = " + loginPassword);
		tokenizer = new StringTokenizer(loginPassword, ":");
		System.out.println("tokenizer = " + tokenizer);
		authenticationService.authenticate(tokenizer.nextToken(), tokenizer.nextToken());
	}
	
	/**
	 * This is set in cases when we don't want to continue down the filter chain. This occurs
	 * for any {@link HttpServletResponse#SC_UNAUTHORIZED} and also for login or logout.
	 */
	private void doNotContinueWithRequestProcessing(HttpServletRequest httpRequest) {
		httpRequest.setAttribute(REQUEST_ATTR_DO_NOT_CONTINUE, "");
	}

}
