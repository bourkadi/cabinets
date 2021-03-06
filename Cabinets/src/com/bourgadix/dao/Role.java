package com.bourgadix.dao;

// Generated 31 mars 2015 00:48:58 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * UserRoles generated by hbm2java
 */
@Entity
@Table(name = "role", catalog = "cabinets", uniqueConstraints = @UniqueConstraint(columnNames = "role"))
public class Role implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5980775524621906059L;
	private Integer userRoleId;
	private String role;
	private String description;

	public Role() {
	}

	public Role(String role) {
		this.role = role;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idrole", unique = true, nullable = false)
	public Integer getUserRolesId() {
		return this.userRoleId;
	}

	public void setUserRolesId(Integer userRolesId) {
		this.userRoleId = userRolesId;
	}

	@Column(name = "role", unique = true, length = 45)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	@Column(name = "description", length = 65535)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static void main(String[] args) {
		DaoService daoService = new Dao();
		Role role=new Role("admin");
		daoService.save(role);
	}
}
