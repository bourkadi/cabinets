package com.bourgadix.services;

import java.util.Date;
import java.util.Set;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.Prescription;
import com.bourgadix.dao.Treatment;
import com.bourgadix.dao.User;

public interface PrescriptionService {
	
	public Prescription addPrescription(Set<Treatment> treatments,Date prescriptiondate,String note, Client client,User user);

}
