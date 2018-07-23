/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.template.spboot.root.interfaces.AnimalInterface;
import org.template.spboot.root.interfaces.SwitchInterface;

/**
 *
 * @author dnikiforov
 */
@SpringBootApplication
public class WebApplicationConfig {

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

}
