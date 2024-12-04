package com.siakadtpq_server.tpq_server.repositories;

import com.siakadtpq_server.tpq_server.models.Nilai;
import com.siakadtpq_server.tpq_server.models.Santri;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NilaiRepository extends JpaRepository<Nilai, Integer> {
    List<Nilai> findBySantri(Santri santri);

    @Query("SELECT p FROM Nilai p WHERE p.santri.id = :santriId")
    List<Nilai> findBySantriId(@Param("santriId") Integer santriId);

    @Query("SELECT p FROM Nilai p WHERE p.santri IN :santris")
    List<Nilai> findBySantriIn(@Param("santris") List<Santri> santris);
}
