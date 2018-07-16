/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.web.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *
 * @author dnikiforov
 */
@RestController
@RequestMapping("/hello")
public class EmptyController {

	@RequestMapping(value = "", method = GET)
	public String get() {
		return "Welcome to test service";
	}

}
