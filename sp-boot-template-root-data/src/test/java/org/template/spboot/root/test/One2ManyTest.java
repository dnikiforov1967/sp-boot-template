/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.template.spboot.root.data.model.one2many.Child;
import org.template.spboot.root.data.model.one2many.Child2DJoined;
import org.template.spboot.root.data.model.one2many.Parent;

/**
 *
 * @author dnikiforov
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestDatabaseConfig.class})
@Transactional
public class One2ManyTest {
	
    @PersistenceContext(unitName = "testPU")
    private EntityManager em;	
	
	public One2ManyTest() {
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
	public void controlOverAssociation() {
		Parent parent = new Parent();
		parent.setId(1);
		Child child = new Child();
		child.setId(1);
		
		Child2DJoined child2DJoined = new Child2DJoined();
		child2DJoined.setId(1);
		child2DJoined.setParent(parent);
		
		parent.getChilds().add(child);
		em.persist(parent);
		//We have to persist child also (no cascade operation)
		em.persist(child);
		em.persist(child2DJoined);
		
		em.flush();
	}
}
