package com.springSecurityExample.demo.service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class MyserviceImpl implements MyService {

	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

		//P022 獲取主體
		Object obj = authentication.getPrincipal();
		//判斷主體是否煮魚userDetails
		if (obj instanceof UserDetails) {
			//獲取權限
			UserDetails userDetails = (UserDetails)obj;
			Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
			//判斷請求的URI是否在權限裡
			return authorities.contains(new SimpleGrantedAuthority(request.getRequestURI()));
		}
		
		return false;
	}

}
