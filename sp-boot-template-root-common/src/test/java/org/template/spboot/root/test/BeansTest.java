/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.test;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Enumeration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.template.spboot.root.beans.SimpleBean;

/**
 *
 * @author dima
 */
public class BeansTest {
    
    public BeansTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() throws IntrospectionException {
        final BeanInfo beanInfo = Introspector.getBeanInfo(SimpleBean.class);
        final BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
        final Enumeration<String> attributeNames = beanDescriptor.attributeNames();
        final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for(PropertyDescriptor pd : propertyDescriptors) {
            System.out.println("desc "+pd.getReadMethod().getName());
        }
    }
}
