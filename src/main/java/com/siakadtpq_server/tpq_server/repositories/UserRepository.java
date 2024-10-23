package com.siakadtpq_server.tpq_server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siakadtpq_server.tpq_server.models.User;;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUsername(String username);
}
