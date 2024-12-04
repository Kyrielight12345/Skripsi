package com.siakadtpq_server.tpq_server.services;

import lombok.AllArgsConstructor;

import com.siakadtpq_server.tpq_server.models.Kelas;
import com.siakadtpq_server.tpq_server.models.Pengajar;
import com.siakadtpq_server.tpq_server.models.Progress;
import com.siakadtpq_server.tpq_server.models.Santri;
import com.siakadtpq_server.tpq_server.models.User;
import com.siakadtpq_server.tpq_server.repositories.ProgressRepository;
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
public class ProgressService {

    private ProgressRepository progressRepository;
    private UserRepository userRepository;
    private final SantriRepository santriRepository;

    public List<Progress> getAll() {
        return progressRepository.findAll();
    }

    public Progress getById(Integer id) {
        return progressRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Progress not found!!!"));
    }

    public Progress create(Progress progress) {
        return progressRepository.save(progress);
    }

    public Progress update(Integer id, Progress progress) {
        getById(id);
        progress.setId(id);
        return progressRepository.save(progress);
    }

    public Progress delete(Integer id) {
        Progress progress = getById(id);
        progressRepository.delete(progress);
        return progress;
    }

    public List<Progress> getBySantri(Santri santri) {
        return progressRepository.findBySantri(santri);
    }

    // public Map<Integer, List<Progress>> getAllGroupedBySantri() {
    // List<Progress> allProgress = progressRepository.findAll();

    // return allProgress.stream()
    // .collect(Collectors.groupingBy(p -> p.getSantri().getId()));
    // }

    public Map<Integer, List<Progress>> getAllGroupedBySantri() {
        User currentUser = getCurrentUser();

        if ("santri".equals(currentUser.getRole())) {
            Santri santri = currentUser.getSantri();
            return progressRepository.findBySantri(santri).stream()
                    .collect(Collectors.groupingBy(p -> p.getSantri().getId()));
        } else if ("pengajar".equals(currentUser.getRole())) {
            Pengajar pengajar = currentUser.getPengajar();
            Kelas kelasPengajar = pengajar.getKelas();

            if (kelasPengajar != null) {
                List<Santri> santrisInKelas = santriRepository.findByDeletedFalseAndJilid_Kelas(kelasPengajar);
                return progressRepository.findBySantriIn(santrisInKelas).stream()
                        .collect(Collectors.groupingBy(p -> p.getSantri().getId()));
            }
            return Collections.emptyMap();
        } else {
            List<Progress> allProgress = progressRepository.findAll();
            return allProgress.stream()
                    .collect(Collectors.groupingBy(p -> p.getSantri().getId()));
        }
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }
}