/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
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
public class SemaphorTest {
    
    private static AtomicInteger ai = new AtomicInteger();
    
    private static class TestCallable implements Callable<Integer> {

        private final Semaphore sem;

        public TestCallable(Semaphore sem) {
            this.sem = sem;
        }
        
        @Override
        public Integer call() throws Exception {
            final boolean tryAcquire = sem.tryAcquire();
            try {
                if (tryAcquire) {
                    ai.incrementAndGet();
                    TimeUnit.SECONDS.sleep(4);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(SemaphorTest.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (tryAcquire) {
                    sem.release();
                }
            }
            return 0;
        }
    }
    
    
    public SemaphorTest() {
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
    public void hello() throws InterruptedException, ExecutionException {
    
        Semaphore semaphore = new Semaphore(3);
        final ExecutorService fixedPool = Executors.newFixedThreadPool(4);
        List<Callable<Integer>> list = Arrays.asList(new TestCallable(semaphore),
                new TestCallable(semaphore),
                new TestCallable(semaphore),
                new TestCallable(semaphore));
        final List<Future<Integer>> invokeAll = fixedPool.invokeAll(list);
        for(Future<Integer> fut : invokeAll) {
            fut.get();
        }
        assertEquals(3, ai.get());
    }
}
