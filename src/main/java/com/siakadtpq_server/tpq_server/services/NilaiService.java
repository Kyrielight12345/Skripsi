package com.siakadtpq_server.tpq_server.services;

import lombok.AllArgsConstructor;

import com.siakadtpq_server.tpq_server.models.Kelas;
import com.siakadtpq_server.tpq_server.models.Nilai;
import com.siakadtpq_server.tpq_server.models.Pengajar;
import com.siakadtpq_server.tpq_server.models.Santri;
import com.siakadtpq_server.tpq_server.models.User;
import com.siakadtpq_server.tpq_server.repositories.NilaiRepository;
import com.siakadtpq_server.tpq_server.repositories.SantriRepository;
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
public class NilaiService {

    private NilaiRepository nilaiRepository;
    private UserRepository userRepository;
    private final SantriRepository santriRepository;

    public List<Nilai> getAll() {
        User currentUser = getCurrentUser();
        if ("santri".equals(currentUser.getRole())) {
            Santri santri = currentUser.getSantri();
            if (santri != null) {
                return nilaiRepository.findBySantri(santri);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Santri tidak ditemukan untuk user ini!");
            }
        } else {
            return nilaiRepository.findAll();
        }
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

    public List<Nilai> getBySantri(Santri santri) {
        return nilaiRepository.findBySantri(santri);
    }

    public Map<Integer, List<Nilai>> getAllGroupedBySantri() {
        User currentUser = getCurrentUser();

        if ("santri".equals(currentUser.getRole())) {
            Santri santri = currentUser.getSantri();
            return nilaiRepository.findBySantri(santri).stream()
                    .collect(Collectors.groupingBy(p -> p.getSantri().getId()));
        } else if ("pengajar".equals(currentUser.getRole())) {
            Pengajar pengajar = currentUser.getPengajar();
            Kelas kelasPengajar = pengajar.getKelas();

            if (kelasPengajar != null) {
                List<Santri> santrisInKelas = santriRepository.findByDeletedFalseAndJilid_Kelas(kelasPengajar);
                return nilaiRepository.findBySantriIn(santrisInKelas).stream()
                        .collect(Collectors.groupingBy(p -> p.getSantri().getId()));
            }
            return Collections.emptyMap();
        } else {
            List<Nilai> allNilai = nilaiRepository.findAll();
            return allNilai.stream()
                    .collect(Collectors.groupingBy(p -> p.getSantri().getId()));
        }
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }
}