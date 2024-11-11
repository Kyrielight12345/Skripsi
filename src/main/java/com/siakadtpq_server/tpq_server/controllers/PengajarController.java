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

import com.siakadtpq_server.tpq_server.models.Pengajar;
import com.siakadtpq_server.tpq_server.models.dto.request.Detail_PengajarRequest;
import com.siakadtpq_server.tpq_server.services.PengajarService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/pengajar")
public class PengajarController {

    public PengajarService pengajarService;

    @GetMapping
    public List<Pengajar> getAll() {
        return pengajarService.getAll();
    }

    @GetMapping("/{id}")
    public Pengajar getById(@PathVariable Integer id) {
        return pengajarService.getById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Pengajar> create(@RequestBody Detail_PengajarRequest detailUserRequest) {
        Pengajar createdPengajar = pengajarService.create(detailUserRequest);
        return ResponseEntity.ok(createdPengajar);
    }

    @PutMapping("/{id}")
    public Pengajar update(@PathVariable Integer id, @RequestBody Pengajar pengajar) {
        return pengajarService.update(id, pengajar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        pengajarService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
