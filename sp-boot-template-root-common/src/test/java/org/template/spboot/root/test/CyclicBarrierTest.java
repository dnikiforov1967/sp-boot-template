/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
 * @author dnikiforov
 */
public class CyclicBarrierTest {

	private static final AtomicInteger ai = new AtomicInteger(0);

	private static class PostRunner implements Runnable {

		@Override
		public void run() {
			ai.getAndUpdate((t) -> {
				return t << 2;
			});
		}

	}

	private static class Changer implements Runnable {

		private final CyclicBarrier cb;
		private Thread thread;

		public Changer(CyclicBarrier cb) {
			this.cb = cb;
		}
		
		public void interrupt() {
			System.out.println("Interrupt "+thread.getName());
			thread.interrupt();
		}

		@Override
		public void run() {
			thread = Thread.currentThread();
			ai.incrementAndGet();
			try {
				cb.await();
			} catch (InterruptedException ex) {
				Logger.getLogger(CyclicBarrierTest.class.getName()).log(Level.SEVERE, null, ex);
			} catch (BrokenBarrierException ex) {
				Logger.getLogger(CyclicBarrierTest.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}
        
	private static class Changer2 implements Runnable {

		private final CyclicBarrier cb;
		private Thread thread;

		public Changer2(CyclicBarrier cb) {
			this.cb = cb;
		}
		
		public void interrupt() {
			System.out.println("Interrupt "+thread.getName());
			thread.interrupt();
		}

		@Override
		public void run() {
			thread = Thread.currentThread();
			ai.incrementAndGet();
			try {
				cb.await();
			} catch (InterruptedException ex) {
				Logger.getLogger(CyclicBarrierTest.class.getName()).log(Level.SEVERE, null, ex);
			} catch (BrokenBarrierException ex) {
				Logger.getLogger(CyclicBarrierTest.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}        
	
	public CyclicBarrierTest() {
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
	public void cyclicBarrierRepeatableUse() throws InterruptedException, BrokenBarrierException {

		ai.set(0);
		CyclicBarrier cb = new CyclicBarrier(3, new PostRunner());
		final ExecutorService pool = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 2; i++) {
			pool.submit(new Changer(cb));
		}
		//Main thread also should wait for tirring
		TimeUnit.SECONDS.sleep(4);
		cb.await();
		assertEquals(8, ai.get());
		//Repeatable use
		cb.reset();
		for (int i = 0; i < 2; i++) {
			pool.submit(new Changer(cb));
		}
		cb.await();	
		assertEquals(40, ai.get());
	}
	
	
	@Test(expected = BrokenBarrierException.class)
	public void cyclicBarrierAll_Or_None() throws InterruptedException, BrokenBarrierException {

		ai.set(0);
		CyclicBarrier cb = new CyclicBarrier(3, new PostRunner());
		final ExecutorService pool = Executors.newFixedThreadPool(3);
		//Start just one thread
		final Changer changer = new Changer(cb);
		pool.submit(changer);
		System.out.println("Main thread "+Thread.currentThread().getName());

		//Start breaking thread with 5 seconds timeout
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(4);
				} catch (InterruptedException ex) {
					Logger.getLogger(CyclicBarrierTest.class.getName()).log(Level.SEVERE, null, ex);
				}
				changer.interrupt();
			}
		}).start(); 

		//Main thread also should wait for tirring -gets Interrupted exception 
		cb.await();
	}
        
        
	@Test
	public void cyclicBarrierTHreadsToComplete() throws InterruptedException, BrokenBarrierException {

		ai.set(0);
		CyclicBarrier cb = new CyclicBarrier(3, new PostRunner());
		for (int i = 0; i < 2; i++) {
			new Thread(new Changer(cb)).start();
		}
		//Main thread also should wait for tirring
		TimeUnit.SECONDS.sleep(4);
		cb.await();
                TimeUnit.SECONDS.sleep(2);
                ai.incrementAndGet();
                TimeUnit.SECONDS.sleep(2);
		assertEquals(9, ai.get());
	}        
	
}
