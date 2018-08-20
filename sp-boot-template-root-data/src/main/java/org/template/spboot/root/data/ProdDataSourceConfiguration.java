/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import oracle.jdbc.xa.client.OracleXADataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.template.spboot.root.data.jpa.StorageProcedureInitInterface;

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
		//Remark: UCP does not work in version 11.2.0.4 (!!)
		OracleXADataSource dataSource = new OracleXADataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setURL(urlDatabase);
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return dataSource;		
	}
	
	@Bean
	public StorageProcedureInitInterface getSTP(DataSource dataSource) {
		return new StorageProcedureInitInterface() {
			@Override
			public void init() {
				try(final Connection conn = dataSource.getConnection();
						final Statement stmt = conn.createStatement();
						) {
					stmt.execute("create or replace procedure calc_prc"
							+ "(x INTEGER, y INTEGER, z OUT INTEGER)"
							+ " as "
							+ " begin z := 2*(x + y); end;"
					);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		};
	}

}
