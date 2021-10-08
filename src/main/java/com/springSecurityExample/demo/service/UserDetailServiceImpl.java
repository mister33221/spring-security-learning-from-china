package com.springSecurityExample.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//自定義登入邏輯
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//1.根據用戶名去數據庫查詢，如果不存在就拋異常
		if (!"admin".equals(username)) {
			throw new UsernameNotFoundException("用戶名不存在");
		}
		//2.比較密碼(存入資料庫的通常是已經加密的 所以比較加密級的就可以了)，如果匹配成功返回UserDetails
		String password = passwordEncoder.encode("123");
		return new User(username, password, 
				AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal"));

	}

}
