package com.tpq.clinet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import com.tpq.clinet.models.Kelas;

@Service
public class KelasService {
    @Value("${server.base.url}/cart_product")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    public List<Kelas> getAll() {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Kelas>>() {
                })
                .getBody();
    }

    public Kelas getById(Integer id) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.GET,
                null, Kelas.class,
                id)
                .getBody();
    }

    public Kelas create(Kelas kelas) {
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(kelas), Kelas.class)
                .getBody();
    }

    public Kelas update(Integer id, Kelas kelas) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.PUT,
                new HttpEntity<>(kelas),
                Kelas.class)
                .getBody();
    }

    public Kelas delete(Integer id) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.DELETE,
                null,
                Kelas.class)
                .getBody();
    }
}
