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

import com.siakadtpq_server.tpq_server.models.Progress;
import com.siakadtpq_server.tpq_server.services.ProgressService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/progress")
public class ProgressController {

    public ProgressService progressService;

    @GetMapping
    public List<Progress> getAll() {
        return progressService.getAll();
    }

    @GetMapping("/{id}")
    public Progress getById(@PathVariable Integer id) {
        return progressService.getById(id);
    }

    @PostMapping("/create")
    public Progress create(@RequestBody Progress progress) {
        return progressService.create(progress);

    }

    @PutMapping("/{id}")
    public Progress update(@PathVariable Integer id, @RequestBody Progress progress) {
        return progressService.update(id, progress);
    }

    @DeleteMapping("/{id}")
    public Progress delete(@PathVariable Integer id) {
        return progressService.delete(id);
    }

}
