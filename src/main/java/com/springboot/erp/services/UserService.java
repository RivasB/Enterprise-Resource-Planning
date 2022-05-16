package com.springboot.erp.services;

import com.springboot.erp.entitys.User;

public interface UserService {
    
    public Iterable<User> getAllUsers();
}
