package com.siakadtpq_server.tpq_server.services;

import lombok.AllArgsConstructor;

import com.siakadtpq_server.tpq_server.models.Spp;
import com.siakadtpq_server.tpq_server.repositories.SppRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class SppService {

    private SppRepository sppRepository;

    public List<Spp> getAll() {
        return sppRepository.findAll();
    }

    public Spp getById(Integer id) {
        return sppRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Spp not found!!!"));
    }

    public Spp create(Spp spp) {
        return sppRepository.save(spp);
    }

    public Spp update(Integer id, Spp spp) {
        getById(id);
        spp.setId(id);
        return sppRepository.save(spp);
    }

    public Spp delete(Integer id) {
        Spp spp = getById(id);
        sppRepository.delete(spp);
        return spp;
    }
}