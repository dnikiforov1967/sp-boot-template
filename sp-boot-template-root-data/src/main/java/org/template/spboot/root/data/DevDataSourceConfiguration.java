/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.data;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author dnikiforov
 */
@Configuration
@Profile("dev")
@PropertySource("classpath:/dev.properties")
public class DevDataSourceConfiguration {
	
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
		atomikosDataSourceBean.setPoolSize(10);
        return atomikosDataSourceBean;
    }	
	
}
