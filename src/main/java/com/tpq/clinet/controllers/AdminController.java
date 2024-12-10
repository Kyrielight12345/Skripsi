package com.tpq.clinet.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String dashboard(Model model, Authentication auth) {
        model.addAttribute("name", auth.getName());
        model.addAttribute("isActive", "home");
        return "admin/index";
    }
}
