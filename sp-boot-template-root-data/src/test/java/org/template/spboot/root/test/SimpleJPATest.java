/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.test;

import java.util.Map;
import java.util.Set;
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
import org.template.spboot.root.data.model.Address;
import org.template.spboot.root.data.model.AppRole;
import org.template.spboot.root.data.model.AppUser;
import org.template.spboot.root.data.model.Person;
import static org.template.spboot.root.data.model.RoleEnum.ROLE_ADMIN;
import static org.template.spboot.root.data.model.RoleEnum.ROLE_USER;
import org.template.spboot.root.data.model.Shape;
import org.template.spboot.root.data.model.ShapeType;

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
		shape.getNames().add(ShapeType.SPHERE);
		shape.getNames().add(ShapeType.SPHEROID);
        em.persist(shape);
        em.flush();
        em.clear();
        final Shape find = em.find(Shape.class, shape.getId());
		assertEquals(find.getNames().size(),2);
        assertEquals(find.getNames(),find.getNames());
        em.clear();
    }
    
    @Test
    public void testAddress() {
        Address address = new Address();
        address.setId(1L);
        address.setCity("Moscow");
        address.setStreat("Tverskaya 10");
        
        Person person = new Person();
        person.setName("Vasya");
        person.getAddresses().put("Moscow", address);
        address.setPerson(person);
        
        em.persist(person);

        em.flush();
        em.clear();
        
        final Person find = em.find(Person.class, "Vasya");
        final Set<Map.Entry<String, Address>> entrySet = find.getAddresses().entrySet();
        assertEquals(1, entrySet.size());
        assertEquals("Tverskaya 10", entrySet.iterator().next().getValue().getStreat());
        
    }
    
}
