package com.example.jwtTesting.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtTesting.entity.JwtRequest;
import com.example.jwtTesting.entity.JwtResponse;
import com.example.jwtTesting.service.CookieService;
import com.example.jwtTesting.service.IPService;
import com.example.jwtTesting.service.JwtService;

@RestController
@CrossOrigin
public class JwtController {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private IPService ipService;
	
	@Autowired
	private CookieService cookieService;
	
	@PostMapping({"/authenticate"})
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest, HttpServletRequest request) throws Exception{
		System.out.println(request.getRemoteAddr());
		return jwtService.createJwtToken(jwtRequest);
	}
	
	@GetMapping({"/test"})
	public void test(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		//StringBuffer url=request.getRequestURL();
		//String uri=request.getRequestURI();
		cookieService.createCookie(response, "my_key", "arijit");
		String ip=ipService.ipTrace(request);
	    System.out.println(ip);
	}
}
