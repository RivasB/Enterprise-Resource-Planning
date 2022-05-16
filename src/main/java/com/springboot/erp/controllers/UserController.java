package com.springboot.erp.controllers;

import javax.validation.Valid;

import com.springboot.erp.entitys.User;
import com.springboot.erp.repositories.RoleRepository;
import com.springboot.erp.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/users")
    public String postUsers(@Valid @ModelAttribute("userObj") User user, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("userObj", user);
            model.addAttribute("modalShow","js/users/modal.js");
        }
        else {
            try {
                userService.createUser(user);
                model.addAttribute("userObj", new User());
            } catch (Exception e) {
                model.addAttribute("formErrorMessage",e.getMessage());
                model.addAttribute("userObj", user);
                model.addAttribute("modalShow","js/users/modal.js");
                model.addAttribute("userList", userService.getAllUsers());
                model.addAttribute("roles",roleRepository.findAll());
                model.addAttribute("userMenuClass","active");
            }
        }
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles",roleRepository.findAll());
        model.addAttribute("userMenuClass","active");
        return "users/users";
    }
}
