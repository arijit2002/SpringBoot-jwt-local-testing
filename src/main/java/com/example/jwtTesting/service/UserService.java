package com.example.jwtTesting.service;

import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwtTesting.dao.RoleDao;
import com.example.jwtTesting.dao.UserDao;
import com.example.jwtTesting.entity.Role;
import com.example.jwtTesting.entity.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User registerNewUser(User user) {
		Role role = roleDao.findById("User").get();
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRole(roles);
		user.setUserPassword(getEncodedPassword(user.getUserPassword()));
		return userDao.save(user);
	}
	
	public String findUser(String token,String name) {
		String[] divs = token.split("\\.");
		String payload = divs[1];String codes="\"";
		byte[] decodedBytes = Base64.getDecoder().decode(payload);
		String decodedString = new String(decodedBytes);
		String test=decodedString.substring(8);
		//System.out.println(test);
		String validUser = test.substring(0,test.indexOf(codes));
		if(validUser.equals(name)) {
			return "Yes, Valid User";
		}else {
			return "Not allowed, Invalid User";
		}
	}
	
	public void initRolesAndUser() {
		Role adminRole = new Role();
		adminRole.setRoleName("Admin");
		adminRole.setRoleDescription("Highest");
		roleDao.save(adminRole);
		
		Role userRole = new Role();
		userRole.setRoleName("User");
		userRole.setRoleDescription("Lowest");
		roleDao.save(userRole);
		
		User adminUser = new User();
		adminUser.setUserName("Arijit");
		adminUser.setUserPassword(getEncodedPassword("arijit@123"));
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRole(adminRoles);
		userDao.save(adminUser);
		
//		User user = new User();
//		user.setUserName("Das");
//		user.setUserPassword(getEncodedPassword("das@123"));
//		Set<Role> userRoles = new HashSet<>();
//		userRoles.add(userRole);
//		user.setRole(userRoles);
//		userDao.save(user);
	}
	
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
