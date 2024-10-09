package com.siakadtpq_server.tpq_server.services;

import lombok.AllArgsConstructor;

import com.siakadtpq_server.tpq_server.models.Kelas;
import com.siakadtpq_server.tpq_server.repositories.KelasRepository;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class KelasService {

    private KelasRepository kelasRepository;

    public List<Kelas> getAll() {
        return kelasRepository.findAll();
    }

    public Kelas getById(Integer id) {
        return kelasRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kelas not found!!!"));
    }

    public Kelas create(Kelas kelas) {
        return kelasRepository.save(kelas);
    }

    public Kelas update(Integer id, Kelas kelas) {
        getById(id);
        kelas.setId(id);
        return kelasRepository.save(kelas);
    }

    public Kelas delete(Integer id) {
        Kelas kelas = getById(id);
        kelasRepository.delete(kelas);
        return kelas;
    }

    public List<Kelas> searchByName(String name) {
        return kelasRepository.findByName(name);
    }
}