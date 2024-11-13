package com.tpq.clinet.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nilai {

    private Integer id;
    private Date tanggal_ujian;
    private Integer al_harakat;
    private Integer makhraj;
    private Integer imla;
    private Integer an_namroh;
    private Integer al_lafadz;
    private Integer tajwid;
    private Integer gharib;
    private Integer al_mad;
    private Santri santri;

}
