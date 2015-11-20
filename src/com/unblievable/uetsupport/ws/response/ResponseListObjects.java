package com.unblievable.uetsupport.ws.response;

public class ResponseListObjects<T> {
	public Integer success;
	public String message;
	public Integer totalItem;
	public T data;
	
	public ResponseListObjects(boolean success, String message, Integer totalItem, T data) {
		this(success ? 1 : 0, message, totalItem, data);
	}

	private ResponseListObjects(int success, String message, Integer totalItem,T data) {
		this.success = success;
		this.message = message;
		this.totalItem = totalItem;
		this.data = data;
	}

}
