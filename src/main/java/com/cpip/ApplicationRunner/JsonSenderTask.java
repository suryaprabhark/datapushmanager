package com.cpip.ApplicationRunner;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class JsonSenderTask implements Runnable {

    private final String json;
    private final String endpointUrl;
    private final String httpMethod;
    private final String bearerToken;

    public JsonSenderTask(String json, String endpointUrl, String httpMethod, String bearerToken) {
        this.json = json;
        this.endpointUrl = endpointUrl;
        this.httpMethod = httpMethod;
        this.bearerToken = bearerToken;
    }

    @Override
    public void run() {

            long startTime = System.currentTimeMillis();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + bearerToken);

            HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);

            ResponseEntity<String> responseEntity = null;

            if (httpMethod.equalsIgnoreCase("POST")) {
                responseEntity = restTemplate.postForEntity(endpointUrl, requestEntity, String.class);
            } else if (httpMethod.equalsIgnoreCase("PUT")) {
                responseEntity = restTemplate.exchange(endpointUrl, HttpMethod.PUT, requestEntity, String.class);
            }

            long endTime = System.currentTimeMillis();

            System.out.println("Thread: " + Thread.currentThread().getName() +
                    " | Processing time: " + (endTime - startTime) + " ms" +
                    " | HttpMethod: " + httpMethod +
                    " | Endpoint URL: " + endpointUrl +
                    " | HTTP Status Code: " + responseEntity.getStatusCode());

    }
}