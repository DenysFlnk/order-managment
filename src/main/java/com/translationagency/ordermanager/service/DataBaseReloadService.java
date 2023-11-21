package com.translationagency.ordermanager.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Profile("deploy")
@Service
@EnableScheduling
@AllArgsConstructor
@Slf4j
public class DataBaseReloadService {

    private final DataSource dataSource;

    @Scheduled(cron = "0 0 23 * * *")
    public void reloadDBOncePerDay() {
        String initDbClasspath = "/db/init.sql";
        String populateDbClasspath = "/db/populate.sql";
        log.info("reloadDBOncePerDay with scripts {}, {}", initDbClasspath, populateDbClasspath);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource(initDbClasspath), new ClassPathResource(populateDbClasspath));
        populator.execute(dataSource);
    }
}
