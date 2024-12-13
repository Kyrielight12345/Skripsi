package com.siakadtpq_server.tpq_server.services;

import com.siakadtpq_server.tpq_server.models.Pengajar;
import com.siakadtpq_server.tpq_server.models.Santri;
import com.siakadtpq_server.tpq_server.models.User;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.siakadtpq_server.tpq_server.models.dto.request.LoginRequest;
import com.siakadtpq_server.tpq_server.models.dto.Response.LoginResponse;
import com.siakadtpq_server.tpq_server.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@AllArgsConstructor
public class AuthService {

        private AuthenticationManager authenticationManager;
        private UserRepository usersRepository;
        private AppUserDetailService appUserDetailService;

        public LoginResponse login(LoginRequest loginRequest) {
                // Create an authentication token
                UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword());

                // Authenticate the user
                Authentication auth = authenticationManager.authenticate(authReq);
                SecurityContextHolder.getContext().setAuthentication(auth);

                // Fetch the user from the repository
                User user = usersRepository
                                .findByUsername(loginRequest.getUsername())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                // Load user details
                appUserDetailService.loadUserByUsername(loginRequest.getUsername());

                // Get the related entity (Santri or Pengajar)
                Object relatedEntity = user.getRelatedEntity();

                // Create a new login response
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setUsername(user.getUsername());
                loginResponse.setRole(user.getRole());

                // Set the related entity and its ID based on the role
                if (relatedEntity instanceof Santri) {
                        Santri santri = (Santri) relatedEntity;
                        loginResponse.setSantri(santri);
                        loginResponse.setId(santri.getId()); // Set the Santri's ID
                } else if (relatedEntity instanceof Pengajar) {
                        Pengajar pengajar = (Pengajar) relatedEntity;
                        loginResponse.setPengajar(pengajar);
                        loginResponse.setId(pengajar.getId()); // Set the Pengajar's ID
                }

                return loginResponse;
        }
}
