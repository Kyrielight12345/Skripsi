package com.siakadtpq_server.tpq_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.siakadtpq_server.tpq_server.models.Santri;
import java.util.List;

@Repository
public interface SantriRepository extends JpaRepository<Santri, Integer> {
    List<Santri> findByName(String name);
}
