package com.tpq.clinet.models.dto.request;

import java.sql.Date;

import com.tpq.clinet.models.JenisKelamin;
import com.tpq.clinet.models.Jilid;
import com.tpq.clinet.models.Kelas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Detail_PengajarRequest {

    private Integer id_user;
    private String name;
    private String alamat;
    private String tempat_lahir;
    private Date tanggal_lahir;
    private Date tanggal_bergabung;
    private JenisKelamin jenisKelamin;
    private Kelas kelas;
    private Jilid jilid;
    private String username;
    private String password;
    private String role;
}
