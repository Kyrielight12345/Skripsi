package com.tpq.clinet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import org.springframework.security.core.Authentication;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/laporan")
public class LaporanController {

    @GetMapping
    public String getAll(Model model, Authentication auth) {
        model.addAttribute("role", auth.getAuthorities());
        return "laporan/index";
    }

}
