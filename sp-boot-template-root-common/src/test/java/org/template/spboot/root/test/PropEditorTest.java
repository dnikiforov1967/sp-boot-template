/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.test;

import java.beans.PropertyEditorSupport;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.StringUtils;

class CreditCard {
	
	private Class<?> clazz;

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

}

class CreditCardEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isEmpty(text)) {
            setValue(null);
        } else {
			try {
				CreditCard credCard = new CreditCard();
				final Class<?> forName = Class.forName(text);
				credCard.setClazz(forName);
				setValue(credCard);
			} catch (ClassNotFoundException ex) {
				throw new IllegalArgumentException(ex);
			}
		}
	}

	@Override
	public String getAsText() {
		CreditCard credCard = (CreditCard)this.getValue();
		return credCard.getClazz().getCanonicalName();		
	}
	
}
 

/**
 *
 * @author dnikiforov
 */
public class PropEditorTest {
	
	public PropEditorTest() {
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
	public void hello() {
		final CreditCardEditor creditCardEditor = new CreditCardEditor();
	
		creditCardEditor.setAsText("java.lang.String");
		final CreditCard card = (CreditCard)creditCardEditor.getValue();
		assertEquals(card.getClazz(),String.class);
		
	
	}
}
