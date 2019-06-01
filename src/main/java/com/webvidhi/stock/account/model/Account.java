package com.webvidhi.stock.account.model;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Document(collection="Accounts")
public class Account implements UserDetails {
	
	@Id
	public ObjectId _id;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	private String verificationCode;
	
	private List<SimpleGrantedAuthority> authorities;
	
	private Date verificationExpiryTime;
	
	public Account(String username, String password, List<SimpleGrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private double balance;
	
	@Indexed(unique = true)
	private String username;


	public void setUserName(String userName) {
		this.username = userName;
	}

	public ObjectId getAccountId() {
		return _id;
	}

	public void setAccountId(ObjectId accountId) {
		this._id = accountId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.authorities;
	}

	@Override
	public String getUsername() {

		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Date getVerificationExpiryTime() {
		return verificationExpiryTime;
	}

	public void setVerificationExpiryTime(int expiryTimeMins) {
	    
	    Calendar now = Calendar.getInstance();
	    now.add(Calendar.MINUTE, expiryTimeMins);
		this.verificationExpiryTime = now.getTime();
	}

}
