package com.tpq.clinet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import com.tpq.clinet.services.UserService;

import org.springframework.security.core.Authentication;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @GetMapping
    public String getAll(Model model, Authentication auth) {
        model.addAttribute("user", userService.getAll());
        model.addAttribute("role", auth.getAuthorities());
        return "user/index";
    }

}
