package com.tpq.clinet.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.tpq.clinet.models.Pengajar;
import com.tpq.clinet.services.PengajarService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/pengajar")
public class PengajarController {

    private PengajarService pengajarService;

    @GetMapping
    public String getAll(Model model, Authentication auth) {
        model.addAttribute("pengajar", pengajarService.getAll());
        model.addAttribute("role", auth.getAuthorities());
        return "pengajar/index";
    }

    @GetMapping("/detail")
    public String detail(Pengajar pengajar, Model model) {
        return "pengajar/detail";
    }

    @GetMapping("/create")
    public String createView(Pengajar pengajar, Model model) {
        return "pengajar/create";
    }

    @GetMapping("/update")
    public String update(Pengajar pengajar, Model model) {
        return "pengajar/update";
    }
}
