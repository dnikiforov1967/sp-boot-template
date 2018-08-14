/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.web.controller;

import java.util.concurrent.Callable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;
import org.template.spboot.root.async.TaskInterface;


/**
 *
 * @author dnikiforov
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

	@Autowired
	private TaskInterface taskService;
	
	@RequestMapping(path = "", method = GET)
	public Callable<String> getSimpleAsync() {
		Callable<String> callable = taskService::execute;
		return callable;
	}
	
}
