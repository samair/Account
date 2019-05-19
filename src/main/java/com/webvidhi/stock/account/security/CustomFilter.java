package com.webvidhi.stock.account.security;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class CustomFilter implements Filter {

	
	@Override
	public void init(FilterConfig filterConfig) {
		System.out.println("Init::called");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		HttpServletRequest req = (HttpServletRequest)request;
		
		System.out.println(req.getHeader("Authorization"));		
		System.out.println(req.getMethod());
		
		chain.doFilter(request, response);
		
	}

}
