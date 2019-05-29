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

@Service
public class EmailService {
	
	@Autowired
	private OTPGenerator otpGenerator;
	
	public static void main(String args[]) {
		EmailService service = new EmailService();

	    service.sendMail("tsameerc@gmail.com");

	}
	public boolean sendMail(String emailId) {
		
		Email from = new Email("no-reply@stocks.com");
	    String subject = "OTP for stock account verification";
	    Email to = new Email("emailId");
	    Content content = new Content("text/plain", "OTP : "+otpGenerator.getOTP());
	    Mail mail = new Mail(from, subject, to, content);

	    SendGrid sg = new SendGrid("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");
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

}