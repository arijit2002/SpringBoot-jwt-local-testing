package com.example.jwtTesting.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class User {
	
	@Id
	private String userName;
	private String userPassword;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="User_Role",
		joinColumns = {
				@JoinColumn(name="User_Id")
		},
		inverseJoinColumns = {
			@JoinColumn(name="Role_Id")
		}
	)
	private Set<Role> role;

	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String userName, String userPassword, Set<Role> role) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}
	
	
}
