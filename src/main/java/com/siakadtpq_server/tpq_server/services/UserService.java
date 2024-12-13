package com.siakadtpq_server.tpq_server.services;

import lombok.AllArgsConstructor;

import com.siakadtpq_server.tpq_server.models.User;
import com.siakadtpq_server.tpq_server.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Integer id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kelas not found!!!"));
    }

    public User update(Integer id, User user) {
        // Ensure the user exists in the database
        User existingUser = getById(id); // This can be an existing method to fetch the user by id.

        // Set the id for the user being updated
        user.setId(id);

        // Update username if provided
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            existingUser.setUsername(user.getUsername());
        }

        // Update password if provided
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword())); // Password encryption
        }

        // Update role if provided
        if (user.getRole() != null && !user.getRole().isEmpty()) {
            existingUser.setRole(user.getRole());
        }

        // Update related entities (Santri or Pengajar) based on the role if provided
        if (user.getRole() != null) {
            if ("santri".equals(user.getRole()) && user.getSantri() != null) {
                existingUser.setSantri(user.getSantri());
            } else if ("pengajar".equals(user.getRole()) && user.getPengajar() != null) {
                existingUser.setPengajar(user.getPengajar());
            }
        }

        // Save the updated user
        return userRepository.save(existingUser);
    }

    public User delete(Integer id) {
        User user = getById(id);
        userRepository.delete(user);
        return user;
    }

    public Optional<User> searchByName(String name) {
        return userRepository.findByUsername(name);
    }
}