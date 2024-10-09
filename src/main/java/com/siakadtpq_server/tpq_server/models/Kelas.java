package com.siakadtpq_server.tpq_server.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_kelas")
public class Kelas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kelas", length = 5)
    private Integer id;

    @Column(name = "nama_kelas", nullable = false, length = 25)
    private String name;

    @OneToMany(mappedBy = "kelas")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Jilid> jilid;

    @OneToMany(mappedBy = "kelas")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Pengajar> pengajar;
}
