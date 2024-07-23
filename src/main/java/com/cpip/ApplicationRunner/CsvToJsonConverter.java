package com.cpip.ApplicationRunner;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CsvToJsonConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void convertCsvToJson(FileSystemResource file, ThreadPoolExecutor taskExecutor, String endpointUrl, String httpMethod, String bearerToken) throws Exception {
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new InputStreamReader(file.getInputStream()));

        long recordCount = 0;
        for (CSVRecord record : parser) {
            Map<String, Object> recordMap = new HashMap<>();
            for (String header : parser.getHeaderNames()) {
                String[] nestedKeys = header.split("\\.");
                Map<String, Object> currentMap = recordMap;
                for (int i = 0; i < nestedKeys.length - 1; i++) {
                    currentMap.computeIfAbsent(nestedKeys[i], k -> new HashMap<>());
                    currentMap = (Map<String, Object>) currentMap.get(nestedKeys[i]);
                }
                currentMap.put(nestedKeys[nestedKeys.length - 1], record.get(header));
            }
            String json = objectMapper.writeValueAsString(recordMap);
            System.out.println(json);
            taskExecutor.execute(new JsonSenderTask(json, endpointUrl, httpMethod, bearerToken));


            recordCount++;
        }

        // Log the total number of records processed
        System.out.println("Total number of records processed: " + recordCount);
    }
}
