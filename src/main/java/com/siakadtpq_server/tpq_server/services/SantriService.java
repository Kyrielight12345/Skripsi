package com.siakadtpq_server.tpq_server.services;

import lombok.AllArgsConstructor;
import com.siakadtpq_server.tpq_server.models.Santri;
import com.siakadtpq_server.tpq_server.repositories.SantriRepository;
import com.siakadtpq_server.tpq_server.repositories.UserRepository;
import com.siakadtpq_server.tpq_server.models.User;
import com.siakadtpq_server.tpq_server.models.dto.request.Detail_SantriRequest;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class SantriService {

    private SantriRepository santriRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public List<Santri> getAll() {
        return santriRepository.findAll();
    }

    public Santri getById(Integer id) {
        return santriRepository
                .findById(id)
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

    public Santri delete(Integer id) {
        try {
            Santri santri = getById(id);
            santriRepository.delete(santri);
            return santri;
        } catch (Exception e) {
            System.out.println("Error during delete: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete Santri");
        }

    }

    public List<Santri> searchByName(String name) {
        return santriRepository.findByName(name);
    }
}
