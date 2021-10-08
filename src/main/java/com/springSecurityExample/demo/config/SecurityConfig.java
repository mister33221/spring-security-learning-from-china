package com.springSecurityExample.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationFailureProviderNotFoundEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springSecurityExample.demo.handler.MyAuthenticationSuccessHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 提交表單
		http.formLogin()
				//自定義登入表單中username跟password inputname
//				.usernameParameter("username123")//對應表單的name
//				.passwordParameter("password123")
				// 自定義登入頁面
				.loginPage("/login.html")
				// 必須和表單提交的接口一樣，會去執行自定義的登入邏輯
				.loginProcessingUrl("/login")
				// 登入成功後跳轉的頁面 這是個頁面跳轉 本質上是一個get請求 但這邊我們只接受post請求 所以會跳405錯誤
				// .successForwardUrl("/main.html");
				// 既然如此我們就自己寫一個post請求
//				.successForwardUrl("/toMain")
				//直接跳使用successForwardUrl來跳轉指定網址會失敗 因為底下的方法是使用forward(?) 
				//所以我們另外寫了一個handler 把跳轉的方法改成sendRedirect
//				.successForwardUrl("https://www.google.com/") //會失敗
				//自定義登陸成功處理器
				.successHandler(new MyAuthenticationSuccessHandler("/main.html")) //會成功
				// 登入失敗跳轉 也必須是post
				.failureForwardUrl("/toError");//此頁面也會被攔截 所以也要設定放行
		// 授權
		http.authorizeRequests()
				// 指定放行的頁面
				.antMatchers("/error.html").permitAll()
				// 指定放行的頁面
				.antMatchers("/login.html").permitAll()
				// 所有的請求都必須認證才能訪問，必須登入
				.anyRequest().authenticated();// 此頁面也會被攔截 所以要放行他

		// 關閉防火牆
		http.csrf().disable();
	}

	// 自定義登入邏輯
	@Bean
	public PasswordEncoder getPw() {
		return new BCryptPasswordEncoder();
	}
}
