package com.springSecurityExample.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {


	//用不到了
//	@RequestMapping("/login")
//	public String login() {
//		return "redirect:main.html";
//	}
	
	@RequestMapping("/toMain")
	public String toMain() {
		return "redirect:main.html";
	}
	
	@RequestMapping("/toError")
	public String toError() {
		return "redirect:error.html";
	}
	
}
