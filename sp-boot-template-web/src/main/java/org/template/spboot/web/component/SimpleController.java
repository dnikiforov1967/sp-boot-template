/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.web.component;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author dnikiforov
 */
public class SimpleController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Logger.getLogger(SimpleController.class.getName()).log(Level.WARNING, "Simple controller called");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("direct");
		mav.addObject("greeting", "Hello from simple direct mvc controller");
		return mav;
	}

}
