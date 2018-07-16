/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.data.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dnikiforov
 */
@Entity
@Table(name = "users")
public class AppUser implements Comparable<AppUser>, Serializable {

	@Id
	private String username;
	@NotNull
	@Column(length = 64)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {
		CascadeType.REFRESH, CascadeType.MERGE
	})
	@JoinTable(
			name = "userToRole",
			joinColumns = {
				@JoinColumn(referencedColumnName = "username", name = "appUserId")
			},
			inverseJoinColumns = {
				@JoinColumn(referencedColumnName = "id", name = "appRoleId")
			}
	)
	private Set<AppRole> appRoles = new HashSet<>();

	public Set<AppRole> getAppRoles() {
		return appRoles;
	}

	public void setAppRoles(Set<AppRole> appRoles) {
		this.appRoles = appRoles;
	}

	public AppUser() {
	}

	public AppUser(String username, String password) {
		this.username = username;
		this.password = password;
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

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 43 * hash + Objects.hashCode(this.username);
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
		if (!(obj instanceof AppUser)) {
			return false;
		}
		final AppUser other = (AppUser) obj;
		if (!Objects.equals(this.username, other.username)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(AppUser t) {
		return this.username.compareTo(t.username);
	}

	@Override
	public String toString() {
		return "AppUser{" + "username=" + username + ", password=" + password + '}';
	}

}
