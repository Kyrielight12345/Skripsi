package com.siakadtpq_server.tpq_server.repositories;

import com.siakadtpq_server.tpq_server.models.Progress;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository extends JpaRepository<Progress, Integer> {
}
