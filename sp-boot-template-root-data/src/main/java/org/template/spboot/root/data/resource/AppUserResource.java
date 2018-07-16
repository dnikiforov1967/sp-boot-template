/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.data.resource;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.template.spboot.root.data.model.AppRole;
import org.template.spboot.root.data.model.AppUser;
import org.template.spboot.root.data.model.RoleEnum;

/**
 *
 * @author dnikiforov
 */
public class AppUserResource implements Comparable<AppUserResource>, Serializable {

	public static AppUser CONVERT2USER(AppUserResource resource) {
		final AppUser appUser = new AppUser(resource.getUsername(), resource.getPassword());
		final Set<AppRole> appRoles = resource.roles.stream().map((t) -> {
			return new AppRole(t);
		}).collect(Collectors.toSet());
		appUser.setAppRoles(appRoles);
		return appUser;
	}

	private String username;
	private String password;
	private Set<RoleEnum> roles = new HashSet<>();

	public AppUserResource() {
	}

	public AppUserResource(AppUser appUser) {
		this.username = appUser.getUsername();
		this.password = appUser.getPassword();
		final Set<AppRole> appRoles = appUser.getAppRoles();
		if (appRoles != null) {
			this.roles = appRoles.stream().map(AppRole::getId).collect(Collectors.toSet());
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<RoleEnum> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEnum> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 83 * hash + Objects.hashCode(this.username);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AppUserResource other = (AppUserResource) obj;
		if (!Objects.equals(this.username, other.username)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(AppUserResource t) {
		return this.username.compareTo(t.username);
	}

}
