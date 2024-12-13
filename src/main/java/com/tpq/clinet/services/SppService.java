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

import com.tpq.clinet.models.Spp;

@Service
public class SppService {
    @Value("${server.base.url}/spp")
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

    public List<Spp> getAll() {
        return restTemplate.exchange(
                adjustUrl(""),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Spp>>() {
                })
                .getBody();
    }

    public Spp getById(Integer id) {
        return restTemplate.exchange(
                adjustUrl(String.valueOf(id)),
                HttpMethod.GET,
                null, Spp.class,
                id)
                .getBody();
    }

    public Spp create(Spp spp) {
        return restTemplate.exchange(
                adjustUrl("create"),
                HttpMethod.POST,
                new HttpEntity<>(spp), Spp.class)
                .getBody();
    }

    public Spp update(Integer id, Spp spp) {
        return restTemplate.exchange(
                adjustUrl(String.valueOf(id)),
                HttpMethod.PUT,
                new HttpEntity<>(spp),
                Spp.class)
                .getBody();
    }

    public Spp delete(Integer id) {
        return restTemplate.exchange(
                adjustUrl(String.valueOf(id)),
                HttpMethod.DELETE,
                null,
                Spp.class)
                .getBody();
    }

    public List<Spp> getBySantri(Integer santriId) {
        return restTemplate.exchange(
                adjustUrl("/santri/" + santriId),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Spp>>() {
                })
                .getBody();
    }

    public Map<Integer, List<Spp>> getAllGroupedBySantri() {
        return restTemplate.exchange(
                adjustUrl("santri"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<Integer, List<Spp>>>() {
                })
                .getBody();
    }
}
