package com.minakov.blog.controllers;

import com.minakov.blog.model.User;
import com.minakov.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());

        return "auth/registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User userForm,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Password don't match");
            return "auth/registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "User with such username is already exists");
            return "auth/registration";
        }

        return "redirect:/login";
    }
}