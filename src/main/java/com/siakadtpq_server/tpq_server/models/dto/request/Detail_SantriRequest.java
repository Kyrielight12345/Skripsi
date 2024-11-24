package com.siakadtpq_server.tpq_server.models.dto.request;

import java.sql.Date;

import com.siakadtpq_server.tpq_server.models.Jilid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Detail_SantriRequest {

    private Integer id_user;
    private String name;
    private String alamat;
    private String tempat_lahir;
    private Date tanggal_lahir;
    private Date tanggal_bergabung;
    private Jilid jilid;
    private String username;
    private String password;
    private String role;

}