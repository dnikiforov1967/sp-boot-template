/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.template.spboot.root.interfaces.AnimalInterface;

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
}
