package com.bourgadix.dao;

// Generated 31 mars 2015 00:48:58 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

/**
 * Sexe generated by hbm2java
 */
@Entity
@Table(name = "sexe", catalog = "cabinets", uniqueConstraints = @UniqueConstraint(columnNames = "sexe"))
@Indexed
public class Sexe implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5576421052881319910L;
	private Integer idsexe;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String sexe;
	
	private Set<Client> clients = new HashSet<Client>(0);

	public Sexe() {
	}

	public Sexe(String sexe, Set<Client> clients) {
		this.sexe = sexe;
		this.clients = clients;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idsexe", unique = true, nullable = false)
	public Integer getIdsexe() {
		return this.idsexe;
	}

	public void setIdsexe(Integer idsexe) {
		this.idsexe = idsexe;
	}

	@Column(name = "sexe", unique = true, length = 45)
	public String getSexe() {
		return this.sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sexe")
	public Set<Client> getClients() {
		return this.clients;
	}

	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}

	public static void main(String[] args) {
		Dao dao = new Dao();
		Country country = new Country();
		country.setCountry("Morocco");
		dao.save(country);
	}
}