package com.springSecurityExample.demo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {


	//用不到了
//	@RequestMapping("/login")
//	public String login() {
//		return "redirect:main.html";
//	}
	//P023 @secured 要再啟動程式那邊加上@EnableGlobalMethodSecurity(securedEnabled = true) 來啟動了註解 
//	裡面可以寫上角色 如同haseRole
//	@Secured("ROLE_role1")
	//P024 同上 @PreAuthorize允許以ROLE_開頭 也允許不用ROLE_開頭 但配置類不允許以ROLE_開頭
	@PreAuthorize("hasRole('role1')")
	@RequestMapping("/toMain")
	public String toMain() {
		return "redirect:main.html";
	}
	
	@RequestMapping("/toError")
	public String toError() {
		return "redirect:error.html";
	}
	
	/**
	 * 頁麵跳轉
	 * @return
	 */
	@GetMapping("/demo")
	@ResponseBody
	public String demo() {
		return "demo";
	}
	
}
