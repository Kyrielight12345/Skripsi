package com.tpq.clinet.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Spp {

    private Integer id;
    private Date tanggal_bayar;
    private Integer jumlah_bayar;
    private Integer bayar_bulan;
    private Integer bayar_tahun;
    private Santri santri;
    private Pengajar pengajar;

}
