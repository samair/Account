package com.webvidhi.stock.account.model;

public class Status {

	private boolean status;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public Status(boolean status) {
		setStatus(status);
		
	}
}
