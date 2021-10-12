package com.springSecurityExample.demo.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

//記得加component
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler{

	
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		//P020響應狀態
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		//返回json格式
		response.setHeader("content-Type", "application/json;charset=utf-8");
		PrintWriter writerr = response.getWriter();
		writerr.write("{\"status\":\"error\", \"message\":\"權限不足\"}");
		writerr.flush();
		writerr.close();
	}

}
