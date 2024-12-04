package com.siakadtpq_server.tpq_server.models;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_pengajar")
public class Pengajar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pengajar", length = 5)
    private Integer id;

    @Column(name = "nama_pengajar", nullable = false, length = 25)
    private String name;

    @Column(name = "alamat", nullable = false, length = 50)
    private String alamat;

    @Enumerated(EnumType.STRING)
    @Column(name = "jenis_kelamin", nullable = false, length = 10)
    private JenisKelamin jenisKelamin;

    @Column(name = "tempat_lahir", nullable = false, length = 50)
    private String tempat_lahir;

    @Column(name = "tanggal_lahir", nullable = false)
    private Date tanggal_lahir;

    @Column(name = "tanggal_bergabung", nullable = false)
    private Date tanggal_bergabung;

    @OneToMany(mappedBy = "pengajar")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Progress> progress;

    @OneToMany(mappedBy = "pengajar")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Spp> spp;

    @ManyToOne
    @JoinColumn(name = "id_kelas")
    private Kelas kelas;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = true)
    @JsonProperty(access = Access.WRITE_ONLY)
    private User user;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
