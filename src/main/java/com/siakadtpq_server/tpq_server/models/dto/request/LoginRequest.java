package com.siakadtpq_server.tpq_server.models.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}