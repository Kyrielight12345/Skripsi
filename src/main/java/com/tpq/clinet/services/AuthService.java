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
  private RestTemplate restTemplate; // Fixed typo from restTamplate to restTemplate

  public Boolean login(LoginRequest loginRequest) {
    try {
      // Send a POST request to the login URL
      ResponseEntity<LoginResponse> response = restTemplate.exchange(
          url,
          HttpMethod.POST,
          new HttpEntity<>(loginRequest),
          LoginResponse.class);

      // Check if the response is successful
      if (response.getStatusCode() == HttpStatus.OK) {
        // Set the principle (authentication) with the returned login response
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
    // Ensure the role is prefixed with "ROLE_" as expected by Spring Security
    String role = "ROLE_" + response.getRole().toUpperCase();

    // Create a list of authorities (roles) for the authenticated user
    List<SimpleGrantedAuthority> authorities = Collections.singletonList(
        new SimpleGrantedAuthority(role));

    // Create the authentication token with username, password, and authorities
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        response.getUsername(),
        password,
        authorities);

    // Set the authentication token into the SecurityContext
    SecurityContextHolder.getContext().setAuthentication(token);
  }
}
