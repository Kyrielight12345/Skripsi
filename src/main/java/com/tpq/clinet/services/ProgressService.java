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
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    public List<Progress> getAll() {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Progress>>() {
                })
                .getBody();
    }

    public Progress getById(Integer id) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.GET,
                null, Progress.class,
                id)
                .getBody();
    }

    public Progress create(Progress progress) {
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(progress), Progress.class)
                .getBody();
    }

    public Progress update(Integer id, Progress progress) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.PUT,
                new HttpEntity<>(progress),
                Progress.class)
                .getBody();
    }

    public Progress delete(Integer id) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.DELETE,
                null,
                Progress.class)
                .getBody();
    }

    public List<Progress> getBySantri(Integer santriId) {
        return restTemplate.exchange(
                url.concat("/santri/" + santriId),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Progress>>() {
                })
                .getBody();
    }

    public Map<Integer, List<Progress>> getAllGroupedBySantri() {
        return restTemplate.exchange(
                url.concat("/santri"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<Integer, List<Progress>>>() {
                })
                .getBody();
    }
}
