package com.springboot.erp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/users")
    public String index() {
        return "users/users";
    }
}