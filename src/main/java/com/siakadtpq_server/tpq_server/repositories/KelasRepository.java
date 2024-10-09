package com.siakadtpq_server.tpq_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siakadtpq_server.tpq_server.models.Kelas;
import java.util.List;

@Repository
public interface KelasRepository extends JpaRepository<Kelas, Integer> {
    List<Kelas> findByName(String name);
}