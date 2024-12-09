package com.tpq.clinet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.tpq.clinet.services.NilaiService;

import org.springframework.security.core.Authentication;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/nilai")
public class NilaiController {
    private NilaiService nilaiService;

    @GetMapping
    public String getAll(Model model, Authentication auth) {
        model.addAttribute("progress", nilaiService.getAllGroupedBySantri());
        model.addAttribute("role", auth.getAuthorities());
        return "nilai/index";
    }

    @GetMapping("/detail")
    public String detailPage(Model model, Authentication auth) {
        model.addAttribute("role", auth.getAuthorities());
        return "nilai/detail";
    }
}