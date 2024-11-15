package com.tpq.clinet.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tpq.clinet.services.AuthService;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public ModelAndView home() {
        // Retrieve the authentication information from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the role from the authentication
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                .orElse("USER"); // Default to USER if no role found

        // Create a map to hold the response
        Map<String, Object> model = new HashMap<>();
        model.put("role", role); // Adding role to the model

        // Return the "dashboard" view and pass the role to the view
        return new ModelAndView("dashboard", model);
    }
}
