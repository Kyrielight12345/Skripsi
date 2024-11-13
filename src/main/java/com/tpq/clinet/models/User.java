package com.tpq.clinet.models;

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

    public Object getRelatedEntity() {
        if ("santri".equals(role)) {
            return santri;
        } else if ("pengajar".equals(role)) {
            return pengajar;
        }
        return null;
    }
}
