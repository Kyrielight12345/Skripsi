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

import com.tpq.clinet.models.Progress;

@Service
public class ProgressService {
    @Value("${server.base.url}/progress")
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

    public List<Progress> getAll() {
        return restTemplate.exchange(
                adjustUrl(""),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Progress>>() {
                })
                .getBody();
    }

    public Progress getById(Integer id) {
        return restTemplate.exchange(
                adjustUrl(String.valueOf(id)),
                HttpMethod.GET,
                null, Progress.class,
                id)
                .getBody();
    }

    public Progress create(Progress progress) {
        return restTemplate.exchange(
                adjustUrl("create"),
                HttpMethod.POST,
                new HttpEntity<>(progress), Progress.class)
                .getBody();
    }

    public Progress update(Integer id, Progress progress) {
        return restTemplate.exchange(
                adjustUrl(String.valueOf(id)),
                HttpMethod.PUT,
                new HttpEntity<>(progress),
                Progress.class)
                .getBody();
    }

    public Progress delete(Integer id) {
        return restTemplate.exchange(
                adjustUrl(String.valueOf(id)),
                HttpMethod.DELETE,
                null,
                Progress.class)
                .getBody();
    }

    public List<Progress> getBySantri(Integer santriId) {
        return restTemplate.exchange(
                adjustUrl("/santri/" + santriId),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Progress>>() {
                })
                .getBody();
    }

    public Map<Integer, List<Progress>> getAllGroupedBySantri() {
        return restTemplate.exchange(
                adjustUrl("santri"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<Integer, List<Progress>>>() {
                })
                .getBody();
    }
}
