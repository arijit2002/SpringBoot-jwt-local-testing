package com.example.jwtTesting.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtTesting.entity.JwtRequest;
import com.example.jwtTesting.entity.JwtResponse;
import com.example.jwtTesting.service.IPService;
import com.example.jwtTesting.service.JwtService;

@RestController
@CrossOrigin
public class JwtController {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private IPService ipService;
	
	@PostMapping({"/authenticate"})
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest, HttpServletRequest request) throws Exception{
		System.out.println(request.getRemoteAddr());
		return jwtService.createJwtToken(jwtRequest);
	}
	
	@GetMapping({"/test"})
	public void test(HttpServletRequest request) {
		
		//StringBuffer url=request.getRequestURL();
		//String uri=request.getRequestURI();
		
		String ip=ipService.ipTrace(request);
	    System.out.println(ip);
	}
}
