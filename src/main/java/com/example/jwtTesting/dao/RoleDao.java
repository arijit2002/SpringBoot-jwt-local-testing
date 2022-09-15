package com.example.jwtTesting.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.jwtTesting.entity.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, String>{

}
