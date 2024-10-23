package com.siakadtpq_server.tpq_server.services;

import com.siakadtpq_server.tpq_server.models.Santri;
import com.siakadtpq_server.tpq_server.models.User;
import com.siakadtpq_server.tpq_server.repositories.SantriRepository;
import com.siakadtpq_server.tpq_server.repositories.UserRepository;
import com.siakadtpq_server.tpq_server.models.dto.request.Detail_SantriRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SantriService {

    private SantriRepository santriRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public List<Santri> getAll() {
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
