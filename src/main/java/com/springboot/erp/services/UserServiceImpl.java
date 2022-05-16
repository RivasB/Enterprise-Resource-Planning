package com.springboot.erp.services;

import java.util.Optional;

import javax.validation.Valid;

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

    private boolean checkUsernameNotExist(User user) throws Exception{
        Optional<User> userFound = repository.findByUsername(user.getUsername());
        if (userFound.isPresent()) {
            throw new Exception("Usuario no disponible");
        }
        return true;
    }

    private boolean checkPasswordMatch(User user) throws Exception{
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new Exception("Las contraseñas no coinciden");
        }
        return true;
    }

    @Override
    public User createUser(@Valid User user) throws Exception {
        if (checkPasswordMatch(user) && checkUsernameNotExist(user)) {
            user = repository.save(user);
        }
        return user;
    }
    
}
