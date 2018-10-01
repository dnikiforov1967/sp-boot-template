/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.data.model.one2many;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author dnikiforov
 * 
 * Unidirectional association creates 3 tables.
 * 
 * create table test_child (id integer not null, primary key (id))
 * create table test_parent (id integer not null, primary key (id))
 * create table test_parent_test_child (Parent_id integer not null, childs_id integer not null)
 * 
 * The constraint below supports one-to-one
 * alter table test_parent_test_child add constraint UK_2uqbahdvf97p9ycwb6id3ma0f unique (childs_id)
 * 
 * alter table test_parent_test_child add constraint FKg1teay9w8yhil4ok58v4mdxe foreign key (childs_id) references test_child
 * alter table test_parent_test_child add constraint FKclqss6x66r6wn32mfyujm70o2 foreign key (Parent_id) references test_parent
 * 
 */
@Entity
@Table(name="test_parent")
public class Parent {
	@Id
	private Integer id;

	
	@OneToMany
	private Collection<Child> childs = new LinkedList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Collection<Child> getChilds() {
		return childs;
	}

	public void setChilds(Collection<Child> childs) {
		this.childs = childs;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 59 * hash + Objects.hashCode(this.id);
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
		final Parent other = (Parent) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}
	
}
