package com.bourgadix.dao;

// Generated 31 mars 2015 00:48:58 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.hibernate.annotations.Formula;

/**
 * Visit generated by hbm2java
 */
@Entity
@Table(name = "visit", catalog = "cabinets")
public class Visit implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3000044191634553321L;
	private Integer idvisit;
	private StatusVisit statusVisit;
	private User users;
	private TypeVisit typeVisit;
	private Client client;
	private Integer datevisit;
	private Integer datecreated;
	private String note;
	private Set<VisitHistory> visitHistories = new HashSet<VisitHistory>(0);
	//private Set<Prescription> prescriptions = new HashSet<Prescription>(0);
	
	private Date dateVisitTime;

	public Visit() {
	}

	public Visit(StatusVisit statusVisit, User users, TypeVisit typeVisit,
			Client client, Integer datevisit, Integer datecreated,
			Set<VisitHistory> visitHistories) {
		this.statusVisit = statusVisit;
		this.users = users;
		this.typeVisit = typeVisit;
		this.client = client;
		this.datevisit = datevisit;
		this.datecreated = datecreated;
		this.visitHistories = visitHistories;
	
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idvisit", unique = true, nullable = false)
	public Integer getIdvisit() {
		return this.idvisit;
	}

	public void setIdvisit(Integer idvisit) {
		this.idvisit = idvisit;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "status_visit")
	public StatusVisit getStatusVisit() {
		return this.statusVisit;
	}

	public void setStatusVisit(StatusVisit statusVisit) {
		this.statusVisit = statusVisit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	public User getUsers() {
		return this.users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_visit")
	public TypeVisit getTypeVisit() {
		return this.typeVisit;
	}

	public void setTypeVisit(TypeVisit typeVisit) {
		this.typeVisit = typeVisit;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client")
	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}


	@Column(name = "datevisit")
	public Integer getDatevisit() {
		return this.datevisit;
	}

	public void setDatevisit(Integer datevisit) {
		this.datevisit = datevisit;
	}

	@Column(name = "datecreated")
	public Integer getDatecreated() {
		return this.datecreated;
	}

	public void setDatecreated(Integer datecreated) {
		this.datecreated = datecreated;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "visit")
	public Set<VisitHistory> getVisitHistories() {
		return this.visitHistories;
	}

	public void setVisitHistories(Set<VisitHistory> visitHistories) {
		this.visitHistories = visitHistories;
	}

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "visit")
//	public Set<Prescription> getPrescriptions() {
//		return this.prescriptions;
//	}
//
//	public void setPrescriptions(Set<Prescription> prescriptions) {
//		this.prescriptions = prescriptions;
//	}

	@Column(name = "note", length = 65535)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Formula(" (SELECT FROM_UNIXTIME(datevisit) )")
	public Date getDateVisitTime() {
		return dateVisitTime;
	}

	public void setDateVisitTime(Date dateVisitTime) {
		this.dateVisitTime = dateVisitTime;
	}

}
