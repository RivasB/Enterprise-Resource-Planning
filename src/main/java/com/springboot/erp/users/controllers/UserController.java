package com.springboot.erp.users.controllers;

import javax.validation.Valid;

import com.springboot.erp.users.entitys.User;
import com.springboot.erp.users.repositories.RoleRepository;
import com.springboot.erp.users.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/users")
    public String getAll(Model model) {
        model.addAttribute("userObj", new User());
        model.addAttribute("userList", userService.getAll());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("userMenuClass", "active");
        return "users/users";
    }

    @GetMapping("/users/form")
    public String form(Model model) {
        model.addAttribute("userObj", new User());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("form", "true");
        model.addAttribute("userMenuClass", "active");
        return "users/users";
    }

    @PostMapping("/users/form/action/{action}")
    public String create(@Valid @ModelAttribute("userObj") User user, BindingResult result, ModelMap model, @PathVariable(name = "action") String action) {
        if (result.hasErrors()) {
            model.addAttribute("userObj", user);
            model.addAttribute("form", "true");
        } else {
            try {
                if (action.equals("created")) {
                    userService.create(user);
                } else if (action.equals("updated")){
                    userService.update(user);
                } else {
                    return "redirect:/users";
                }
                model.addAttribute("userObj", new User());
                model.addAttribute("userList", userService.getAll());
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("userObj", user);
                model.addAttribute("form", "true");

            }
        }
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("userMenuClass", "active");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String edit(Model model, @PathVariable(name = "id") Long id) throws Exception {
        User userToEdit = userService.getById(id);
        model.addAttribute("userObj", userToEdit);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("userMenuClass", "active");
        model.addAttribute("editMode", "true");
        model.addAttribute("form", "true");
        return "users/users";
    }

    @GetMapping("/users/delete/{id}")
    public String delete(Model model, @PathVariable(name = "id") Long id) throws Exception {
        try {
            userService.delete(id);
        } catch (Exception e) {
            model.addAttribute("listErrorMessage", e.getMessage());
        }
        return getAll(model);
    }
}
