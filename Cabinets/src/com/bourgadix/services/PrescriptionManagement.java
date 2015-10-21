package com.bourgadix.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Medicament;
import com.bourgadix.dao.Prescription;
import com.bourgadix.dao.Treatment;
import com.bourgadix.dao.User;

public class PrescriptionManagement implements PrescriptionService {

	private DaoService dao = new Dao();

	@Override
	public void addPrescription(Set<Treatment> treatments, Date prescriptiondate, Client client, User user) {
		// TODO Auto-generated method stub
		Prescription prescription = new Prescription();
		prescription.setClient(client);
		prescription.setPrescriptiondate(prescriptiondate);
		prescription.setTreatments(treatments);
		prescription.setUser(user);
		prescription.setCreatedate(dateToUnixTimestamp(new Date()));
		dao.save(prescription);
		
	}
	private static Integer dateToUnixTimestamp(Date date) {
		long unixtime;
		unixtime = date.getTime() / 1000L;
		return (int) unixtime;
	}
	public static void main(String[] args) {
		DaoService dao = new Dao();
		PrescriptionService prescriptionService=new PrescriptionManagement();
		Treatment treatment=new Treatment();
		Medicament medicament=dao.get(Medicament.class, 1);
		Medicament medicament1=dao.get(Medicament.class, 2);
		treatment.setMedicament(medicament);
		treatment.setDescription("2 fois par jour");
		Treatment treatment1=new Treatment();
		treatment1.setMedicament(medicament1);
		treatment1.setDescription("3 fois par jour apres repas");
		Date date=new Date();
		Client client=dao.get(Client.class, 1);
		User user=dao.get(User.class, 1);
		Set<Treatment> set=new  HashSet<Treatment>(0);
		set.add(treatment1);
		set.add(treatment);
		prescriptionService.addPrescription(set, date, client, user);
		

	}
	
}
