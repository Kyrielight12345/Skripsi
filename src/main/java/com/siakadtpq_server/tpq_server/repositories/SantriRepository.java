package com.siakadtpq_server.tpq_server.repositories;

import com.siakadtpq_server.tpq_server.models.Santri;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SantriRepository extends JpaRepository<Santri, Integer> {
    List<Santri> findAllByDeletedFalse();

    List<Santri> findByNameAndDeletedFalse(String name);
}
