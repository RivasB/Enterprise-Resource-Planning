package com.springboot.erp.services;

import javax.validation.Valid;

import com.springboot.erp.entitys.User;

public interface UserService {
    
    public Iterable<User> getAllUsers();

    public User createUser(@Valid User user) throws Exception;
}
