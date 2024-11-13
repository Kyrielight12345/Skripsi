package com.tpq.clinet.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Progress {

    private Integer id;
    private Date tanggal_progress;
    private String halaman;
    private String keterangan;
    private Santri santri;
    private Pengajar pengajar;
}
