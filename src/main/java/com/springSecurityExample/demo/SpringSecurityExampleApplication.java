package com.springSecurityExample.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
//P023 P024
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //啟動了註解 然後去config裡面把自己寫的訪問控制都關帝凹不然會衝突
public class SpringSecurityExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityExampleApplication.class, args);
	}

}
