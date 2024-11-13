package com.tpq.clinet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import com.tpq.clinet.models.Spp;
@Service
public class SppService {
    @Value("${server.base.url}/cart_product")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    public List<Spp> getAll() {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Spp>>() {
                })
                .getBody();
    }

    public Spp getById(Integer id) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.GET,
                null, Spp.class,
                id)
                .getBody();
    }

    public Spp create(Spp spp) {
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(spp), Spp.class)
                .getBody();
    }

    public Spp update(Integer id, Spp spp) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.PUT,
                new HttpEntity<>(spp),
                Spp.class)
                .getBody();
    }

    public Spp delete(Integer id) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.DELETE,
                null,
                Spp.class)
                .getBody();
    }
}
