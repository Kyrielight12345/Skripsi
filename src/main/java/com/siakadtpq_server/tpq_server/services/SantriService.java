package com.siakadtpq_server.tpq_server.services;

import com.siakadtpq_server.tpq_server.models.Kelas;
import com.siakadtpq_server.tpq_server.models.Pengajar;
import com.siakadtpq_server.tpq_server.models.Santri;
import com.siakadtpq_server.tpq_server.models.User;
import com.siakadtpq_server.tpq_server.repositories.SantriRepository;
import com.siakadtpq_server.tpq_server.repositories.UserRepository;
import com.siakadtpq_server.tpq_server.models.dto.request.Detail_SantriRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SantriService {

    private final SantriRepository santriRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public List<Santri> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        Optional<User> user = userRepository.findByUsername(loggedInUsername);

        if (user.isPresent()) {
            if (user.get().getRelatedEntity() instanceof Santri) {
                Santri santri = (Santri) user.get().getRelatedEntity();
                return Collections.singletonList(santri);
            }

            else if (user.get().getRelatedEntity() instanceof Pengajar) {
                Pengajar pengajar = (Pengajar) user.get().getRelatedEntity();
                Kelas kelasPengajar = pengajar.getKelas();
                if (kelasPengajar != null) {
                    return santriRepository.findByDeletedFalseAndJilid_Kelas(kelasPengajar);
                }
            }
        }

        return santriRepository.findAllByDeletedFalse();
    }

    public Santri getById(Integer id) {
        return santriRepository
                .findById(id)
                .filter(santri -> !santri.isDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Santri not found!!!"));
    }

    public Santri create(Detail_SantriRequest detailUserRequest) {
        Santri santri = modelMapper.map(detailUserRequest, Santri.class);

        User user = new User();
        user.setUsername(detailUserRequest.getUsername());
        user.setPassword(passwordEncoder.encode(detailUserRequest.getPassword()));
        user.setRole(detailUserRequest.getRole());

        User savedUser = userRepository.save(user);

        santri.setUser(savedUser);

        return santriRepository.save(santri);
    }

    public Santri update(Integer id, Santri santri) {
        getById(id);
        santri.setId(id);
        return santriRepository.save(santri);
    }

    public void delete(Integer id) {
        Santri santri = getById(id);
        santri.setDeleted(true);
        santri.setDeletedAt(LocalDateTime.now());
        santriRepository.save(santri);
    }

    public List<Santri> searchByName(String name) {
        return santriRepository.findByNameAndDeletedFalse(name);
    }
}
