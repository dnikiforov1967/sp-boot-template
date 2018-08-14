/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.async;

import org.springframework.stereotype.Service;

/**
 *
 * @author dnikiforov
 */
@Service(value = "taskServiceEx")
public class TaskServiceWithException implements TaskInterface {

	@Override
	public String execute() {
		try {
			Thread.sleep(5000);
			if (1==1) {
				throw new ArithmeticException("NaN is detected");
			}
			return "Task finished";
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}
	
}