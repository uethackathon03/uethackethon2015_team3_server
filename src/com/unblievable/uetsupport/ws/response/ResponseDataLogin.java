package com.unblievable.uetsupport.ws.response;

import com.unblievable.uetsupport.models.Student;


public class ResponseDataLogin {
	public String token;
	public Student student;

	public ResponseDataLogin() {}
	
	public ResponseDataLogin(String token, Student student) {
		super();
		this.token = token;
		this.student = student;
	}
	
}
