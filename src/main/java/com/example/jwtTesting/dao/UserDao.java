package com.example.jwtTesting.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.jwtTesting.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, String>{

}
