package com.unblievable.uetsupport.respsec;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "token_info")
public class TokenInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long tokenId;
	@Column(nullable = false)
	public String token;
	@Column(nullable = false)
	public Long userId;
	@Column(nullable = false)
	public Date timeOut;
	@Column(name = "deviceId")
	public String deviceId;

	public TokenInfo() {
	}

	public TokenInfo(String token, Long userId, String deviceId) {
		this.token = token;
		this.userId = userId;
		this.deviceId = deviceId;
		timeOut = new Date(System.currentTimeMillis());
	}

	public String getToken() {
		return token;
	}
}
