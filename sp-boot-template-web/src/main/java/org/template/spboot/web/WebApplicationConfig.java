/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.template.spboot.root.interfaces.AnimalInterface;
import org.template.spboot.root.interfaces.SwitchInterface;
import org.template.spboot.web.component.DirectController;
import org.template.spboot.web.component.SimpleController;
import org.template.spboot.web.servlets.StatisticsServlet;

/**
 *
 * @author dnikiforov
 */
@SpringBootApplication
public class WebApplicationConfig implements WebMvcConfigurer {

	@Bean("animal")
	@Primary
	public AnimalInterface animal() {
		return new AnimalInterface() {
			@Override
			public String animalName() {
				return "Dog";
			}
		};
	}

	@Bean("switch")
	@ConditionalOnProperty(name = "context.switch", havingValue = "child")
	public SwitchInterface getSwitch() {
		return new SwitchInterface() {
			@Override
			public String switchName() {
				return "Child switch";
			}
		};
	}

	@Bean
	public ServletRegistrationBean axisServletRegistrationBean() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new StatisticsServlet(), "/statistics/raw");
		return registration;
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(2);
		taskExecutor.setMaxPoolSize(16);
		taskExecutor.setKeepAliveSeconds(0);
		taskExecutor.setQueueCapacity(256);
		taskExecutor.initialize();
		configurer.setTaskExecutor(taskExecutor);
	}

	@Bean("/direct")
	public DirectController directController() {
		return new DirectController();
	}
	
	@Bean("simple")
	public SimpleController simple() {
		return new SimpleController();
	}
	
	@Bean
    public SimpleUrlHandlerMapping urlHandler() {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping
          = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setOrder(Integer.MAX_VALUE - 2);
		
        Properties prop = new Properties();
        prop.put("/simpledirect", "simple");
        simpleUrlHandlerMapping.setMappings(prop);
		
         
        return simpleUrlHandlerMapping;
    }	
	
	
}
