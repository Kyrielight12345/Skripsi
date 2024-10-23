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

import com.siakadtpq_server.tpq_server.models.Nilai;
import com.siakadtpq_server.tpq_server.services.NilaiService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/nilai")
public class NilaiController {

    public NilaiService nilaiService;

    @GetMapping
    public List<Nilai> getAll() {
        return nilaiService.getAll();
    }

    @GetMapping("/{id}")
    public Nilai getById(@PathVariable Integer id) {
        return nilaiService.getById(id);
    }

    @PostMapping("/create")
    public Nilai create(@RequestBody Nilai nilai) {
        return nilaiService.create(nilai);

    }

    @PutMapping("/{id}")
    public Nilai update(@PathVariable Integer id, @RequestBody Nilai nilai) {
        return nilaiService.update(id, nilai);
    }

    @DeleteMapping("/{id}")
    public Nilai delete(@PathVariable Integer id) {
        return nilaiService.delete(id);
    }

}
