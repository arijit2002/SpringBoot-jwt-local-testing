package com.example.jwtTesting.controller;

import java.net.InetSocketAddress;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtTesting.entity.User;
import com.example.jwtTesting.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void initRolesAndUsers() {
		userService.initRolesAndUser();
	}
	
	@PostMapping({"/registerNewUser"})
	public User registerNewUser(@RequestBody User user) {
		return userService.registerNewUser(user);
	}
	
	@GetMapping({"/forAdmin"})
	@PreAuthorize("hasRole('Admin')")
	public String forAdmin() {
		return "Only for admin ";
	}
	
	@GetMapping({"/forUser"})
	@PreAuthorize("hasRole('User')")
	public String forUser(@RequestHeader(value = "Authorization") String authHeader) {
		String token = authHeader.substring(7);
		String[] divs = token.split("\\.");
		String payload = divs[1];
		byte[] decodedBytes = Base64.getDecoder().decode(payload);
		String decodedString = new String(decodedBytes);
		System.out.println(decodedString);
		return "Only for users \n";
	}
	
	@GetMapping({"/forUserAdmin"})
	@PreAuthorize("hasAnyRole('Admin','User')")
	public String forUSerAdmin() {
		return "For User and Admin Both";
	}
}
