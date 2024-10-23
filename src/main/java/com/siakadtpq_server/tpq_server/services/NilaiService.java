package com.siakadtpq_server.tpq_server.services;

import lombok.AllArgsConstructor;

import com.siakadtpq_server.tpq_server.models.Nilai;
import com.siakadtpq_server.tpq_server.repositories.NilaiRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class NilaiService {

    private NilaiRepository nilaiRepository;

    public List<Nilai> getAll() {
        return nilaiRepository.findAll();
    }

    public Nilai getById(Integer id) {
        return nilaiRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nilai not found!!!"));
    }

    public Nilai create(Nilai nilai) {
        return nilaiRepository.save(nilai);
    }

    public Nilai update(Integer id, Nilai nilai) {
        getById(id);
        nilai.setId(id);
        return nilaiRepository.save(nilai);
    }

    public Nilai delete(Integer id) {
        Nilai nilai = getById(id);
        nilaiRepository.delete(nilai);
        return nilai;
    }
}