package com.tpq.clinet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import com.tpq.clinet.models.Nilai;

@Service
public class NilaiService {
    @Value("${server.base.url}/cart_product")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    public List<Nilai> getAll() {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Nilai>>() {
                })
                .getBody();
    }

    public Nilai getById(Integer id) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.GET,
                null, Nilai.class,
                id)
                .getBody();
    }

    public Nilai create(Nilai nilai) {
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(nilai), Nilai.class)
                .getBody();
    }

    public Nilai update(Integer id, Nilai nilai) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.PUT,
                new HttpEntity<>(nilai),
                Nilai.class)
                .getBody();
    }

    public Nilai delete(Integer id) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.DELETE,
                null,
                Nilai.class)
                .getBody();
    }
}
