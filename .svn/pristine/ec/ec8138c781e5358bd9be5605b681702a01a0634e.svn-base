package com.bourgadix.dao;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "treatment", catalog = "cabinets")
public class Treatment {

	private Integer idTreatment;
	private Medicament medicament;
	private String description;

	public Treatment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Treatment(Integer idTreatment, Medicament medicament,
			String description) {
		super();
		this.idTreatment = idTreatment;
		this.medicament = medicament;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idtreatment", unique = true, nullable = false)
	public Integer getIdTreatment() {
		return idTreatment;
	}

	public void setIdTreatment(Integer idTreatment) {
		this.idTreatment = idTreatment;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "medicament")
	public Medicament getMedicament() {
		return medicament;
	}

	public void setMedicament(Medicament medicament) {
		this.medicament = medicament;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static void main(String[] args) {

	
		Dao dao = new Dao();
		Treatment treatment = dao.get(Treatment.class, 1);
		Prescription prescription=new Prescription();
		Visit visit=dao.get(Visit.class, 1);
		prescription.getTreatments().add(treatment);
		prescription.setVisit(visit);
		prescription.setCreatedate(12345671);
		dao.save(prescription);
	

	}
}
