package com.tpq.clinet.utils;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;

public class RequestInterceptorUtil implements ClientHttpRequestInterceptor {
  @Override
  public ClientHttpResponse intercept(
      HttpRequest request,
      byte[] body,
      ClientHttpRequestExecution execution) throws IOException {
    // get principle
    Authentication auth = SecurityContextHolder
        .getContext()
        .getAuthentication();

    // check basic header from login backend
    if (!request.getURI().getPath().equals("/login")) {
      request
          .getHeaders()
          .add(
              "Authorization",
              "Basic " +
                  BaseHeaderUtil.createBasicToken(
                      auth.getPrincipal().toString(),
                      auth.getCredentials().toString()));
    }

    ClientHttpResponse response = execution.execute(request, body);
    return response;
  }
}