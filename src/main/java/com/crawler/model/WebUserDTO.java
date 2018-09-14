package com.crawler.model;

import java.math.BigDecimal;

/**
 * Created by daiyong on 2018/8/10.
 * email daiyong@qiyi.com
 */
public class WebUserDTO {
	private String userId;
	private String userName;
	private BigDecimal balance;
	private String coinType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCoinType() {
		return coinType;
	}

	public void setCoinType(String coinType) {
		this.coinType = coinType;
	}
}
