package com.siakadtpq_server.tpq_server.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siakadtpq_server.tpq_server.models.Santri;
import com.siakadtpq_server.tpq_server.models.dto.request.Detail_SantriRequest;
import com.siakadtpq_server.tpq_server.services.SantriService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/santri")
public class SantriController {

    public SantriService santriService;

    @GetMapping
    public List<Santri> getAll() {
        return santriService.getAll();
    }

    @GetMapping("/{id}")
    public Santri getById(@PathVariable Integer id) {
        return santriService.getById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Santri> create(@RequestBody Detail_SantriRequest detailUserRequest) {
        Santri createdSantri = santriService.create(detailUserRequest);
        return ResponseEntity.ok(createdSantri);
    }

    @PutMapping("/{id}")
    public Santri update(@PathVariable Integer id, @RequestBody Santri santri) {
        return santriService.update(id, santri);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        santriService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
