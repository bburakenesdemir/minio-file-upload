package com.burakenesdemir.minio.configuration;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {


    @Bean
    public MinioClient generateMinioClient() {
        try {
            MinioClient client = new MinioClient("http://******", "****", "*****");
            return client;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}
