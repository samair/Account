package com.webvidhi.stock.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.webvidhi.stock.account.model.Account;
import com.webvidhi.stock.account.repo.UserRepository;

@Service
public class AccountService {

	@Autowired
	public UserRepository usrRepo;
	
	@Autowired
	public EmailService emailService;
	
	public boolean createOrUpdate(Account account) {
		
		boolean status = false;
		try {
			if(!account.getPassword().isEmpty()) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				account.setPassword(encoder.encode(account.getPassword()));
			}
			usrRepo.save(account);
			status = true;
			
			//Send a welcome email
			emailService.sendWelcomeMail(account.getUsername());
		}
		catch(Exception e) {
			System.out.println("exception :" + e.getMessage());
		}
		
		return status;
	}
	
	public List<Account> getAllAccounts() {
		
		List<Account> accounts = null;
		try {
			accounts = usrRepo.findAll();
			
		}
		catch(Exception e) {
			System.out.println("exception :" + e.getMessage());
		}
		return accounts;
		
	}
	
	public Account getUserByEmailId(String userName) {
		System.out.println("user is "+ usrRepo.findByUserName(userName));
		return usrRepo.findByUserName(userName);
	}

	public boolean validate(String userName, String password) {
		
		Account account = usrRepo.findByUserName(userName);
		
		if (null != account ) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
			//System.out.println(account.getPassword()+ "got :" + encoder.encode(password));
			return encoder.matches(password, account.getPassword());
			
		}
		return false;
	}
	
	
	

}
