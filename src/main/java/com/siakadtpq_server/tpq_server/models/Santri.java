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
@Table(name = "tb_santri")
public class Santri {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_santri", length = 5)
    private Integer id;

    @Column(name = "nama_santri", nullable = false, length = 25)
    private String name;

    @Column(name = "alamat", nullable = false, length = 50)
    private String alamat;

    @Column(name = "tempat_lahir", nullable = false, length = 50)
    private String tempat_lahir;

    @Column(name = "tanggal_lahir", nullable = false)
    private Date tanggal_lahir;

    @Column(name = "tanggal_bergabung", nullable = false)
    private Date tanggal_bergabung;

    @Enumerated(EnumType.STRING)
    @Column(name = "jenis_kelamin", nullable = false, length = 10)
    private JenisKelamin jenisKelamin;

    @OneToMany(mappedBy = "santri")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Progress> progress;

    @OneToMany(mappedBy = "santri")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Spp> spp;

    @OneToMany(mappedBy = "santri")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Nilai> nilai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_jilid")
    private Jilid jilid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = true)
    @JsonProperty(access = Access.WRITE_ONLY)
    private User user;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
