package com.cpip;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import com.cpip.ApplicationRunner.CsvToJsonApplicationRunner;

@SpringBootApplication
public class DataPushManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataPushManagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            if (args.length > 0) {
                String csvFilePath = args[0];
                CsvToJsonApplicationRunner csvToJsonApplicationRunner = ctx.getBean(CsvToJsonApplicationRunner.class);
                csvToJsonApplicationRunner.run(csvFilePath);
            } else {
                System.err.println("No CSV file path provided!");
            }
        };
    }


}
