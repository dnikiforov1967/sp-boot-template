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
 *  * 
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
 * Bi-directional without JoinColumn and MappedBy is a harmfull mixture
 * 
 * create table test_child2d (id integer not null, parent_id integer, primary key (id))
 * create table test_parent_test_child2d (Parent_id integer not null, childs2d_id integer not null)
 * alter table test_parent_test_child2d add constraint UK_e0netqc8m8xujr95rlmym6ght unique (childs2d_id)
 * alter table test_child2d add constraint FKf9lunsgaf0wm6wpr76x9mnkxs foreign key (parent_id) references test_parent
 * alter table test_parent_test_child2d add constraint FKlrcbwyv8t7gfj7t7fuukf24ld foreign key (childs2d_id) references test_child2d
 * alter table test_parent_test_child2d add constraint FK76sie1tikm1jqttsu4nflavll foreign key (Parent_id) references test_parent
 * 
 * Bi-directional with mappedBy:
 * 
 * create table test_child2djoined (id integer not null, parent_id integer, primary key (id))
 * alter table test_child2djoined add constraint FKtp33508e9if425e4psfy5mxgx foreign key (parent_id) references test_parent
 * 
 * 
 */
@Entity
@Table(name="test_parent")
public class Parent {
	@Id
	private Integer id;

	
	@OneToMany
	private Collection<Child> childs = new LinkedList<>();

	@OneToMany
	private Collection<Child2D> childs2d = new LinkedList<>();	
	
	@OneToMany(mappedBy = "parent")
	private Collection<Child2DJoined> childs2djoined = new LinkedList<>();		

	public Collection<Child2D> getChilds2d() {
		return childs2d;
	}

	public void setChilds2d(Collection<Child2D> childs2d) {
		this.childs2d = childs2d;
	}
	
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
