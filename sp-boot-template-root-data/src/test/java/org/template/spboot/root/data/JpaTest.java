/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.data;

import java.util.Set;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.template.spboot.root.data.DatabaseConfiguration;
import org.template.spboot.root.data.DevDataSourceConfiguration;
import org.template.spboot.root.data.jpa.RoleJpaInterface;
import org.template.spboot.root.data.jpa.UserAppJpaInterface;
import org.template.spboot.root.data.model.AppRole;
import org.template.spboot.root.data.model.AppUser;
import static org.template.spboot.root.data.model.RoleEnum.ROLE_ADMIN;
import static org.template.spboot.root.data.model.RoleEnum.ROLE_USER;

/**
 *
 * @author dnikiforov
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@EnableAutoConfiguration
@ContextConfiguration(classes = {DatabaseConfiguration.class, DevDataSourceConfiguration.class})
@Transactional
public class JpaTest {

    
	@Autowired
	private UserAppJpaInterface apiUser;
	@Autowired
	private RoleJpaInterface apiRole;
	@PersistenceContext(unitName = "globalPU")
	private EntityManager em;
	
	public JpaTest() {
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
	public void testCreateUser() {
		AppUser appUser = new AppUser("probe","probe");
		appUser.getAppRoles().add(new AppRole(ROLE_USER));
		appUser.getAppRoles().add(new AppRole(ROLE_ADMIN));
		final AppUser saved = apiUser.saveAndFlush(appUser);
		final Set<AppRole> appRoles = saved.getAppRoles();
		assertFalse(appRoles.isEmpty());
		assertEquals(2, appRoles.size());
		em.clear();
		final AppUser find = em.find(AppUser.class, "probe");
		final Set<AppRole> findAppRoles = find.getAppRoles();
		assertFalse(findAppRoles.isEmpty());
		assertEquals(2, findAppRoles.size());
	}
}
