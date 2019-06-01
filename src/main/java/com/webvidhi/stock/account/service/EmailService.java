package com.webvidhi.stock.account.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.webvidhi.stock.account.model.Account;

@Service
public class EmailService {
	
	@Autowired
	private OTPService otpGenerator;
	
	public static void main(String args[]) {
		EmailService service = new EmailService();

	    service.sendOTPMail("tsameerc@gmail.com");

	}
	
	public boolean sendOTPMail(String emailId) {
		
		Email from = new Email("no-reply@stocks.com");
	    String subject = "OTP for stock account verification";
	    Email to = new Email(emailId);
	    Content content = new Content("text/plain", "OTP : "+otpGenerator.generateOTP(emailId));
	    Mail mail = new Mail(from,subject,to,content);
	   // mail.setTemplateId("d-00592446f39243168393c3338a94bce5");
	    

	    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	    } catch (IOException ex) {
	      return false;
	    }
	    return true;
	}

	public boolean sendWelcomeMail(Account account) {
		
		Email from = new Email("no-reply@stocks.com");
	    String subject = "OTP for stock account verification";
	    Email to = new Email(account.getUsername());
	    Content content = new Content("text/plain", "OTP : "+otpGenerator.generateOTP(account.getUsername()));
	    Mail mail = new Mail(from,subject,to,content);
	    mail.setTemplateId("d-00592446f39243168393c3338a94bce5");
	    
	    Personalization personalization = new Personalization();
	    personalization.addDynamicTemplateData("name", account.getFirstName());
	    
        personalization.addDynamicTemplateData("link", "https://frozen-shelf-75821.herokuapp.com/acc/verifyAccount?email="
	    +account.getUsername()+"&code="+account.getVerificationCode());
        
        personalization.addTo(new Email(account.getUsername()));
	    mail.addPersonalization(personalization);
	    
	    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	    } catch (IOException ex) {
	      System.out.println(ex.getMessage());
	      return false;
	    }
	    return true;
	}

}
