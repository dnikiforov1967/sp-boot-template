/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author dima
 */
@Controller
@RequestMapping("/views")
public class ViewsRestController {

	@RequestMapping("/modelview")
	public ModelAndView getModelView(@RequestParam(value = "name", required = false, defaultValue = "Dmitry") String name) {
		final ModelAndView modelAndView = new ModelAndView("simpleView");
		modelAndView.addObject("name", name);
		return modelAndView;
	}

}
