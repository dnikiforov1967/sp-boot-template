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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.template.spboot.root.annotation.FromParent;
import org.template.spboot.root.beans.SimpleBean;
import org.template.spboot.root.exception.NuclearException;
import org.template.spboot.root.exception.SpecificException;
import org.template.spboot.root.exception.TooBigException;
import org.template.spboot.root.exception.handler.ExternalExceptionHandler;
import org.template.spboot.root.interfaces.AnimalInterface;
import org.template.spboot.root.interfaces.FeedInterface;
import org.template.spboot.root.interfaces.SwitchInterface;

/**
 *
 * @author dnikiforov
 */
@RestController
@RequestMapping("/hello")
public class EmptyController extends ExternalExceptionHandler {

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

	@Autowired
	@Qualifier("switch")
	private SwitchInterface switchInterface;

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

	@RequestMapping(path = "/switch", method = GET)
	public String getSwitch() {
		return switchInterface.switchName();
	}

	// Convert a predefined exception to an HTTP Status code
	@ResponseStatus(value=HttpStatus.CONFLICT,
                  reason="Annigilation")  // 409
	@ExceptionHandler(NuclearException.class)
	public void conflict() {
		// Nothing to do
	}	
	
	
	@RequestMapping(path = "/simplebean/{name}", method = GET)
	public String getSimpleBean(@PathVariable("name") SimpleBean simpleBean) {
		final String name = simpleBean.getName();
		if ("Godsilla".equals(name)) {
			throw new SpecificException();
		} else if ("Anti".equals(name)) {
			throw new NuclearException();
		} else if ("Elephant".equals(name)) {
			throw new TooBigException();
		}
		return name;
	}
	
}
