/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.component;

import org.springframework.stereotype.Component;
import org.template.spboot.root.interfaces.FeedInterface;

/**
 *
 * @author dnikiforov
 */
@Component
public class Feed implements FeedInterface {

	@Override
	public int feed() {
		return 1;
	}

}
