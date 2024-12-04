package com.siakadtpq_server.tpq_server.services;

import lombok.AllArgsConstructor;

import com.siakadtpq_server.tpq_server.models.Kelas;
import com.siakadtpq_server.tpq_server.models.Pengajar;
import com.siakadtpq_server.tpq_server.models.Santri;
import com.siakadtpq_server.tpq_server.models.Spp;
import com.siakadtpq_server.tpq_server.models.User;
import com.siakadtpq_server.tpq_server.repositories.SantriRepository;
import com.siakadtpq_server.tpq_server.repositories.SppRepository;
import com.siakadtpq_server.tpq_server.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class SppService {

    private SppRepository sppRepository;
    private UserRepository userRepository;
    private final SantriRepository santriRepository;

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

    public List<Spp> getBySantri(Santri santri) {
        return sppRepository.findBySantri(santri);
    }

    public Map<Integer, List<Spp>> getAllGroupedBySantri() {
        User currentUser = getCurrentUser();

        if ("santri".equals(currentUser.getRole())) {
            Santri santri = currentUser.getSantri();
            return sppRepository.findBySantri(santri).stream()
                    .collect(Collectors.groupingBy(p -> p.getSantri().getId()));
        } else if ("pengajar".equals(currentUser.getRole())) {
            Pengajar pengajar = currentUser.getPengajar();
            Kelas kelasPengajar = pengajar.getKelas();

            if (kelasPengajar != null) {
                List<Santri> santrisInKelas = santriRepository.findByDeletedFalseAndJilid_Kelas(kelasPengajar);
                return sppRepository.findBySantriIn(santrisInKelas).stream()
                        .collect(Collectors.groupingBy(p -> p.getSantri().getId()));
            }
            return Collections.emptyMap();
        } else {
            List<Spp> allSpp = sppRepository.findAll();
            return allSpp.stream()
                    .collect(Collectors.groupingBy(p -> p.getSantri().getId()));
        }
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }
}