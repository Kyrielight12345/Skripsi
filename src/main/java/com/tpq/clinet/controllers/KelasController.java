package com.tpq.clinet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import com.tpq.clinet.services.KelasService;

@Controller
@AllArgsConstructor
@RequestMapping("/kelas")
public class KelasController {

    private KelasService kelasService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("kelas", kelasService.getAll());
        return "kelas/index";
    }

}
