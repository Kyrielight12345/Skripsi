package com.tpq.clinet.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tpq.clinet.models.dto.request.LoginRequest;
import com.tpq.clinet.models.dto.Response.LoginResponse;

@Service
public class AuthService {

  @Value("${server.base.url}/login")
  private String url;

  @Autowired
  private RestTemplate restTamplate;

  public Boolean login(LoginRequest loginRequest) {
    try {
      ResponseEntity<LoginResponse> response = restTamplate.exchange(
          url,
          HttpMethod.POST,
          new HttpEntity<>(loginRequest),
          LoginResponse.class);

      if (response.getStatusCode() == HttpStatus.OK) {
        setPrinciple(response.getBody(), loginRequest.getPassword());
        return true;
      }
    } catch (Exception e) {
      System.out.println("Error = " + e.getMessage());
      return false;
    }
    return false;
  }

  public void setPrinciple(LoginResponse response, String password) {
    List<SimpleGrantedAuthority> authorities = Collections.singletonList(
        new SimpleGrantedAuthority(response.getRole()));

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        response.getUsername(),
        password,
        authorities);

    SecurityContextHolder.getContext().setAuthentication(token);
  }
}