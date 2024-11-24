package com.tpq.clinet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;

import com.tpq.clinet.services.JilidService;

@Controller
@AllArgsConstructor
@RequestMapping("/jilid")
public class JilidController {

    private JilidService jilidService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("jilid", jilidService.getAll());
        return "jilid/index";
    }

}
