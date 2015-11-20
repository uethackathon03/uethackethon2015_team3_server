package com.unblievable.uetsupport.ws.response;

public class ResponseObjectDetail<T> {
	
	public Integer success;
	public String message;
	public T data;
	
	public ResponseObjectDetail(boolean success, String message, T data) {
		this(success ? 1 : 0, message, data);
	}
	
	
	public ResponseObjectDetail(Integer success, String message, T data) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
}
