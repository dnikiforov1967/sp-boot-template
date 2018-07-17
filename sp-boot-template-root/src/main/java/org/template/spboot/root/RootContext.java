/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;

/**
 *
 * @author dnikiforov
 */
@SpringBootApplication(
		//exclude = {DataSourceAutoConfiguration.class, JmsAutoConfiguration.class}
)
public class RootContext {

}
