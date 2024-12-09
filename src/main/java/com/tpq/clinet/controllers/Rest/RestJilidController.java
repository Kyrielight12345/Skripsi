package com.tpq.clinet.controllers.Rest;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tpq.clinet.models.Jilid;
import com.tpq.clinet.services.JilidService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/jilid")
public class RestJilidController {

    public JilidService jilidService;

    @GetMapping
    public List<Jilid> getAll() {
        return jilidService.getAll();
    }

    @GetMapping("/{id}")
    public Jilid getById(@PathVariable Integer id) {
        return jilidService.getById(id);
    }

    @PostMapping("/create")
    public Jilid create(@RequestBody Jilid jilid) {
        return jilidService.create(jilid);

    }

    @PutMapping("/{id}")
    public Jilid update(@PathVariable Integer id, @RequestBody Jilid jilid) {
        return jilidService.update(id, jilid);
    }

    @DeleteMapping("/{id}")
    public Jilid delete(@PathVariable Integer id) {
        return jilidService.delete(id);
    }

}