package com.tpq.clinet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.tpq.clinet.models.Nilai;
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

    @GetMapping("/detail-ortu")
    public String detailPageortu(Model model, Authentication auth) {
        model.addAttribute("role", auth.getAuthorities());
        return "nilai/detail-ortu";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("nilai", nilaiService.getAll());
        return "nilai/create";
    }

    @GetMapping("/update")
    public String detail(Nilai nilai, Model model) {
        return "nilai/update";
    }
}
