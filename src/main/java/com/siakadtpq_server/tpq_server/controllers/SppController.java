package com.siakadtpq_server.tpq_server.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siakadtpq_server.tpq_server.models.Spp;
import com.siakadtpq_server.tpq_server.services.SppService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/spp")
public class SppController {

    public SppService sppService;

    @GetMapping
    public List<Spp> getAll() {
        return sppService.getAll();
    }

    @GetMapping("/{id}")
    public Spp getById(@PathVariable Integer id) {
        return sppService.getById(id);
    }

    @PostMapping("/create")
    public Spp create(@RequestBody Spp spp) {
        return sppService.create(spp);

    }

    @PutMapping("/{id}")
    public Spp update(@PathVariable Integer id, @RequestBody Spp spp) {
        return sppService.update(id, spp);
    }

    @DeleteMapping("/{id}")
    public Spp delete(@PathVariable Integer id) {
        return sppService.delete(id);
    }

}