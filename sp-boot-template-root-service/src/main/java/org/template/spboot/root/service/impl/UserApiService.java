/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Component;
import org.template.spboot.root.data.jpa.UserAppJpaInterface;
import org.template.spboot.root.data.model.AppUser;
import org.template.spboot.root.service.UserApiServiceInterface;

/**
 *
 * @author dnikiforov
 */
@Component("jpaUserApiService")
@Transactional
public class UserApiService implements UserApiServiceInterface {

	private static final Logger LOG = Logger.getLogger(UserApiService.class.getName());

	@Autowired
	private UserAppJpaInterface userAppJpa;

	//@PersistenceContext(unitName = "globalPU")
	//private EntityManager entityManager;
	@Override
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public AppUser create(AppUser user) {
		if (!userAppJpa.existsById(user.getUsername())) {
			userAppJpa.saveAndFlush(user);
		} else {
			throw new EntityExistsException("User " + user.getUsername() + " already exists");
		}
		return user;
	}

	@Override
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public AppUser update(AppUser user) {
		final AppUser found = find(user);
		if (found == null) {
			throw new EntityNotFoundException("User " + user.getUsername() + " not found");
		}
		final AppUser merged = userAppJpa.saveAndFlush(user);
		return merged;
	}

	@Override
	public void delete(AppUser user) {
		userAppJpa.delete(user);
		userAppJpa.flush();
	}

	@Override
	public AppUser find(AppUser user) {
		final Optional<AppUser> find = userAppJpa.findById(user.getUsername());
		return find.orElse(null);
	}

	@Override
	public AppUser findByName(String userName) {
		final Optional<AppUser> found = userAppJpa.findById(userName);
		return found.orElse(null);
	}

	@Override
	public List<AppUser> getAllUsers() {
		final List<AppUser> findAll = userAppJpa.findAll();
		return findAll;
	}

}
