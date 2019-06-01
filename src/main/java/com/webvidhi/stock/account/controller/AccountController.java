package com.webvidhi.stock.account.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webvidhi.stock.account.model.Account;
import com.webvidhi.stock.account.model.Status;
import com.webvidhi.stock.account.model.User;
import com.webvidhi.stock.account.service.AccountService;
import com.webvidhi.stock.account.service.EmailService;
import com.webvidhi.stock.account.service.OTPService;

import io.swagger.annotations.Api;
@CrossOrigin
@RestController
@RequestMapping("/acc/")
@Api(value="Account Controller")
public class AccountController {
	
	@Autowired
	private AccountService acService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private OTPService otpService;
	
	@GetMapping("/accounts")
	public List<Account> hello() {
		return acService.getAllAccounts();

	}
	@GetMapping("/account/{emailId}")
	public Account getAccountByEmail(@PathVariable String emailId) {
		return acService.getUserByEmailId(emailId);

	}
	@PostMapping("/account")
	public Status createAccount(@RequestBody Account account){
		
		//Generate
		if (acService.createOrUpdate(account)) {
			return new Status(true);
		}
		else {
			return new Status(false);
		}
		
		
	}
	@PostMapping("/login")
	public boolean login(@RequestBody User user) {
		
		return acService.validate(user.getUserName(), user.getPassword());
		
	}
	@PostMapping("/otp")
	public boolean otp(@RequestBody User user) {
		
		return emailService.sendOTPMail(user.getUserName());
		
	}
	
	@PostMapping("/validateOTP")	
	public boolean validateOTP(@PathParam(value = "email") String email,  @PathParam(value = "otp") Integer otp){
		return otpService.validateOTP(email, otp);
	}

}
