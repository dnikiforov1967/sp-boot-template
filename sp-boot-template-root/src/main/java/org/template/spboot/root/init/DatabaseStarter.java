/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.init;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.template.spboot.root.common.CommonHelper;
import org.template.spboot.root.data.model.AppRole;
import org.template.spboot.root.data.model.AppUser;
import org.template.spboot.root.data.resource.AppUserResource;
import org.template.spboot.root.service.RoleServiceInterface;
import org.template.spboot.root.service.UserApiServiceInterface;

/**
 *
 * @author dnikiforov
 */
@Service
@Lazy(false)
public class DatabaseStarter {

	private static final Logger LOG = Logger.getLogger(DatabaseStarter.class.getName());

	@Autowired
	private UserApiServiceInterface userServiceInterface;

	@Autowired
	private RoleServiceInterface roleServiceInterface;

	@PostConstruct
	private void init() {
		try {
			final AppRole[] roles = CommonHelper.transformJsonToObject("defaultRoleSet.json", AppRole[].class);
			Arrays.stream(roles).forEach(roleServiceInterface::create);

			final AppUserResource[] userResources = CommonHelper.transformJsonToObject("defaultUserSet.json", AppUserResource[].class);
			final Stream<AppUser> users = Arrays.stream(userResources).map((t) -> {
				final AppUser appUser = new AppUser(t.getUsername(), t.getPassword());
				final Set<AppRole> collect = t.getRoles().stream().map(AppRole::new).collect(Collectors.toSet());
				appUser.setAppRoles(collect);
				return appUser;
			});
			users.forEach(userServiceInterface::create);
			LOG.log(Level.WARNING, "Initial user set created");
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

}
