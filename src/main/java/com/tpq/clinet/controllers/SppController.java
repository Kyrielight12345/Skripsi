package com.tpq.clinet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.tpq.clinet.models.Spp;
import com.tpq.clinet.services.SppService;

import org.springframework.security.core.Authentication;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/spp")
public class SppController {
    private SppService sppService;

    @GetMapping
    public String getAll(Model model, Authentication auth) {
        model.addAttribute("progress", sppService.getAllGroupedBySantri());
        model.addAttribute("role", auth.getAuthorities());
        return "spp/index";
    }

    @GetMapping("/detail")
    public String detailPage(Model model, Authentication auth) {
        model.addAttribute("role", auth.getAuthorities());
        return "spp/detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("progress", sppService.getAll());
        return "spp/create";
    }

    @GetMapping("/update")
    public String detail(Spp spp, Model model) {
        return "spp/update";
    }
}
