package com.siakadtpq_server.tpq_server.services;

import com.siakadtpq_server.tpq_server.models.Pengajar;
import com.siakadtpq_server.tpq_server.models.User;
import com.siakadtpq_server.tpq_server.repositories.PengajarRepository;
import com.siakadtpq_server.tpq_server.repositories.UserRepository;
import com.siakadtpq_server.tpq_server.models.dto.request.Detail_PengajarRequest;
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
public class PengajarService {

    private PengajarRepository pengajarRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public List<Pengajar> getAll() {
        return pengajarRepository.findAllByDeletedFalse();
    }

    public Pengajar getById(Integer id) {
        return pengajarRepository
                .findById(id)
                .filter(pengajar -> !pengajar.isDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pengajar not found!!!"));
    }

    public Pengajar create(Detail_PengajarRequest detailUserRequest) {
        Pengajar pengajar = modelMapper.map(detailUserRequest, Pengajar.class);

        User user = new User();
        user.setUsername(detailUserRequest.getUsername());
        user.setPassword(passwordEncoder.encode(detailUserRequest.getPassword()));
        user.setRole(detailUserRequest.getRole());

        User savedUser = userRepository.save(user);

        pengajar.setUser(savedUser);

        return pengajarRepository.save(pengajar);
    }

    public Pengajar update(Integer id, Pengajar pengajar) {
        getById(id);
        pengajar.setId(id);
        return pengajarRepository.save(pengajar);
    }

    public void delete(Integer id) {
        Pengajar pengajar = getById(id);
        pengajar.setDeleted(true);
        pengajar.setDeletedAt(LocalDateTime.now());
        pengajarRepository.save(pengajar);
    }

    public List<Pengajar> searchByName(String name) {
        return pengajarRepository.findByNameAndDeletedFalse(name);
    }
}
