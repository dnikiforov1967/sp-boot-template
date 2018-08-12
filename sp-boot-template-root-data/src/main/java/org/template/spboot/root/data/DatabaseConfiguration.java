/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.data;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.template.spboot.root.data.model.AppUser;

/**
 *
 * @author dima
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {

    @Bean
    @DependsOn({"transactionManager"})
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource) {
        Map<String, String> pMap = new HashMap<>();
        pMap.put("javax.persistence.schema-generation.database.action", "drop-and-create");
        pMap.put("hibernate.transaction.jta.platform", "org.template.spboot.root.data.CustomJtaPlatform");
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        bean.setJtaDataSource(dataSource);
        bean.setPackagesToScan(AppUser.class.getPackage().getName());
        bean.setPersistenceUnitName("globalPU");
        final Properties properties = new Properties();
        properties.putAll(pMap);
        bean.setJpaProperties(properties);
        return bean;
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
