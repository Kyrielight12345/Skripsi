package com.tpq.clinet.services;

import java.util.List;
import java.util.Map;

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
    @Value("${server.base.url}/nilai")
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

    public List<Nilai> getAll() {
        return restTemplate.exchange(
                adjustUrl(""),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Nilai>>() {
                })
                .getBody();
    }

    public Nilai getById(Integer id) {
        return restTemplate.exchange(
                adjustUrl(String.valueOf(id)),
                HttpMethod.GET,
                null, Nilai.class,
                id)
                .getBody();
    }

    public Nilai create(Nilai nilai) {
        return restTemplate.exchange(
                adjustUrl("create"),
                HttpMethod.POST,
                new HttpEntity<>(nilai), Nilai.class)
                .getBody();
    }

    public Nilai update(Integer id, Nilai nilai) {
        return restTemplate.exchange(
                adjustUrl(String.valueOf(id)),
                HttpMethod.PUT,
                new HttpEntity<>(nilai),
                Nilai.class)
                .getBody();
    }

    public Nilai delete(Integer id) {
        return restTemplate.exchange(
                adjustUrl(String.valueOf(id)),
                HttpMethod.DELETE,
                null,
                Nilai.class)
                .getBody();
    }

    public List<Nilai> getBySantri(Integer santriId) {
        return restTemplate.exchange(
                adjustUrl("/santri/" + santriId),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Nilai>>() {
                })
                .getBody();
    }

    public Map<Integer, List<Nilai>> getAllGroupedBySantri() {
        return restTemplate.exchange(
                adjustUrl("santri"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<Integer, List<Nilai>>>() {
                })
                .getBody();
    }
}
