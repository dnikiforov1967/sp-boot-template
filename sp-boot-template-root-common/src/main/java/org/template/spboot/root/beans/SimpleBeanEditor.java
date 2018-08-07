/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.beans;

import java.beans.PropertyEditorSupport;

/**
 *
 * @author dima
 */
public class SimpleBeanEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text==null) {
            this.setValue(null);
        } else {
            SimpleBean simpleBean = new SimpleBean();
            simpleBean.setName(text);
            setValue(simpleBean);
        }
    }

    @Override
    public String getAsText() {
        SimpleBean simpleBean = (SimpleBean)getValue();
        return simpleBean.getName();
    }
    
    
    
}
