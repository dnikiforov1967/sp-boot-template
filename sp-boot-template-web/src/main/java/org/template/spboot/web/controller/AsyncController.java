/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.web.controller;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
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

	@RequestMapping(path = "/def", method = GET)
	public DeferredResult<String> getSimpleAsyncDef() {
		DeferredResult<String> dr = new DeferredResult<>();
		final CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(taskService::execute);
		supplyAsync.whenCompleteAsync((result, throwable) -> dr.setResult(result));
		return dr;
	}
	
}
