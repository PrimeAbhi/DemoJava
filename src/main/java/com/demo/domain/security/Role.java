package com.demo.domain.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Role implements Serializable {

	private static final long serialVersionUID = 890245234L;

	@Id
	private int roleId;

	private String name;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<UserRole> userroles = new HashSet<>();

	public Role() {

	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserRole> getUserroles() {
		return userroles;
	}

	public void setUserroles(Set<UserRole> userroles) {
		this.userroles = userroles;
	}

}
