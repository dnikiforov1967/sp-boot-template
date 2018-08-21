/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.test;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dnikiforov
 */
public class ConcurrentModificationExceptionTest {
	
	public ConcurrentModificationExceptionTest() {
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
	@Test(expected = ConcurrentModificationException.class)
	public void add() {
		final List<Integer> list = new LinkedList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		for(Integer i : list) {
			list.add(7);
		}
	}
	
	@Test(expected = ConcurrentModificationException.class)
	public void remove() {
		final List<Integer> list = new LinkedList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		for(Integer i : list) {
			list.remove(0);
		}
	}	
	
}
