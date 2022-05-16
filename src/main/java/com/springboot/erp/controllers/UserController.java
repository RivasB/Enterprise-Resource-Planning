package com.springboot.erp.controllers;

import com.springboot.erp.entitys.User;
import com.springboot.erp.repositories.RoleRepository;
import com.springboot.erp.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    
    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("userObj", new User());
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles",roleRepository.findAll());
        model.addAttribute("userMenuClass","active");
        return "users/users";
    }
}
