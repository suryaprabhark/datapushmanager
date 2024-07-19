package com.cpip.applicationrunner;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class JsonSenderTask implements Runnable {

    private final String json;
    private final String endpointUrl;

    public JsonSenderTask(String json, String endpointUrl) {
        this.json = json;
        this.endpointUrl = endpointUrl;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        //restTemplate.postForObject(endpointUrl, requestEntity, String.class);
        long endTime = System.currentTimeMillis();
        System.out.println("Thread: " + Thread.currentThread().getName() + " | Processing time: " + (endTime - startTime) + " ms");
    }
}