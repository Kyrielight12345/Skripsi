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
                UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword());

                Authentication auth = authenticationManager.authenticate(authReq);
                SecurityContextHolder.getContext().setAuthentication(auth);

                User user = usersRepository
                                .findByUsername(loginRequest.getUsername())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                appUserDetailService.loadUserByUsername(loginRequest.getUsername());

                Object relatedEntity = user.getRelatedEntity();

                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setUsername(user.getUsername());
                loginResponse.setRole(user.getRole());

                if (relatedEntity instanceof Santri) {
                        loginResponse.setSantri((Santri) relatedEntity);
                } else if (relatedEntity instanceof Pengajar) {
                        loginResponse.setPengajar((Pengajar) relatedEntity);
                }

                return loginResponse;
        }

}