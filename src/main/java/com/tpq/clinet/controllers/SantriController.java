package com.tpq.clinet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import com.tpq.clinet.services.SantriService;
import com.tpq.clinet.models.Santri;
import org.springframework.security.core.Authentication;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/santri")
public class SantriController {

    private SantriService santriService;

    @GetMapping
    public String getAll(Model model, Authentication auth) {
        model.addAttribute("santri", santriService.getAll());
        model.addAttribute("role", auth.getAuthorities());
        return "santri/index";
    }

    @GetMapping("/create")
    public String createView(Santri santri, Model model) {
        return "santri/create";
    }
}
