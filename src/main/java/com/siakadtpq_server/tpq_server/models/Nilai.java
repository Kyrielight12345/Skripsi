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
@Table(name = "tb_nilai")
public class Nilai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nilai", length = 5)
    private Integer id;

    @Column(name = "tanggal_ujian", nullable = false)
    private Date tanggal_ujian;

    @Column(name = "al_harakat", nullable = false, length = 25)
    private Integer al_harakat;

    @Column(name = "makhraj", nullable = false, length = 25)
    private Integer makhraj;

    @Column(name = "imla", nullable = false, length = 25)
    private Integer imla;

    @Column(name = "an_namroh", nullable = false, length = 25)
    private Integer an_namroh;

    @Column(name = "al_lafadz", nullable = false, length = 25)
    private Integer al_lafadz;

    @Column(name = "tajwid", nullable = false, length = 25)
    private Integer tajwid;

    @Column(name = "gharib", nullable = false, length = 25)
    private Integer gharib;

    @Column(name = "al_mad ", nullable = false, length = 25)
    private Integer al_mad;

    @ManyToOne
    @JoinColumn(name = "id_santri")
    private Santri santri;

}
