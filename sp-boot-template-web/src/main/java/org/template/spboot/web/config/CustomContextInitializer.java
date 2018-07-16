/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.web.config;

import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author dnikiforov
 */
@Configuration
public class CustomContextInitializer implements ServletContextInitializer {

	private static final Logger LOG = Logger.getLogger(CustomContextInitializer.class.getName());

	@Override
	public void onStartup(ServletContext sc) throws ServletException {
		sc.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
		LOG.log(Level.WARNING, "Setup COOKIE tracking");
	}

}
