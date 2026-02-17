package com.example.video_segmenter_api.infrastructure.orchestrator.persistence;

import com.example.video_segmenter_api.infrastructure.orchestrator.persistence.sqlc.Queries;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class SqlcConfiguration {

    @Bean
    public Queries queries(DataSource dataSource) {
        return new Queries((Connection) dataSource);
    }
}