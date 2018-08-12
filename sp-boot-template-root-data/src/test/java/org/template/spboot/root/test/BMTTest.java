/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRED;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.template.spboot.root.data.model.AppRole;
import org.template.spboot.root.data.model.AppUser;
import static org.template.spboot.root.data.model.RoleEnum.ROLE_ADMIN;
import static org.template.spboot.root.data.model.RoleEnum.ROLE_USER;

/**
 *
 * @author dima
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestDatabaseConfig.class})
public class BMTTest {

    @PersistenceContext(unitName = "testPU")
    private EntityManager em;

    @PersistenceUnit(unitName = "testPU")
    private EntityManagerFactory emf;    
    
    @Autowired
    private PlatformTransactionManager txManager;
    
    private TransactionTemplate transactionTemplate ;

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
    public void testEntityManagerInsideTransaction() {
        DefaultTransactionDefinition td = new DefaultTransactionDefinition();
        td.setPropagationBehavior(PROPAGATION_REQUIRED);
        final TransactionStatus transaction = txManager.getTransaction(td);
        em.persist(new AppRole(ROLE_USER));
        em.persist(new AppRole(ROLE_ADMIN));
        em.flush();
        AppUser appUser = new AppUser("probe", "probe");
        appUser.getAppRoles().add(new AppRole(ROLE_USER));
        appUser.getAppRoles().add(new AppRole(ROLE_ADMIN));
        em.persist(appUser);
        em.flush();
        em.clear();
        AppUser find = em.find(AppUser.class, appUser.getUsername());
        assertEquals(find.getPassword(),appUser.getPassword());
        em.clear();
        txManager.rollback(transaction);
        find = em.find(AppUser.class, appUser.getUsername());
        assertNull(find);        
    }
    
    @Test
    public void testEntityManagerFactoryOutsideTransaction() {
        DefaultTransactionDefinition td = new DefaultTransactionDefinition();
        td.setPropagationBehavior(PROPAGATION_REQUIRED);
        final TransactionStatus transaction = txManager.getTransaction(td);
        EntityManager emLocal = emf.createEntityManager();
        emLocal.joinTransaction();
        emLocal.persist(new AppRole(ROLE_USER));
        emLocal.persist(new AppRole(ROLE_ADMIN));
        emLocal.flush();
        AppUser appUser = new AppUser("probe", "probe");
        appUser.getAppRoles().add(new AppRole(ROLE_USER));
        appUser.getAppRoles().add(new AppRole(ROLE_ADMIN));
        emLocal.persist(appUser);
        emLocal.flush();
        emLocal.clear();
        AppUser find = emLocal.find(AppUser.class, appUser.getUsername());
        assertEquals(find.getPassword(),appUser.getPassword());
        emLocal.clear();
        txManager.rollback(transaction);
        find = emLocal.find(AppUser.class, appUser.getUsername());
        assertNull(find);        
    }   
    
    @Test
    public void testTT() {
        transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(txManager);
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus ts) {
                ts.setRollbackOnly();
                return null;
            }
        });
    }    
    
}