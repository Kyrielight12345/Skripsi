package com.tpq.clinet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.tpq.clinet.models.Progress;
import com.tpq.clinet.services.ProgressService;

import org.springframework.security.core.Authentication;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/progress")
public class ProgressController {
    private ProgressService progressService;

    @GetMapping
    public String getAll(Model model, Authentication auth) {
        model.addAttribute("progress", progressService.getAllGroupedBySantri());
        model.addAttribute("role", auth.getAuthorities());
        return "progress/index";
    }

    @GetMapping("/detail")
    public String detailPage(Model model, Authentication auth) {
        model.addAttribute("role", auth.getAuthorities());
        return "progress/detail";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("progress", progressService.getAll());
        return "progress/create";
    }

    @GetMapping("/update")
    public String detail(Progress progress, Model model) {
        return "progress/update";
    }
}
