/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.data.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author dnikiforov
 */
@Entity
@Table(name = "appRole")
public class AppRole implements Serializable {

	@Id
	@Enumerated(EnumType.STRING)
	private RoleEnum id;
	
	@ManyToMany(mappedBy = "appRoles", cascade=CascadeType.REFRESH)
    private Set<AppUser> users;	

	public Set<AppUser> getUsers() {
		return users;
	}

	public void setUsers(Set<AppUser> users) {
		this.users = users;
	}
	
	public AppRole() {
		
	}

	public AppRole(RoleEnum id) {
		this.id = id;
	}

	public RoleEnum getId() {
		return id;
	}

	public void setId(RoleEnum id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 53 * hash + Objects.hashCode(this.id);
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
		if (!(obj instanceof AppRole)) {
			return false;
		}
		final AppRole other = (AppRole) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

}
