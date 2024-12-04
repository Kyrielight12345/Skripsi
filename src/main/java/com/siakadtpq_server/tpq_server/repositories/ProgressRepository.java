package com.siakadtpq_server.tpq_server.repositories;

import com.siakadtpq_server.tpq_server.models.Pengajar;
import com.siakadtpq_server.tpq_server.models.Progress;
import com.siakadtpq_server.tpq_server.models.Santri;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProgressRepository extends JpaRepository<Progress, Integer> {
    List<Progress> findBySantri(Santri santri);

    @Query("SELECT p FROM Progress p WHERE p.santri.id = :santriId")
    List<Progress> findBySantriId(@Param("santriId") Integer santriId);

    Collection<Progress> findByPengajar(Pengajar pengajar);

    @Query("SELECT p FROM Progress p WHERE p.santri IN :santris")
    List<Progress> findBySantriIn(@Param("santris") List<Santri> santris);
}
