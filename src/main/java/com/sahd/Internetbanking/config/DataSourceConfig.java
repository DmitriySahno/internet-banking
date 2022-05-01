package com.sahd.Internetbanking.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class DataSourceConfig {

    @Value("${config.file}")
    private String configFilePath;

    @Bean
    public DataSource getDataSource() {
        try {
            DataSourceCf dsConfig = getDataSourceConfig();
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.url(String.format("%s://%s/%s", dsConfig.getDatabaseVendor(), dsConfig.getIp(), dsConfig.getDatabaseName()));
            dataSourceBuilder.username(dsConfig.getUsername());
            dataSourceBuilder.password(dsConfig.getPassword());
            return dataSourceBuilder.build();
        } catch (IOException e) {
            System.err.println("Error with reading configuration file: " + e.getMessage());
            return null;
        }
    }

    private DataSourceCf getDataSourceConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Path configFile = Path.of(configFilePath);
        try (BufferedReader reader = Files.newBufferedReader(configFile)) {
            return mapper.readValue(reader, DataSourceCf.class);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class DataSourceCf {
        private String ip;
        private String databaseVendor;
        private String databaseName;
        private String username;
        private String password;
    }

}
