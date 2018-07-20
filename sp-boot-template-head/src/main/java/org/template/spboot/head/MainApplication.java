/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.head;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.template.spboot.root.RootContext;
import org.template.spboot.web.WebApplicationConfig;

/**
 *
 * @author dnikiforov
 */
public class MainApplication {
	
	public static void main(String... args) {
		final ConfigurableApplicationContext run = new SpringApplicationBuilder()
				.parent(RootContext.class)
				.web(WebApplicationType.NONE)
				.profiles("prod")
				.child(WebApplicationConfig.class)
				.web(WebApplicationType.SERVLET)
				.profiles("prod")
				.run(args);
	}
	
}
