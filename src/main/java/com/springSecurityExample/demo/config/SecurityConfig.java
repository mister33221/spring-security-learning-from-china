package com.springSecurityExample.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.event.AuthenticationFailureProviderNotFoundEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.springSecurityExample.demo.handler.MyAccessDeniedHandler;
import com.springSecurityExample.demo.handler.MyAuthenticationFailureHandler;
import com.springSecurityExample.demo.handler.MyAuthenticationSuccessHandler;
import com.springSecurityExample.demo.service.MyService;
import com.springSecurityExample.demo.service.UserDetailServiceImpl;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired 
	private MyAccessDeniedHandler accessDeniedHandler;
	
	//P025 rememberMe 
	@Autowired
	private DataSource dataSource;
	
	//P025 rememberMe 
	@Autowired
	private PersistentTokenRepository persistentTokenRepository;
	
	//P025 rememberMe 
	@Autowired
	private UserDetailServiceImpl userDetailService;
	

	
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
				.successForwardUrl("/toMain")
				//直接跳使用successForwardUrl來跳轉指定網址會失敗 因為底下的方法是使用forward(?) 
				//所以我們另外寫了一個handler 把跳轉的方法改成sendRedirect
//				.successForwardUrl("https://www.google.com/") //會失敗
				//自定義登陸成功處理器
//				.successHandler(new MyAuthenticationSuccessHandler("/main.html")) //會成功
				// 登入失敗跳轉 也必須是post
				.failureForwardUrl("/toError");//此頁面也會被攔截 所以也要設定放行
				//P011
				//直接跳使用failureForwardUrl來跳轉指定網址會失敗 因為底下的方法是使用forward(?) 
				//所以我們另外寫了一個handler 把跳轉的方法改成sendRedirect
//				.failureHandler(new MyAuthenticationFailureHandler("/error.html"));
		
		// 授權
		http.authorizeRequests()
				//P012 這邊授權放行是有前後順序性的 如果把所有請求都必須認證才能放行的程式碼擺在最前面，那麼後面兩條可放行的網址就無法被執行
//				.anyRequest().authenticated().
				// 指定放行的頁面
//				.antMatchers("/login.html").permitAll()
				//P020底下上行 與上面是相同意思 可以進入access方法底層去看  是一樣的
				.antMatchers("/login.html").access("permitAll")
				.antMatchers("/error.html").permitAll()
				.antMatchers("/logout").permitAll()
				// 指定放行的頁面
				//P013 antMatcher() 參數是不定向參數，每個參數都是一個ant表示式，用於配對URL規則，規則如下
				//也可以如底下regex那樣指定HttpMethod
				//?:配對一個字符，*:配對0個或多個字符，**:配對0個或多個目錄
				//例如我要放行我的靜態資料 如resoures底下的css images js 等等
//				.antMatchers("/css/**","/js/**","/images/**").permitAll()
//				.antMatchers("/**/*.png").permitAll() //這樣所有後綴飾png的檔案都會被放行
				
				//P014 後綴放行.jpg, 正則表式示
//				.regexMatchers(".+[.]jpg").permitAll()
//				.regexMatchers("/demo").permitAll()
				//指定請求方法式post
//				.regexMatchers(HttpMethod.POST, "/demo").permitAll()
				
				//P015 其實我們比較不常使用 regexMatcher 而是比較常用mvcMatchers 但最常用的還是antMatchers 後面可以使用servletPath來指定預設的網址
//				.mvcMatchers("/demo").servletPath("/servletpath").permitAll()
				
				//P017基於權限控制，hasAuthority嚴格區分大小寫
				//這條為描述如果有某"1個"權限
//				.antMatchers("/main1.html").hasAuthority("Admin")
				//這條為描述"多個"權限 只要有其中一個符合即可訪問
//				.antMatchers("/main1.html").hasAnyAuthority("Admin","admin")
				
				//P018 基於角色控制 而角色有命名規則 也是嚴格區分大小寫 必須為"ROLE_XXX" 而在這邊的hasRole終究只要寫"XXX"的部分即可
				//這條描述如果登入者式某一個角色的話 就可以進入
//				.antMatchers("/main1.html").hasRole("role2")
				//P020 這條與上面是一樣的 可以到access方法的底層看就會發現 權限相關的基本上都是基於access 都可以這樣操作
//				.antMatchers("/main1.html").access("hasRole('role1')")
				//這條描述多種角色是允許進入 只要符合其中一個可以進入訪問
//				.antMatchers("/main1.html").hasAnyRole("role1","role2")
				
				//P19 基於IP控制
//				.antMatchers("/main1.html").hasIpAddress("127.0.0.1")
				
				
				// 所有的請求都必須認證才能訪問，必須登入。因有順序姓，故必須放在最後(P012)
				.anyRequest().authenticated();// 此頁面也會被攔截 所以要放行他
//				P022取代上面這行 自定義access方法  //這行我看不懂
//				.anyRequest().access("@myserviceImpl.hasPermission(request,authentication)");
		
		//P020 異常處理
		http.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler);
		
		//P025 rememberMe 使用mybatis 連結mysql
		http.rememberMe()
			.tokenRepository(persistentTokenRepository)
//			.rememberMeParameter()
			//超時時間
			.tokenValiditySeconds(60)
			//自定義登入邏輯
			.userDetailsService(userDetailService);
			
		// 關閉防火牆
		http.csrf().disable();
	}

	// 自定義登入邏輯
	@Bean
	public PasswordEncoder getPw() {
		return new BCryptPasswordEncoder();
	}
	
	//P025會用到的bean
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		//設置資料庫
		jdbcTokenRepository.setDataSource(dataSource);
		//自動建表，第一次啟動時開啟，第二次啟動時註釋掉
//		jdbcTokenRepository.setCreateTableOnStartup(true);
		return jdbcTokenRepository;
	}
	
}
