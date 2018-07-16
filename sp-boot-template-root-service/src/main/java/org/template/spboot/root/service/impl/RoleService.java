/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.template.spboot.root.data.jpa.RoleJpaInterface;
import org.template.spboot.root.data.model.AppRole;
import org.template.spboot.root.service.RoleServiceInterface;

/**
 *
 * @author dnikiforov
 */
@Component("jpaRoleService")
@Transactional
public class RoleService implements RoleServiceInterface {

	@Autowired
	private RoleJpaInterface roleJpa;

	@Override
	public AppRole create(AppRole appRole) {
		return roleJpa.saveAndFlush(appRole);
	}

}
