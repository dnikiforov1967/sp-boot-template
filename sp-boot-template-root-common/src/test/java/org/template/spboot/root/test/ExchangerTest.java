/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dima
 */
public class ExchangerTest {
    
    private static final AtomicInteger ai = new AtomicInteger();
    
    private static class RunInside implements Runnable {
        
        private final Exchanger<Integer> exch;

        public RunInside(Exchanger<Integer> exch) {
            this.exch = exch;
        }

        @Override
        public void run() {
            try {
                final Integer exchange = exch.exchange(2);
                ai.addAndGet(exchange);
            } catch (InterruptedException ex) {
                Logger.getLogger(SyncronousQueueTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    
    }
    
    public ExchangerTest() {
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
    public void testExchange() throws InterruptedException {
        Exchanger<Integer> exch = new Exchanger<>();
        final Thread thread = new Thread(new RunInside(exch));
        thread.start();
        TimeUnit.SECONDS.sleep(3);
        assertEquals(0, ai.get());
        exch.exchange(11);
        TimeUnit.SECONDS.sleep(1);
        assertEquals(11, ai.get());
    }
}