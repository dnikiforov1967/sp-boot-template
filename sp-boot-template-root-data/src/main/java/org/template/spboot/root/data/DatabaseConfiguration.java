/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.data;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.template.spboot.root.data.model.AppUser;

/**
 *
 * @author dima
 */
@Configuration
@PropertySource("classpath:/data.properties")
@EnableTransactionManagement
public class DatabaseConfiguration {

    @Value("${atomikos.datasource.url}")
    private String urlDatabase;
    @Value("${atomikos.datasource.xa-class}")
    private String xaClass;
    @Value("${atomikos.datasource.username}")
    private String username;
    @Value("${atomikos.datasource.password}")
    private String password;     
    

    @Bean
    public DataSource dataSource() {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSourceClassName(xaClass);
        Properties properties = new Properties();
        properties.put("user", username);
        properties.put("password", password);
        properties.put("url", urlDatabase);
        atomikosDataSourceBean.setXaProperties(properties);
        atomikosDataSourceBean.setUniqueResourceName("mainDataSourceResource");
        return atomikosDataSourceBean;
    }

    @Bean
    @DependsOn({"transactionManager"})
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            DataSource dataSource) {
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.schema-generation.database.action", "drop-and-create");
        properties.put("hibernate.transaction.jta.platform", "org.template.spboot.root.data.CustomJtaPlatform");
        return builder
                .dataSource(dataSource)
                .jta(true)
                .persistenceUnit("globalPU")
                .packages(AppUser.class)
                .properties(properties)
                .build();
    }

    @Bean(name = "userTransaction")
    public UserTransaction userTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(300);
        return userTransactionImp;
    }

    @Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
    public TransactionManager atomikosTransactionManager() throws Throwable {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    @Bean(name = "transactionManager")
    @DependsOn({"userTransaction", "atomikosTransactionManager"})
    public PlatformTransactionManager transactionManager(UserTransaction userTransaction, TransactionManager atomikosTransactionManager) throws Throwable {
        CustomJtaPlatform.setUserTransaction(userTransaction);
        CustomJtaPlatform.setTransactionManager(atomikosTransactionManager);
        return new JtaTransactionManager(userTransaction, atomikosTransactionManager);
    }

}
