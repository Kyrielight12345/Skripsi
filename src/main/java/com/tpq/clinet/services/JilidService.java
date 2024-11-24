package com.tpq.clinet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import com.tpq.clinet.models.Jilid;

@Service
public class JilidService {
    @Value("${server.base.url}/jilid")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    public List<Jilid> getAll() {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Jilid>>() {
                })
                .getBody();
    }

    public Jilid getById(Integer id) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.GET,
                null, Jilid.class,
                id)
                .getBody();
    }

    public Jilid create(Jilid jilid) {
        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(jilid), Jilid.class)
                .getBody();
    }

    public Jilid update(Integer id, Jilid jilid) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.PUT,
                new HttpEntity<>(jilid),
                Jilid.class)
                .getBody();
    }

    public Jilid delete(Integer id) {
        return restTemplate.exchange(
                url.concat("/" + id),
                HttpMethod.DELETE,
                null,
                Jilid.class)
                .getBody();
    }
}
