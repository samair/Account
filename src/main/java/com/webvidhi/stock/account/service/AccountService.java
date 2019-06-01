package com.webvidhi.stock.account.service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

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
			//Set Account verfication token and expiry
			account.setVerificationExpiryTime(120);
			
			String verficationCode = UUID.randomUUID().toString();
			
			account.setVerificationCode(verficationCode);
			
			usrRepo.save(account);
			
			
			//Send a welcome email
			status = emailService.sendWelcomeMail(account);
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

			return encoder.matches(password, account.getPassword());
			
		}
		return false;
	}

	public boolean verifyAccount(String email, String code) {
		
		Account account = usrRepo.findByUserName(email);
		
		if (null != account ) {
			Calendar now = Calendar.getInstance();
			if (account.getVerificationExpiryTime().compareTo(now.getTime()) > 0 && account.getVerificationCode().contentEquals(code))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	

}
