package com.bourgadix.services;

import java.util.Date;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.Country;
import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Sexe;
import com.bourgadix.dao.User;

public class ClientManagement implements ClientsService {
	private DaoService dao = new Dao();
	private static Logger logger = LogManager.getLogger(UserManagement.class);
	private static Level VERBOSE = Level.forName("VERBOSE", 190);

	private AccessService access = new AccessManagement();

	@Override
	public Message addClient(int us, String name, String lastname, String identity, String birthplace, Date birthdate,
			int s, int c, String ph, String phf, String addr, String note) {
		// TODO Auto-generated method stub
		Message message = new Message();
		if (!access.canUpdateClient(us)) {
			message.setMessage("You are not authorized to perform this action");
			message.setValue(false);
			return message;
		} else {
			if (!isExisted(identity)) {
				Client client = new Client();
				client.setUsers(dao.get(User.class, us));
				client.setAdress(addr);
				client.setBirthdate(birthdate);
				client.setBirthplace(birthplace);
				client.setCountry(dao.get(Country.class, c));
				client.setIdentityNumber(identity);
				client.setLastname(lastname);
				client.setName(name);
				client.setSexe(dao.get(Sexe.class, s));
				client.setNote(note);
				client.setPhone(ph);
				client.setPhonefix(phf);
				client.setCreatedDate(dateToUnixTimestamp(new Date()));
				dao.save(client);
				logger.log(VERBOSE, "Client " + name + " add with success");

				message.setMessage("Client " + name + " add with success");
				message.setValue(true);
				return message;

			} else {
				logger.log(VERBOSE, "Client " + name + " already exist");

				message.setMessage("Client " + name + " already exist");
				message.setValue(false);
				return message;
			}
		}

	}

	@Override
	public void updateClient() {
		// TODO Auto-generated method stub

	}

	public Boolean isExisted(String identity) {
		// TODO Auto-generated method stub
		/*
		 * if (dao.getByProperty(Client.class, "identityNumber",
		 * identity).size() > 0) { return true; } else { return false; }
		 */
		return false;
	}

	private static Integer dateToUnixTimestamp(Date date) {
		long unixtime;
		unixtime = date.getTime() / 1000L;
		return (int) unixtime;
	}

	@Override
	public Message addClient(String us, String name, String lastname, String identity, String birthplace,
			Date birthdate, int s, int c, String ph, String phf, String addr, String note) {
		Message message = new Message();
		User user = (User) dao.getByProperty(User.class, "username", us).get(0);
		if (!access.canUpdateClient(user.getIdUser())) {
			message.setMessage("You are not authorized to perform this action");
			message.setValue(false);
			return message;
		} else {
			if (!isExisted(identity)) {
				Client client = new Client();
				client.setUsers(user);
				client.setAdress(addr);
				client.setBirthdate(birthdate);
				client.setBirthplace(birthplace);
				client.setCountry(dao.get(Country.class, c));
				client.setIdentityNumber(identity);
				client.setLastname(lastname);
				client.setName(name);
				client.setSexe(dao.get(Sexe.class, s));
				client.setNote(note);
				client.setPhone(ph);
				client.setPhonefix(phf);

				client.setCreatedDate(dateToUnixTimestamp(new Date()));
				dao.save(client);

				logger.log(VERBOSE, "Client " + name + " add with success");

				message.setMessage("Client " + name + " add with success");
				message.setValue(true);
				message.setIdClient(client.getIdclient());
				System.out.println("ID CLIENT HERE!=>" + message.getIdClient());
				return message;

			} else {
				logger.log(VERBOSE, "Client " + name + " already exist");

				message.setMessage("Client " + name + " already exist");
				message.setValue(false);
				return message;
			}
		}
	}

	public static void main(String[] args) {
		ClientsService service = new ClientManagement();

		Message message = service.addClient(1, "test", "testy", "hhhh", "fes", new Date("12/10/1988"), 1, 1,
				"HHHH", "hhh", "hhhhh", "dddrr");
		System.out.println("le message=>"+message.getMessage());
	}
}
