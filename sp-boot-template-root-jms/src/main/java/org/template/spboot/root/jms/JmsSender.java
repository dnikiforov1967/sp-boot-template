/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.jms;

import javax.jms.Destination;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.template.spboot.root.data.resource.AppUserResource;
import org.template.spboot.root.jms.api.JmsApi;
import org.template.spboot.root.jms.model.JmsResult;

/**
 *
 * @author dima
 */
@Component("jmsSender")
public class JmsSender implements JmsApi {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	private Destination destination = new ActiveMQQueue("testQueue");

	@Override
	public JmsResult send(AppUserResource resource) {
		return jmsMessagingTemplate.convertSendAndReceive(destination, resource, JmsResult.class);
	}

	@Override
	public JmsResult receive(AppUserResource resource) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
