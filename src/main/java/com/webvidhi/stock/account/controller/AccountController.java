package com.webvidhi.stock.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/acc/")
@Api(value="Account Controller")
public class AccountController {
	
	@Autowired
	private AccountService acService;
	
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
	

}
