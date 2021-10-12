package com.springSecurityExample.demo.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	
	private String url;
	
	public MyAuthenticationSuccessHandler(String url) {
		this.url = url;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		//取得IP位置
		System.out.println(request.getRemoteAddr());
		
		User user = (User)authentication.getPrincipal();
		System.out.println(user.getUsername());
		//基於安全會輸出null
		System.out.println(user.getPassword());
		System.out.println(user.getAuthorities());
		
		response.sendRedirect(url);
		
	}

	
	
}
