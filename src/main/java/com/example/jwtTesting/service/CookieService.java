package com.example.jwtTesting.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public class CookieService {
	
	public void createCookie(HttpServletResponse response,String key,String value) 
			throws ServletException, IOException{
		
		Cookie prefer = new Cookie(key,value);
		response.addCookie(prefer);
	}
}
