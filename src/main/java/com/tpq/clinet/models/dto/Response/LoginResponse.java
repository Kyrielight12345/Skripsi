package com.tpq.clinet.models.dto.Response;

import com.tpq.clinet.models.Santri;
import com.tpq.clinet.models.Pengajar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String username;
    private String role;
    private Integer id;
    private Santri santri;
    private Pengajar pengajar;

}
