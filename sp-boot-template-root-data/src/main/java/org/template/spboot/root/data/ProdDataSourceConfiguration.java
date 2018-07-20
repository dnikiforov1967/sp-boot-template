/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.data;

import java.sql.SQLException;
import javax.sql.DataSource;
import oracle.jdbc.xa.client.OracleXADataSource;
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
@Profile("prod")
@PropertySource("classpath:/prod.properties")
public class ProdDataSourceConfiguration {

	@Value("${atomikos.datasource.url}")
	private String urlDatabase;
	@Value("${atomikos.datasource.xa-class}")
	private String xaClass;
	@Value("${atomikos.datasource.username}")
	private String username;
	@Value("${atomikos.datasource.password}")
	private String password;

	@Bean
	public DataSource dataSource() throws SQLException {
		OracleXADataSource dataSource = new OracleXADataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setURL(urlDatabase);
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return dataSource;		
	}

}
