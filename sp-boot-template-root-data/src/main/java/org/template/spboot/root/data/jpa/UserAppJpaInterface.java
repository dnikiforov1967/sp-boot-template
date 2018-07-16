/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.data.jpa;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.template.spboot.root.data.model.AppUser;

/**
 *
 * @author dnikiforov
 */
@Transactional
public interface UserAppJpaInterface extends JpaRepository<AppUser, String> {
	
}
