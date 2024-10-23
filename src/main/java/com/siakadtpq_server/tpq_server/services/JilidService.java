package com.siakadtpq_server.tpq_server.services;

import lombok.AllArgsConstructor;

import com.siakadtpq_server.tpq_server.models.Jilid;
import com.siakadtpq_server.tpq_server.models.Kelas;
import com.siakadtpq_server.tpq_server.repositories.JilidRepository;
import com.siakadtpq_server.tpq_server.repositories.KelasRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class JilidService {

    private KelasRepository kelasRepository;
    private JilidRepository jilidRepository;

    public List<Jilid> getAll() {
        return jilidRepository.findAll();
    }

    public Jilid getById(Integer id) {
        return jilidRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jilid not found!!!"));
    }

    public Jilid create(Jilid jilid) {
        if (jilid.getKelas() != null && jilid.getKelas().getId() != null) {
        Kelas kelas = kelasRepository.findById(jilid.getKelas().getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kelas not found!"));
        jilid.setKelas(kelas);
    }
    return jilidRepository.save(jilid);
    }

    public Jilid update(Integer id, Jilid jilid) {
        getById(id);
        jilid.setId(id);
        return jilidRepository.save(jilid);
    }

    public Jilid delete(Integer id) {
        Jilid jilid = getById(id);
        jilidRepository.delete(jilid);
        return jilid;
    }

    public List<Jilid> searchByName(String name) {
        return jilidRepository.findByName(name);
    }
}