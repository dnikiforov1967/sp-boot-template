/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.template.spboot.root.annotation.FromParent;
import org.template.spboot.root.interfaces.AnimalInterface;
import org.template.spboot.root.interfaces.FeedInterface;

/**
 *
 * @author dnikiforov
 */
@SpringBootApplication
public class RootContext {

	@Autowired
	@Qualifier("feed")
	private FeedInterface feed;

	@Bean(name = {"parentAlias", "animal"})
	@FromParent
	public AnimalInterface returnAnimal() {
		return new AnimalInterface() {
			@Override
			public String animalName() {
				return "Cat 1";
			}
		};
	}

	@Bean("animalParent")
	public AnimalInterface returnAnimalParent() {
		return new AnimalInterface() {
			@Override
			public String animalName() {
				return "Cat 2";
			}
		};
	}

	@Bean("parentFeed")
	public FeedInterface feed() {
		return feed;
	}

}
