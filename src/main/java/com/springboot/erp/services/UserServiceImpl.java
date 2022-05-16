package com.springboot.erp.services;

import com.springboot.erp.entitys.User;
import com.springboot.erp.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository repository;

    @Override
    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }
    
}
