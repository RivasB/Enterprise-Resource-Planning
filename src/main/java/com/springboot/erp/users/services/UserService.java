package com.springboot.erp.users.services;

import javax.validation.Valid;

import com.springboot.erp.users.entitys.User;

public interface UserService {
    
    public Iterable<User> getAll();

    public User create(@Valid User user) throws Exception;
    
    public User update(@Valid User user) throws Exception;

    public User getById(Long id) throws Exception;

    public void delete(Long id) throws Exception;
}
