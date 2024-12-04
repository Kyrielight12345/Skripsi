package com.siakadtpq_server.tpq_server.repositories;

import com.siakadtpq_server.tpq_server.models.Santri;
import com.siakadtpq_server.tpq_server.models.Spp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SppRepository extends JpaRepository<Spp, Integer> {
    List<Spp> findBySantri(Santri santri);

    @Query("SELECT p FROM Spp p WHERE p.santri.id = :santriId")
    List<Spp> findBySantriId(@Param("santriId") Integer santriId);

    @Query("SELECT p FROM Spp p WHERE p.santri IN :santris")
    List<Spp> findBySantriIn(@Param("santris") List<Santri> santris);
}
