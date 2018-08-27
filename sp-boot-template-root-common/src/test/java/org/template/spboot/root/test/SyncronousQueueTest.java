/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.test;

import java.util.concurrent.SynchronousQueue;
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
public class SyncronousQueueTest {
    
    private static final AtomicInteger ai = new AtomicInteger();
    
    private static class RunInside implements Runnable {
        
        private final SynchronousQueue<Integer> queue;

        public RunInside(SynchronousQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                queue.put(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(SyncronousQueueTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            ai.incrementAndGet();
        }
    
    }
    
    public SyncronousQueueTest() {
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
    public void testQueue() throws InterruptedException {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        final Thread thread = new Thread(new RunInside(queue));
        thread.start();
        TimeUnit.SECONDS.sleep(3);
        assertEquals(0, ai.get());
        queue.poll();
        TimeUnit.SECONDS.sleep(1);
        assertEquals(1, ai.get());
    }
}
