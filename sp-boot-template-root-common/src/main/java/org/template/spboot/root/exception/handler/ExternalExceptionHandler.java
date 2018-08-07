/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.template.spboot.root.exception.TooBigException;

/**
 *
 * @author dnikiforov
 */
public class ExternalExceptionHandler {
	// Convert a predefined exception to an HTTP Status code
	@ResponseStatus(value=HttpStatus.INSUFFICIENT_STORAGE,
                  reason="Elephant !!!")
	@ExceptionHandler(TooBigException.class)
	public final void elephant() {
		// Nothing to do
	}		
}
