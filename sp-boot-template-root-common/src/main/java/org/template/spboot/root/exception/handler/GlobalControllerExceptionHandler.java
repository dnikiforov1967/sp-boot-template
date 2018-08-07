/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.template.spboot.root.annotation.BanzayHandle;
import org.template.spboot.root.exception.BanzayException;

/**
 *
 * @author dnikiforov
 */
@ControllerAdvice(annotations = {BanzayHandle.class})
public class GlobalControllerExceptionHandler {
	
    @ResponseStatus(value=HttpStatus.CONFLICT,reason = "Banzay !")  // 409
    @ExceptionHandler(BanzayException.class)
    public void banzay() {
        // Nothing to do
    }
}