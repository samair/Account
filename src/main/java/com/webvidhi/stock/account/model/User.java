package com.webvidhi.stock.account.model;

import org.springframework.beans.factory.annotation.Autowired;


import com.webvidhi.stock.account.service.AccountService;

public class User {
	
	@Autowired
	private AccountService acService;
	
	private String userName;
	
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
