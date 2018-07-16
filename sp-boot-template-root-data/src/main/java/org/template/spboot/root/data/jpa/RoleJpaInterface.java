/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.data.jpa;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.template.spboot.root.data.model.AppRole;
import org.template.spboot.root.data.model.RoleEnum;

/**
 *
 * @author dnikiforov
 */
@Transactional
public interface RoleJpaInterface extends JpaRepository<AppRole, RoleEnum> {
	
}
