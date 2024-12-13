package com.tpq.clinet.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private Santri santri;
    private Pengajar pengajar;

    @JsonProperty("relatedEntity")
    public Object getRelatedEntity() {
        if ("santri".equals(role) && santri != null) {
            return santri;
        } else if ("pengajar".equals(role) && pengajar != null) {
            return pengajar;
        }
        return null;
    }
}
