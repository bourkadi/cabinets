package com.bourgadix.dao;

import java.util.Date;

// Generated 31 mars 2015 00:48:58 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Prescription generated by hbm2java
 */
@Entity
@Table(name = "prescription", catalog = "cabinets")
public class Prescription implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4167558234019141385L;
	private Integer idprescription;
	// private Visit visit;
	private Integer createdate;
	private User user;
	private Client client;
	// private Set<Medicament> medicaments=new HashSet<Medicament>(0);
	private Set<Treatment> treatments = new HashSet<Treatment>(0);

	private Date prescriptiondate;

	public Prescription() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idprescription", unique = true, nullable = false)
	public Integer getIdprescription() {
		return this.idprescription;
	}

	public void setIdprescription(Integer idprescription) {
		this.idprescription = idprescription;
	}

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "visit")
	// public Visit getVisit() {
	// return this.visit;
	// }
	//
	// public void setVisit(Visit visit) {
	// this.visit = visit;
	// }
	//
	// @Column(name = "createdate")
	// public Integer getCreatedate() {
	// return this.createdate;
	// }

	public void setCreatedate(Integer createdate) {
		this.createdate = createdate;
	}
	//
	// @OneToMany(fetch = FetchType.LAZY, targetEntity = Medicament.class,
	// cascade = CascadeType.ALL)
	// @JoinTable(name = "medicament_perscription", joinColumns = {
	// @JoinColumn(name = "idprescription") }, inverseJoinColumns = {
	// @JoinColumn(name = "idmedicament") })
	// public Set<Medicament> getMedicaments() {
	// return medicaments;
	// }
	//
	// public void setMedicaments(Set<Medicament> medicaments) {
	// this.medicaments = medicaments;
	// }

	@OneToMany(fetch = FetchType.LAZY, targetEntity = Treatment.class, cascade = CascadeType.ALL)
	@JoinTable(name = "treatment_perscription", joinColumns = {
			@JoinColumn(name = "idprescription") }, inverseJoinColumns = { @JoinColumn(name = "idtreatment") })
	public Set<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(Set<Treatment> treatments) {
		this.treatments = treatments;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client")
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Column(name = "prescriptiondate")
	public Date getPrescriptiondate() {
		return prescriptiondate;
	}

	public void setPrescriptiondate(Date prescriptiondate) {
		this.prescriptiondate = prescriptiondate;
	}

	@Column(name = "createdate", length=11)
	public Integer getCreatedate() {
		return createdate;
	}

}
