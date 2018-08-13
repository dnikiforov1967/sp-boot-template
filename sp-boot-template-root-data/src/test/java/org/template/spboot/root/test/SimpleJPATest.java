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
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.template.spboot.root.data.model.AppRole;
import org.template.spboot.root.data.model.AppUser;
import static org.template.spboot.root.data.model.RoleEnum.ROLE_ADMIN;
import static org.template.spboot.root.data.model.RoleEnum.ROLE_USER;
import org.template.spboot.root.data.model.Shape;

/**
 *
 * @author dima
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestDatabaseConfig.class})
@Transactional
public class SimpleJPATest {

    @PersistenceContext(unitName = "testPU")
    private EntityManager em;

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
        em.persist(new AppRole(ROLE_USER));
        em.persist(new AppRole(ROLE_ADMIN));
        em.flush();
        AppUser appUser = new AppUser("probe", "probe");
        appUser.getAppRoles().add(new AppRole(ROLE_USER));
        appUser.getAppRoles().add(new AppRole(ROLE_ADMIN));
        em.persist(appUser);
        em.flush();
        em.clear();
        final AppUser find = em.find(AppUser.class, appUser.getUsername());
        assertEquals(find.getPassword(),appUser.getPassword());
        em.clear();
    }
	
	
    @Test
    public void testShape() {
        Shape shape = new Shape();
		shape.setId(1L);
		shape.getNames().add("Sphere");
		shape.getNames().add("Shar");
        em.persist(shape);
        em.flush();
        em.clear();
        final Shape find = em.find(Shape.class, shape.getId());
		assertEquals(find.getNames().size(),2);
        assertEquals(find.getNames(),find.getNames());
        em.clear();
    }	
}
