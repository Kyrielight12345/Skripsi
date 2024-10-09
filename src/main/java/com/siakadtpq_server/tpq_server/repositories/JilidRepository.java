package com.siakadtpq_server.tpq_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siakadtpq_server.tpq_server.models.Jilid;
import java.util.List;

@Repository
public interface JilidRepository extends JpaRepository<Jilid, Integer> {
    List<Jilid> findByName(String name);
}