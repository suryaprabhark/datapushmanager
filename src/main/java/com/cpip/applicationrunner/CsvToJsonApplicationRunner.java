package com.cpip.applicationrunner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class CsvToJsonApplicationRunner {

    @Value("${data.endpoint.url}")
    private String endpointUrl;

    @Value("${csv.filepath}")
    private String csvFilePath;

    private final CsvToJsonConverter csvToJsonConverter;
    private final ThreadPoolTaskExecutor taskExecutor;

    public CsvToJsonApplicationRunner(CsvToJsonConverter csvToJsonConverter, ThreadPoolTaskExecutor taskExecutor) {
        this.csvToJsonConverter = csvToJsonConverter;
        this.taskExecutor = taskExecutor;
    }

    public void run() throws Exception {
        FileSystemResource csvFile = new FileSystemResource(csvFilePath);
        csvToJsonConverter.convertCsvToJson(csvFile, taskExecutor, endpointUrl);
    }
}