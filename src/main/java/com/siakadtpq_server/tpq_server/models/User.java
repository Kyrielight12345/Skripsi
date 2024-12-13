package com.siakadtpq_server.tpq_server.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", length = 5)
    private Integer id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonProperty(access = Access.WRITE_ONLY)
    private Santri santri;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonProperty(access = Access.WRITE_ONLY)
    private Pengajar pengajar;

    @JsonProperty("relatedEntity")
    public Object getRelatedEntity() {
        if ("santri".equals(role)) {
            return santri;
        } else if ("pengajar".equals(role)) {
            return pengajar;
        }
        return null;
    }
}
