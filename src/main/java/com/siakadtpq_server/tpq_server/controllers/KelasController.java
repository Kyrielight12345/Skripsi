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

import com.siakadtpq_server.tpq_server.models.Kelas;
import com.siakadtpq_server.tpq_server.services.KelasService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/kelas")
public class KelasController {

    public KelasService kelasService;

    @GetMapping
    public List<Kelas> getAll() {
        return kelasService.getAll();
    }

    @GetMapping("/{id}")
    public Kelas getById(@PathVariable Integer id) {
        return kelasService.getById(id);
    }

    @PostMapping("/create")
    public Kelas create(@RequestBody Kelas kelas) {
        return kelasService.create(kelas);

    }

    @PutMapping("/{id}")
    public Kelas update(@PathVariable Integer id, @RequestBody Kelas kelas) {
        return kelasService.update(id, kelas);
    }

    @DeleteMapping("/{id}")
    public Kelas delete(@PathVariable Integer id) {
        return kelasService.delete(id);
    }

}
