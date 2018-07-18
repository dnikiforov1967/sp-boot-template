/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.template.spboot.root.interfaces.AnimalInterface;

/**
 *
 * @author dnikiforov
 */
@SpringBootApplication
public class RootContext {

	@Bean(name = {"parentAlias", "animal"})
	public AnimalInterface returnAnimal() {
		return new AnimalInterface() {
			@Override
			public String animalName() {
				return "Cat";
			}
		};
	}

	@Bean("animalParent")
	public AnimalInterface returnAnimalParent() {
		return new AnimalInterface() {
			@Override
			public String animalName() {
				return "Cat";
			}
		};
	}

}
