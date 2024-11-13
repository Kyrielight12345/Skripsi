package com.tpq.clinet.controllers.Rest;

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

import com.tpq.clinet.models.Santri;
import com.tpq.clinet.models.dto.request.Detail_SantriRequest;
import com.tpq.clinet.services.SantriService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/Rest/santri")
public class RestSantriController {

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
