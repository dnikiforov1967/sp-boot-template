/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.test;

import javax.sql.DataSource;
import javax.persistence.EntityManagerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author dima
 */
@TestConfiguration
@Profile("test")
@EnableTransactionManagement
public class TestDatabaseConfig {
    
    @Bean
    public DataSource dataSource() {
        final DataSource build = DataSourceBuilder
                .create()
                .driverClassName("org.h2.Driver")
                .password("sa")
                .username("sa")
                .url("jdbc:h2:mem:todo;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                .build();
        return build;
    }
    
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {
        LocalContainerEntityManagerFactoryBean bean = 
                new LocalContainerEntityManagerFactoryBean();
        bean.setPersistenceUnitName("testPU");
        bean.setDataSource(ds);
        return bean;
    }
    
    @Bean
    PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
    
}
