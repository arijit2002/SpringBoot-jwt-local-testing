package com.example.jwtTesting.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jwtTesting.dao.UserDao;
import com.example.jwtTesting.entity.JwtRequest;
import com.example.jwtTesting.entity.JwtResponse;
import com.example.jwtTesting.entity.User;
import com.example.jwtTesting.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService{

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	// create a jwt token for a user and return the user details and the token
	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception{
		String userName = jwtRequest.getUserName();
		String userPassword = jwtRequest.getUserPassword();
		//System.out.print("i am here");
		authenticate(userName, userPassword);
		//System.out.print("i am here");
		final UserDetails userDetails = loadUserByUsername(userName);
		
		String newGeneratedToken = jwtUtil.generateToken(userDetails);
		
		User user = userDao.findById(userName).get();
		
		return new JwtResponse(user, newGeneratedToken);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findById(username).get();
		
		if(user != null) {
			return new org.springframework.security.core.userdetails.User(
					user.getUserName(),
					user.getUserPassword(),
					getAuthorities(user)
				);
		}else {
			throw new UsernameNotFoundException("User is not valid");
		}
	}
	
	// set user authorities (for validating using getAuthorities method)
	private Set<SimpleGrantedAuthority> getAuthorities(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
		user.getRole().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
		});
		return authorities;
	}
	
	// authenticate user with username and password 
	private void authenticate(String userName, String userPassword) throws Exception{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		}catch(DisabledException e) {
			throw new Exception("User is disabled");
		}catch(BadCredentialsException e) {
			throw new Exception("Bad Credentials from user");
		}
	}
}
