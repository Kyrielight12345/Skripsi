package com.siakadtpq_server.tpq_server.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tb_jilid")
public class Jilid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jilid", length = 5)
    private Integer id;

    @Column(name = "nama_jilid", nullable = false, length = 25)
    private String name;

    @OneToMany(mappedBy = "jilid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Santri> santri;

    @ManyToOne
    @JoinColumn(name = "id_kelas")
    private Kelas kelas;

}
