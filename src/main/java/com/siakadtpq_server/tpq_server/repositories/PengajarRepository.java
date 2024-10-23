package com.siakadtpq_server.tpq_server.repositories;

import com.siakadtpq_server.tpq_server.models.Pengajar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PengajarRepository extends JpaRepository<Pengajar, Integer> {
    List<Pengajar> findAllByDeletedFalse();

    List<Pengajar> findByNameAndDeletedFalse(String name);
}
