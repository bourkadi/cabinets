package com.bourgadix.services;

import java.util.Date;
import java.util.Set;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Prescription;
import com.bourgadix.dao.Treatment;
import com.bourgadix.dao.User;

public class PrescriptionManagement implements PrescriptionService {
	
	private DaoService dao = new Dao();

	@Override
	public Prescription addPrescription(Set<Treatment> treatments, Date prescriptiondate,String note, Client client, User user) {
		// TODO Auto-generated method stub
		Prescription prescription = new Prescription();
		prescription.setClient(client);
		prescription.setPrescriptiondate(prescriptiondate);
		prescription.setTreatments(treatments);
		prescription.setUser(user);
		prescription.setCreatedate(dateToUnixTimestamp(new Date()));
		prescription.setNote(note);
		dao.save(prescription);
		return prescription;
	}
	private static Integer dateToUnixTimestamp(Date date) {
		long unixtime;
		unixtime = date.getTime() / 1000L;
		return (int) unixtime;
	}

}
