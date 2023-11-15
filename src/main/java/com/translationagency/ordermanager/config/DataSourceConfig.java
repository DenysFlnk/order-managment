package com.translationagency.ordermanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
public class DataSourceConfig {
    @Profile("deploy")
    @Bean
    public DataSource dataSourceForDeploy(@Value("${spring.datasource.url}") String datasourceUrl,
                                          @Value("${spring.datasource.username}") String databaseUsername,
                                          @Value("${spring.datasource.password}") String databasePassword) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(datasourceUrl);
        dataSource.setUsername(databaseUsername);
        dataSource.setPassword(databasePassword);
        return dataSource;
    }

    @Profile("dev")
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/translation_agency");
        dataSource.setUsername("postgres");
        dataSource.setPassword("userPassword");
        return dataSource;
    }

    @Profile({"test", "loginTest"})
    @Bean
    public DataSource dataSourceForTests() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("test.sql")
                .build();
    }
}
