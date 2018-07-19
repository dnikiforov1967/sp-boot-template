/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.web.controller;

import java.util.logging.Logger;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.template.spboot.root.annotation.FromParent;
import org.template.spboot.root.interfaces.AnimalInterface;
import org.template.spboot.root.interfaces.FeedInterface;

/**
 *
 * @author dnikiforov
 */
@RestController
@RequestMapping("/hello")
public class EmptyController {

	private static Logger LOG = Logger.getLogger(EmptyController.class.getName());

	@Autowired
	private ApplicationContext context;

	@Autowired
	private AnimalInterface animalInterface;

	@Autowired
	@Qualifier("animal")
	private AnimalInterface animalByName;

	@Autowired
	@Qualifier("animalParent")
	private AnimalInterface animalParentByName;

	@Autowired
	@Qualifier("parentAlias")
	private AnimalInterface animalParentByAlias;

	@Autowired
	private FeedInterface feed;

	@Autowired
	@Qualifier("parentFeed")
	private FeedInterface parentFeed;

	@Inject
	@FromParent
	private AnimalInterface fromParent;

	@RequestMapping(value = "", method = GET)
	public String get() {
		return "Welcome to test service";
	}

	@RequestMapping(path = "/animal", method = GET)
	public String getAnimal() {
		return animalInterface.animalName();
	}

	@RequestMapping(path = "/animalByName", method = GET)
	public String getAnimalByName() {
		return animalByName.animalName();
	}

	@RequestMapping(path = "/animalParentByName", method = GET)
	public String getAnimalParentByName() {
		return animalParentByName.animalName();
	}

	@RequestMapping(path = "/animalParentByAlias", method = GET)
	public String getAnimalParentByAlias() {
		return animalParentByAlias.animalName();
	}

	@RequestMapping(path = "/animalParentByContext", method = GET)
	public String getAnimalParentByContext() {
		return context.getParent().getBean("animal", AnimalInterface.class).animalName();
	}

	@RequestMapping(path = "/animalFromParent", method = GET)
	public String getAnimalFromParent() {
		return fromParent.animalName();
	}

	@RequestMapping(path = "/feed", method = GET)
	public Integer getFeed() {
		return feed.feed();
	}

	@RequestMapping(path = "/parentFeed", method = GET)
	public Integer getParentFeed() {
		return parentFeed.feed();
	}

}
