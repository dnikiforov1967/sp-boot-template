/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.service;

import java.util.List;
import org.template.spboot.root.data.model.AppUser;

/**
 *
 * @author dnikiforov
 */
public interface UserApiServiceInterface {

	AppUser create(AppUser user);

	AppUser update(AppUser user);

	void delete(AppUser user);

	AppUser find(AppUser user);

	AppUser findByName(String userName);

	List<AppUser> getAllUsers();

}
