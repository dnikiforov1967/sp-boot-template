/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.service;

import org.easymock.Capture;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.eq;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.template.spboot.root.data.model.AppRole;
import org.template.spboot.root.data.model.AppUser;
import org.template.spboot.root.data.model.RoleEnum;

/**
 *
 * @author dnikiforov
 */
@RunWith(SpringRunner.class)
public class AppUserServiceTest {

	private static AppUser appUserA;
	private static AppUser appUserB;

	@Configuration
	public static class TestConfiguration {

		@Bean
		public UserApiServiceInterface userApiServiceInterface() {
			Capture<AppUser> capturedArgument = Capture.newInstance();
			final UserApiServiceInterface mock = EasyMock.mock(UserApiServiceInterface.class);

			EasyMock.expect(mock.create(capture(capturedArgument))).andAnswer(
					new IAnswer<AppUser>() {
				@Override
				public AppUser answer() throws Throwable {
					return capturedArgument.getValue();
				}
			}
			).anyTimes();

			EasyMock.expect(mock.update(capture(capturedArgument))).andAnswer(
					new IAnswer<AppUser>() {
				@Override
				public AppUser answer() throws Throwable {
					return capturedArgument.getValue();
				}
			}
			).anyTimes();

			EasyMock.expect(mock.findByName(eq("dima"))).andReturn(appUserB).anyTimes();
			replay(mock);
			return mock;
		}

	}

	@Autowired
	private UserApiServiceInterface userApiService;

	public AppUserServiceTest() {
	}

	@BeforeClass
	public static void setUpClass() {
		appUserA = new AppUser("dima", "dima1");
		appUserA.getAppRoles().add(new AppRole(RoleEnum.ROLE_USER));
		appUserB = new AppUser("dima", "dima2");
		appUserB.getAppRoles().add(new AppRole(RoleEnum.ROLE_USER));
		appUserB.getAppRoles().add(new AppRole(RoleEnum.ROLE_ADMIN));
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

	@Test
	public void testCreate() {
		final AppUser created = userApiService.create(appUserA);
		assertEquals(1, created.getAppRoles().size());
	}

	@Test()
	public void testCreateAndUpdateWithRoles() {
		AppUser appUser = userApiService.create(appUserA);
		assertEquals(1, appUser.getAppRoles().size());
		appUser = userApiService.update(appUserB);
		assertEquals(2, appUser.getAppRoles().size());
		final AppUser foundByName = userApiService.findByName("dima");
		assertEquals(2, foundByName.getAppRoles().size());
	}

}
