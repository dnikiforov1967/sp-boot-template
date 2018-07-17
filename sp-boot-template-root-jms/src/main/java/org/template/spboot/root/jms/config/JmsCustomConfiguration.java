/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.jms.config;

import javax.jms.ConnectionFactory;
import javax.jms.Session;
import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

/**
 *
 * @author dima
 */
@Configuration
@EnableJms
public class JmsCustomConfiguration {

    private ConnectionFactory connectionFactory() {
        ActiveMQXAConnectionFactory factory = new ActiveMQXAConnectionFactory("vm://localhost");
        factory.setTrustAllPackages(true);
        return factory;
    }

    private JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setExplicitQosEnabled(true);
        template.setDeliveryPersistent(false);
        template.setReceiveTimeout(1000);
        template.setTimeToLive(60000);
        template.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        return template;
    }

    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate() {
        final JmsMessagingTemplate jmsMessagingTemplate = new JmsMessagingTemplate();
        jmsMessagingTemplate.setJmsTemplate(jmsTemplate());
        return jmsMessagingTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory custimizedFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setPubSubDomain(false);
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setConcurrency("1");
        return factory;
    }

}
