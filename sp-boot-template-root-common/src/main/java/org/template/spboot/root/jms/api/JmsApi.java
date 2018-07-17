/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.jms.api;

import org.template.spboot.root.data.resource.AppUserResource;
import org.template.spboot.root.jms.model.JmsResult;

/**
 *
 * @author dima
 */
public interface JmsApi {

	JmsResult send(AppUserResource resource);

	JmsResult receive(AppUserResource resource);

}
