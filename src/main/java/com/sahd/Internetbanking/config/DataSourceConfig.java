package com.sahd.Internetbanking.config;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

@Configuration
public class DataSourceConfig {

    @Value("${config.file}")
    private String CONFIG_FILE;

    @Bean
    public DataSource getDataSource() {
        try {
            DataSourceCf dsConfig = getDataSourceConfig();
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.url(String.format("jdbc:postgresql://%s/%s", dsConfig.getIp(), dsConfig.getDatabase()));
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
        return mapper.readValue(new File(CONFIG_FILE), DataSourceCf.class);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class DataSourceCf {
        private String ip;
        private String database;
        private String username;
        private String password;
    }

}
