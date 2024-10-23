package com.siakadtpq_server.tpq_server.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_spp")
public class Spp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_spp", length = 5)
    private Integer id;

    @Column(name = "tanggal_bayar", nullable = false)
    private Date tanggal_bayar;

    @Column(name = "jumlah_bayar", nullable = false, length = 25)
    private Integer jumlah_bayar;

    @ManyToOne
    @JoinColumn(name = "id_santri")
    private Santri santri;

    @ManyToOne
    @JoinColumn(name = "id_pengajar")
    private Pengajar pengajar;

}
