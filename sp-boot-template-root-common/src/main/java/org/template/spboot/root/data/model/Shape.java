/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.data.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author dnikiforov
 */
@Entity
@Table(name="shapes")
public class Shape implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	@ElementCollection
	@CollectionTable(name = "shapenames", joinColumns = @JoinColumn(name = "shape_id"))
	@Column(name="name")
	private Set<String> names = new HashSet<>();

	public Set<String> getNames() {
		return names;
	}

	public void setNames(Set<String> names) {
		this.names = names;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Shape)) {
			return false;
		}
		Shape other = (Shape) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "org.template.spboot.root.data.model.Shape[ id=" + id + " ]";
	}
	
}
