package com.example.jwtTesting.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtTesting.entity.User;
import com.example.jwtTesting.service.JwtService;
import com.example.jwtTesting.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
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
		return "For Admin Only";
	}
	
	@GetMapping({"/forUser"})
	@PreAuthorize("hasRole('User')")
	public String forUser() {
		return "For Users Only";
	}
	
	@GetMapping({"/forUserAdmin"})
	@PreAuthorize("hasAnyRole('Admin','User')")
	public String forUSerAdmin() {
		return "For User and Admin Both";
	}
	
	@GetMapping({"/getUserDetails"})
	@PreAuthorize("hasAnyRole('Admin','User')")
	public UserDetails getUserDetails(@RequestParam String name) {
		return jwtService.loadUserByUsername(name);
	}
	
	@GetMapping({"/onlySpecificUser"})
	@PreAuthorize("hasAnyRole('User')")
	public String onlySpecificUser(@RequestHeader(value = "Authorization") String authHeader, @RequestParam String name) {
		String token = authHeader.substring(7);
		return userService.validateUser(token,name);
	}
	
}
