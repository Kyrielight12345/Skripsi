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
            // If logged in as Santri, group progress for that specific Santri
            Santri santri = currentUser.getSantri();
            return progressRepository.findBySantri(santri).stream()
                    .collect(Collectors.groupingBy(p -> p.getSantri().getId()));
        } else if ("pengajar".equals(currentUser.getRole())) {
            // If logged in as Pengajar, filter progress by the 'Kelas' of the Pengajar
            Pengajar pengajar = currentUser.getPengajar();
            Kelas kelasPengajar = pengajar.getKelas();

            if (kelasPengajar != null) {
                // Fetch Santri based on the Kelas Pengajar
                List<Santri> santrisInKelas = santriRepository.findByDeletedFalseAndJilid_Kelas(kelasPengajar);
                // Filter progress of Santris in the Pengajar's class (Kelas)
                return progressRepository.findBySantriIn(santrisInKelas).stream()
                        .collect(Collectors.groupingBy(p -> p.getSantri().getId()));
            }
            return Collections.emptyMap(); // Return empty if no kelas is associated
        } else {
            // For other roles (e.g., Admin), return all progress entries grouped by Santri
            List<Progress> allProgress = progressRepository.findAll();
            return allProgress.stream()
                    .collect(Collectors.groupingBy(p -> p.getSantri().getId()));
        }
    }

    // Helper method to get the currently logged-in user
    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }
}