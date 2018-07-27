/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author dima
 */
@Controller
@RequestMapping("/views")
public class ViewsRestController {

    @RequestMapping("/modelview")
    public ModelAndView getModelView() {
        System.out.println("MODEL AND VIEW");
        final ModelAndView modelAndView = new ModelAndView("simpleView");
        modelAndView.addObject("name", "Dima");
        return modelAndView;
    }

}
