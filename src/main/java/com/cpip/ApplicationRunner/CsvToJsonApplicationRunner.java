package com.cpip.ApplicationRunner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class CsvToJsonApplicationRunner {

    @Value("${data.endpoint.url}")
    private String endpointUrl;

    @Value("${data.endpoint.method}")
    private String httpMethod;

    @Value("${parallelism.count}")
    private int parallelismCount;

    @Value("${data.endpoint.bearerToken}")
    private String bearerToken;

    private final CsvToJsonConverter csvToJsonConverter;

    public CsvToJsonApplicationRunner(CsvToJsonConverter csvToJsonConverter) {
        this.csvToJsonConverter = csvToJsonConverter;
    }

    public void run(String csvFilePath) throws Exception {
        ExecutorService taskExecutor = Executors.newFixedThreadPool(parallelismCount);
        FileSystemResource csvFile = new FileSystemResource(csvFilePath);
        csvToJsonConverter.convertCsvToJson(csvFile, (ThreadPoolExecutor) taskExecutor, endpointUrl, httpMethod, bearerToken);
        taskExecutor.awaitTermination(5, TimeUnit.MINUTES);
        taskExecutor.shutdown();
    }
}
