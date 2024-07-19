package com.cpip;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.cpip.applicationrunner.CsvToJsonApplicationRunner;

@SpringBootApplication
public class DataPushManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataPushManagerApplication.class, args);
	}
	
	@Value("${parallelism.count}")
    private int parallelismCount;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            CsvToJsonApplicationRunner csvToJsonApplicationRunner = ctx.getBean(CsvToJsonApplicationRunner.class);
            csvToJsonApplicationRunner.run();
        };
    }
 
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(parallelismCount);
        executor.setMaxPoolSize(parallelismCount);
        executor.setQueueCapacity(100);
        executor.initialize();
        return executor;
    }

}
