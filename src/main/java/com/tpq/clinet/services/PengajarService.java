package com.tpq.clinet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import com.tpq.clinet.models.Pengajar;
import com.tpq.clinet.models.dto.request.Detail_PengajarRequest;

@Service
public class PengajarService {
    @Value("${server.base.url}/cart_product")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    public List<Pengajar> getAll() {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Pengajar>>() {
                })
                .getBody();
    }

    public Pengajar getById(Integer id) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.GET,
                null, Pengajar.class,
                id)
                .getBody();
    }

    public Pengajar create(Detail_PengajarRequest detailUserRequest) {
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(detailUserRequest), Pengajar.class)
                .getBody();
    }

    public Pengajar update(Integer id, Pengajar pengajar) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.PUT,
                new HttpEntity<>(pengajar),
                Pengajar.class)
                .getBody();
    }

    public Pengajar delete(Integer id) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.DELETE,
                null,
                Pengajar.class)
                .getBody();
    }
}
