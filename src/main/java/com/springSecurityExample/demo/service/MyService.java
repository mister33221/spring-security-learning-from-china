package com.springSecurityExample.demo.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

public interface MyService {

	
	boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
