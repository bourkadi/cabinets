package com.bourgadix.services;

import java.util.Date;

public interface ClientsService {
	public Message addClient(int us, String name, String lastname,String identity,
			String birthplace, Date birthdate, int s, int c, String ph,
			String phf, String addr, String note);
	public Message addClient(String us, String name, String lastname,String identity,
			String birthplace, Date birthdate, int s, int c, String ph,
			String phf, String addr, String note);

	public void updateClient();

	public Boolean isExisted(String identity);
}
