package com.tpq.clinet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import com.tpq.clinet.models.Santri;
import com.tpq.clinet.models.dto.request.Detail_SantriRequest;

@Service
public class SantriService {
    @Value("${server.base.url}/santri")
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

    public List<Santri> getAll() {
        return restTemplate
                .exchange(
                        adjustUrl(""),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Santri>>() {
                        })
                .getBody();
    }

    public Santri getById(Integer id) {
        return restTemplate.exchange(
                adjustUrl(String.valueOf(id)),
                HttpMethod.GET,
                null, Santri.class,
                id)
                .getBody();
    }

    public Santri create(Detail_SantriRequest detailUserRequest) {
        return restTemplate.exchange(
                adjustUrl("create"),
                HttpMethod.POST,
                new HttpEntity<>(detailUserRequest), Santri.class)
                .getBody();
    }

    public Santri update(Integer id, Santri santri) {
        return restTemplate.exchange(
                adjustUrl(String.valueOf(id)),
                HttpMethod.PUT,
                new HttpEntity<>(santri),
                Santri.class)
                .getBody();
    }

    public Santri delete(Integer id) {
        return restTemplate.exchange(
                adjustUrl(String.valueOf(id)),
                HttpMethod.DELETE,
                null,
                Santri.class)
                .getBody();
    }
}
