package com.springboot.erp.users.services;

import java.util.Optional;

import javax.validation.Valid;

import com.springboot.erp.users.entitys.User;
import com.springboot.erp.users.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public Iterable<User> getAll() {
        return repository.findAll();
    }

    private boolean checkUsername(User user) throws Exception {
        Optional<User> userFound = repository.findByUsername(user.getUsername());
        if (userFound.isPresent()) {
            throw new Exception("Usuario no disponible");
        }
        return true;
    }

    private boolean checkPassword(User user) throws Exception {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new Exception("Las contraseñas no coinciden");
        }
        if (user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) {
            throw new Exception("Confirmar contraseña no debe estar vacío");
        }
        return true;
    }

    @Override
    public User create(@Valid User user) throws Exception {
        if (checkPassword(user) && checkUsername(user)) {
            user = repository.save(user);
        }
        return user;
    }

    @Override
    public User update(@Valid User fromMap) throws Exception {
        User toMap = getById(fromMap.getId());
        map(fromMap, toMap);
        repository.save(toMap);
        return toMap;
    }

    protected void map(@Valid User fromMap, User toMap) {
        toMap.setName(fromMap.getName());
        toMap.setLastName(fromMap.getLastName());
        toMap.setUsername(fromMap.getUsername());
        toMap.setEmail(fromMap.getEmail());
        toMap.setRoles(fromMap.getRoles());
    }

    @Override
    public User getById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("El usuario no existe"));
    }

    @Override
    public void delete(Long id) throws Exception {
        User user = getById(id);
        repository.delete(user);        
    }

}
