/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;
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
public class CountDownLatchTest {

	private static final AtomicInteger ai = new AtomicInteger(0);

	private static class Waiter implements Runnable {

		private final CountDownLatch cdl;

		public Waiter(CountDownLatch cdl) {
			this.cdl = cdl;
		}

		@Override
		public void run() {
			try {
				cdl.await();
			} catch (InterruptedException ex) {
				Logger.getLogger(CountDownLatchTest.class.getName()).log(Level.SEVERE, null, ex);
			}
			ai.getAndUpdate((t) -> {
				return t << 2;
			});
		}

	}

	private static class Changer implements Runnable {

		private final CountDownLatch cdl;

		public Changer(CountDownLatch cdl) {
			this.cdl = cdl;
		}

		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException ex) {
				Logger.getLogger(CountDownLatchTest.class.getName()).log(Level.SEVERE, null, ex);
			}
			ai.incrementAndGet();
			cdl.countDown();

		}

	}

	public CountDownLatchTest() {
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
	public void hello() throws InterruptedException {

		CountDownLatch cdl = new CountDownLatch(3);
		final ExecutorService pool = Executors.newFixedThreadPool(3);
		final Thread t = new Thread(new Waiter(cdl));
		t.start();
		for (int i = 0; i < 3; i++) {
			pool.submit(new Changer(cdl));
		}
		t.join();
		assertEquals(12, ai.get());
	}
}
