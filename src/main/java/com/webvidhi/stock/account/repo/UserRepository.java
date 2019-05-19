package com.webvidhi.stock.account.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.webvidhi.stock.account.model.Account;

public interface UserRepository extends MongoRepository<Account, String>{
	
	@Query("{ 'username' : ?0 }")
    Account findByUserName(String userName);
	
	

}
