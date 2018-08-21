/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.test;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
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
public class FutureTaskTest {

	private static AtomicInteger ai = new AtomicInteger();
	
	public static class InnerCallable implements Callable<Integer> {

		private FutureTask<Integer> ft;

		public void setFt(FutureTask<Integer> ft) {
			this.ft = ft;
		}
		
		@Override
			public Integer call() throws Exception {
				while(!ft.isCancelled()) {
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch(InterruptedException ex) {
						System.out.println("Interrupted");
						ai.set(-1);
						return -1;
					}	
				}
				ai.set(1);
				System.out.println("Cancelled");
				return 1;
			}
			
		}

	
	private static class IntegerFutureTask extends FutureTask<Integer> {
		
		public IntegerFutureTask(Callable<Integer> clbl) {
			super(clbl);
		}

	}
	
	public FutureTaskTest() {
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
	public void taskTestCancellAndInterrupt() throws InterruptedException, ExecutionException {
		InnerCallable ic = new InnerCallable();
		IntegerFutureTask task = new IntegerFutureTask(ic);
		ic.setFt(task);
		final Thread thread = new Thread(task);
		thread.start();
		TimeUnit.SECONDS.sleep(3);
		task.cancel(true);
		TimeUnit.SECONDS.sleep(1);
		try {
			final Integer get = task.get();
		} catch(CancellationException ex) {
			assertEquals(-1,ai.get());
		}	
	}
	
	@Test
	public void taskTestCancell() throws InterruptedException, ExecutionException {
		InnerCallable ic = new InnerCallable();
		IntegerFutureTask task = new IntegerFutureTask(ic);
		ic.setFt(task);
		final Thread thread = new Thread(task);
		thread.start();
		TimeUnit.SECONDS.sleep(3);
		task.cancel(false);
		TimeUnit.SECONDS.sleep(1);
		try {
			final Integer get = task.get();
		} catch(CancellationException ex) {
			assertEquals(1,ai.get());
		}	
	}
	
}
