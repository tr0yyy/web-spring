package com.fmi.springweb.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("Production")
public class DataSourceConfig {
    Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
    private String databaseUrl = System.getenv("DATABASE_URL");
    private String mysqlUser = System.getenv("MYSQL_USER");
    private String mysqlPassword = System.getenv("MYSQL_PASSWORD");
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        logger.info("Connection to database using followng url: " + databaseUrl);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(mysqlUser);
        dataSource.setPassword(mysqlPassword);
        return dataSource;
    }

}
