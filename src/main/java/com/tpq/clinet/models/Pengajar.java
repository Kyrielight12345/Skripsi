package com.tpq.clinet.models;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pengajar {

    private Integer id;
    private String name;
    private String alamat;
    private String tempat_lahir;
    private Date tanggal_lahir;
    private Date tanggal_bergabung;
    private JenisKelamin JenisKelamin;
    private List<Progress> progress;
    private List<Spp> spp;
    private Kelas kelas;
    private User user;
    private boolean deleted = false;
    private LocalDateTime deletedAt;
}
