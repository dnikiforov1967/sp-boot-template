/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.async;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author dnikiforov
 */
@Service
public class TaskService implements TaskInterface {

	@Override
	public String execute() {
		try {
			Thread.sleep(5000);
			return "Task finished";
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}
	
}
