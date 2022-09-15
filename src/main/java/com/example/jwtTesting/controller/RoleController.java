package com.example.jwtTesting.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtTesting.entity.Role;

@RestController
public class RoleController {
	
	@PostMapping({"/createNewRole"})
	public Role createNewRole(Role role) {
		
	}
}
