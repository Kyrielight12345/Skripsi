package com.siakadtpq_server.tpq_server.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siakadtpq_server.tpq_server.models.Nilai;
import com.siakadtpq_server.tpq_server.models.Santri;
import com.siakadtpq_server.tpq_server.services.NilaiService;
import com.siakadtpq_server.tpq_server.services.SantriService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/nilai")
public class NilaiController {

    public NilaiService nilaiService;
    public SantriService santriService;

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

    @GetMapping("/santri/{santriId}")
    public List<Nilai> getSppBySantri(@PathVariable Integer santriId) {
        Santri santri = santriService.getById(santriId);
        return nilaiService.getBySantri(santri);
    }

    @GetMapping("/santri")
    public ResponseEntity<Map<Integer, List<Nilai>>> getAllGroupedBySantri() {
        Map<Integer, List<Nilai>> groupedProgress = nilaiService.getAllGroupedBySantri();
        return ResponseEntity.ok(groupedProgress);
    }
}
