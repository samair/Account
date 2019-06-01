package com.webvidhi.stock.account.service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Component
public class OTPService {

	private static final long EXPIRE_MINS = 5;
	
	private LoadingCache<String, Integer> otpCache;
	
	public int generateOTP(String key) {
		
		Random randm = new Random();
		int otp = 10000 + randm.nextInt(90000);
		otpCache.put(key, otp);
        return otp;
	}
	
	public OTPService()
	{
		otpCache = CacheBuilder.newBuilder().
			     expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
			      public Integer load(String key) {
			             return 0;
			       }
			   });
	} 	
	
	public int getOTP(String key) {
		
		try {
			return otpCache.get(key);
		} catch (Exception e) {

			return 0;
		}
		
		
	}

	public boolean validateOTP(String emailId, Integer otpValue) {

		
		try {
			Integer otp = otpCache.get(emailId);
			if (otpValue.equals(otp)) {
				return true;
			}
		} catch (ExecutionException e) {
	
			e.printStackTrace();
		}

		return false;
	}

}
