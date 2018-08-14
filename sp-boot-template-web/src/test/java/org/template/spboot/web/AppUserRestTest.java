/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.web;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.template.spboot.root.annotation.FromParent;
import org.template.spboot.root.async.TaskInterface;
import org.template.spboot.root.common.CommonHelper;
import org.template.spboot.root.data.model.AppRole;
import org.template.spboot.root.data.model.AppUser;
import org.template.spboot.root.data.model.RoleEnum;
import org.template.spboot.root.data.resource.AppUserResource;
import org.template.spboot.root.exception.handler.ExternalExceptionHandler;
import org.template.spboot.root.interfaces.AnimalInterface;
import org.template.spboot.root.interfaces.FeedInterface;
import org.template.spboot.root.service.UserApiServiceInterface;

/**
 * Mocked integration test
 *
 * @author dnikiforov
 */
@RunWith(SpringRunner.class)
@ContextHierarchy({
	@ContextConfiguration(name = "root", classes = WebApplicationConfig.class)
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc()
@WithMockUser(username = "user1", password = "1111", roles = {"ADMIN"})
public class AppUserRestTest {

	private static AppUser user;
	private static String jsonView;

	@MockBean(name = "jpaUserApiService")
	private UserApiServiceInterface userApiService;

	@MockBean(name = "animalParent")
	private AnimalInterface animalParent;

	@MockBean(name = "parentAlias")
	private AnimalInterface animalParentByAlias;

	@MockBean(name = "parentFeed")
	private FeedInterface parentFeed;	
	
	@MockBean
	private TaskInterface taskService;	
	
	@MockBean
	@FromParent
	private AnimalInterface fromParent;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MockMvc mockMvc;

	public AppUserRestTest() {
	}

	@BeforeClass
	public static void setUpClass() throws IOException {
		user = new AppUser("dmitry", "Qahjasha");
		user.setAppRoles(Collections.singleton(new AppRole(RoleEnum.ROLE_ADMIN)));
		final List<AppUserResource> singletonList = Collections.singletonList(new AppUserResource(user));
		jsonView = CommonHelper.transformObjectToJson(singletonList);
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
		Mockito.when(userApiService.getAllUsers()).thenReturn(Collections.singletonList(user));
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testGetAllUsers() throws Exception {
		mockMvc
				.perform(
						get("/users/getAll")
								.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(content().json(jsonView));
	}

}
