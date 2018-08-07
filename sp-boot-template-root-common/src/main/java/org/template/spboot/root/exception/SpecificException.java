/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author dnikiforov
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="This name is not allowed")  // 404
public class SpecificException extends RuntimeException {
	
}
