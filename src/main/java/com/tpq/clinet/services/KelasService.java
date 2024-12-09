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

    @Value("${server.base.url}/kelas")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    private String adjustUrl(String endpoint) {
        if (endpoint == null || endpoint.isEmpty()) {
            return baseUrl;
        }
        if (endpoint.startsWith("/")) {
            return baseUrl.concat(endpoint);
        }
        return baseUrl + "/" + endpoint;
    }

    public List<Kelas> getAll() {
        return restTemplate.exchange(
                adjustUrl(""),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Kelas>>() {
                })
                .getBody();
    }

    public Kelas getById(Integer id) {
        return restTemplate.exchange(
                adjustUrl(String.valueOf(id)),
                HttpMethod.GET,
                null,
                Kelas.class)
                .getBody();
    }

    public Kelas create(Kelas kelas) {
        return restTemplate.exchange(
                adjustUrl("create"),
                HttpMethod.POST,
                new HttpEntity<>(kelas),
                Kelas.class)
                .getBody();
    }

    public Kelas update(Integer id, Kelas kelas) {
        return restTemplate.exchange(
                adjustUrl(String.valueOf(id)),
                HttpMethod.PUT,
                new HttpEntity<>(kelas),
                Kelas.class)
                .getBody();
    }

    public Kelas delete(Integer id) {
        return restTemplate.exchange(
                adjustUrl(String.valueOf(id)),
                HttpMethod.DELETE,
                null,
                Kelas.class)
                .getBody();
    }
}
