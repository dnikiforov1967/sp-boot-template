/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.jms;

import javax.transaction.Transactional;
import org.easymock.Capture;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import org.easymock.IAnswer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.template.spboot.root.data.model.AppUser;
import org.template.spboot.root.data.resource.AppUserResource;
import org.template.spboot.root.jms.api.JmsApi;
import org.template.spboot.root.jms.model.JmsResult;
import org.template.spboot.root.service.UserApiServiceInterface;

/**
 *
 * @author dima
 */
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@Transactional
public class JmsGeneralTest {
	
	@Configuration
	@ComponentScan("org.template.spboot.root.jms")
	public static class TestConfig {
		
		@Bean("jpaUserApiService")
		public UserApiServiceInterface userApiService() {
			final Capture<AppUser> captureArgs = Capture.newInstance();
			final UserApiServiceInterface mock = EasyMock.mock(UserApiServiceInterface.class);
			expect(mock.create(capture(captureArgs))).andAnswer(new IAnswer<AppUser>() {
				@Override
				public AppUser answer() throws Throwable {
					return captureArgs.getValue();
				}
			}).once();
			expect(mock.create(anyObject())).andReturn(null).times(1, 100);
			replay(mock);
			return mock;
		}				
		
	}
	
	@Autowired
	@Qualifier("jmsSender")
	private JmsApi jmsApi;

	public JmsGeneralTest() {
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
	public void hello() {
		AppUserResource appUserResource = new AppUserResource();
		appUserResource.setUsername("testUser");
		appUserResource.setPassword("testPassword");
		JmsResult result = jmsApi.send(appUserResource);
		assertEquals("testPassword", result.getResource().getPassword());
		result = jmsApi.send(appUserResource);
		assertNull(result.getResource());
	}
}
