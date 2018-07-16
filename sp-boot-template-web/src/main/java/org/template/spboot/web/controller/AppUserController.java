/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.web.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.template.spboot.root.data.model.AppUser;
import org.template.spboot.root.data.resource.AppUserResource;
import org.template.spboot.root.service.UserApiServiceInterface;

/**
 *
 * @author dnikiforov
 */
@RestController
@RequestMapping("/users")
public class AppUserController {

	private static final Logger LOG = Logger.getLogger(AppUserController.class.getName());

	@Autowired
	@Qualifier("jpaUserApiService")
	private UserApiServiceInterface userApiService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/getAll", method = GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	@Secured({"ROLE_ADMIN"})
	public List<AppUserResource> getAll() {
		LOG.log(Level.WARNING, "getAll() method invocation");
		final List<AppUser> allUsers = userApiService.getAllUsers();
		final List<AppUserResource> collect = allUsers.stream().map(AppUserResource::new).collect(Collectors.toList());
		return collect;
	}

	@RequestMapping(value = "", method = POST,
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	@Secured({"ROLE_ADMIN"})
	public AppUserResource create(@RequestBody AppUserResource appUserResource) {
		LOG.log(Level.WARNING, "create() method invocation");
		final AppUser appUser = AppUserResource.CONVERT2USER(appUserResource);
		appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
		final AppUser create = userApiService.create(appUser);
		LOG.log(Level.WARNING, "create() method invocation completed, " + create.toString());
		return new AppUserResource(create);
	}

}
