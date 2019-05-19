package com.webvidhi.stock.account.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.webvidhi.stock.account.model.Account;
import com.webvidhi.stock.account.repo.UserRepository;

@Component
public class MongoUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
	
	 System.out.println("loaduser name " + username);
	 Account account = repository.findByUserName(username);
	 System.out.println("Found user :" + account.getPassword());
	 
	 if (account == null) {
		 throw new UsernameNotFoundException("Account not found with us...");
	 }
	 
	 List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
	 
	 return new Account(account.getUsername(), account.getPassword(),authorities);
	}
	
	

}
