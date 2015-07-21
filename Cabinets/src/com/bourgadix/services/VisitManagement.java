package com.bourgadix.services;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.DaoVisit;
import com.bourgadix.dao.StatusVisit;
import com.bourgadix.dao.TypeVisit;
import com.bourgadix.dao.User;
import com.bourgadix.dao.Visit;
import com.bourgadix.dao.VisitHistory;

public class VisitManagement implements VisitsService {

	private DaoService dao = new DaoVisit();
	private static Logger logger = LogManager.getLogger(VisitManagement.class);
	private static Level VERBOSE = Level.forName("VERBOSE", 190);
	private AccessService access = new AccessManagement();

	@Override
	public Message addVisit(int u, int cl, int st, int tp, String note, Date dv) {
		// TODO Auto-generated method stub
		Message message = new Message();
		if (!access.canUpdateUsers(u)) {
			message.setMessage("You are not authorized to perform this action");
			message.setValue(false);
			return message;
		} else {
			User user = dao.get(User.class, u);
			Client client = dao.get(Client.class, cl);
			StatusVisit statusVisit = dao.get(StatusVisit.class, st);
			TypeVisit typeVisit = dao.get(TypeVisit.class, tp);
			Visit visit = new Visit();
			visit.setClient(client);
			visit.setDatevisit(dateToUnixTimestamp(dv));
			visit.setStatusVisit(statusVisit);
			visit.setTypeVisit(typeVisit);
			visit.setUsers(user);
			visit.setDatecreated(dateToUnixTimestamp(new Date()));
			visit.setNote(note);
			dao.save(visit);
			logger.log(VERBOSE,
					"Une visite a été prise pour Mr: " + client.getLastname()
							+ " le :" + new Date(visit.getDatevisit()));
			message.setMessage("Visit added with success");
			message.setValue(true);
			return message;
		}
	}

	public Message addVisit(String u, int cl, int st, int tp, String note,
			Date dv) {
		// TODO Auto-generated method stub
		Message message = new Message();
		User user = (User) dao.getByProperty(User.class, "username", u).get(0);

		if (!access.canUpdateRdv(user.getIdUser())) {
			message.setMessage("You are not authorized to perform this action");
			message.setValue(false);
			return message;
		}
		if (visitAlreadyExist(dv)) {
			message.setMessage("RDV already exist");
			message.setValue(false);
			return message;
		} else {
			Client client = dao.get(Client.class, cl);
			StatusVisit statusVisit = dao.get(StatusVisit.class, st);
			TypeVisit typeVisit = dao.get(TypeVisit.class, tp);
			Visit visit = new Visit();
			visit.setClient(client);
			visit.setDatevisit(dateToUnixTimestamp(dv));
			visit.setStatusVisit(statusVisit);
			visit.setTypeVisit(typeVisit);
			visit.setUsers(user);
			visit.setDatecreated(dateToUnixTimestamp(new Date()));
			visit.setNote(note);
			dao.save(visit);
			logger.log(VERBOSE,
					"Une visite a été prise pour Mr: " + client.getLastname()
							+ " le :" + new Date(visit.getDatevisit()));
			message.setMessage("Visit added with success");
			message.setValue(true);
			return message;
		}
	}

	@Override
	public Message changeVisitStatus(int v, int s) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Integer dateToUnixTimestamp(Date date) {
		long unixtime;
		unixtime = date.getTime() / 1000L;
		return (int) unixtime;
	}

	@Override
	public Message updateVisit(int u, int v, Date dv, Integer st, Integer tp) {
		// TODO Auto-generated method stub
		Message message = new Message();
		if (!access.canUpdateUsers(u)) {
			message.setMessage("You are not authorized to perform this action");
			message.setValue(false);
			return message;
		} else {
			VisitHistory history = new VisitHistory();
			Visit visit = dao.get(Visit.class, v);
			history.setClient(visit.getClient().getIdclient());
			history.setUpdatedate(dateToUnixTimestamp(new Date()));
			history.setUsers(visit.getUsers());
			history.setVisit(visit);
			StatusVisit statusVisit = dao.get(StatusVisit.class, st);
			TypeVisit typeVisit = dao.get(TypeVisit.class, tp);

			if (dv != null) {
				history.setDatevisit(new Date(visit.getDatevisit()));
				visit.setDatevisit(dateToUnixTimestamp(dv));
				logger.log(
						VERBOSE,
						"La visite avec id: "
								+ v
								+ " a été modifié , la date de la visite a été changée pour :"
								+ dv.toString());
			}
			if (st != null) {
				history.setStatusVisit(visit.getStatusVisit().getStatusVisit());
				visit.setStatusVisit(statusVisit);
				logger.log(
						VERBOSE,
						"La visite avec id: "
								+ v
								+ " a été modifié , le status de la visite a été changé pour :"
								+ statusVisit.getStatusVisit());

			}
			if (tp != null) {
				history.setTypeVisit(visit.getTypeVisit().getTypeVisit());
				visit.setTypeVisit(typeVisit);
				logger.log(
						VERBOSE,
						"La visite avec id: "
								+ v
								+ " a été modifié , le type de la visite a été changé pour :"
								+ typeVisit.getTypeVisit());

			}
			int version = visit.getVisitHistories().size();
			if (version == 0) {
				history.setVersion(1);
			} else {
				history.setVersion(version + 1);
			}
			dao.save(visit);
			dao.save(history);

		}
		message.setMessage("Visit updated with success");
		message.setValue(true);
		return message;
	}

	public Message updateVisit(String u, int v, Date dv, Integer st,
			Integer tp, String note) {
		// TODO Auto-generated method stub
		Message message = new Message();
		User user = (User) dao.getByProperty(User.class, "username", u).get(0);
		if (!access.canUpdateRdv(user.getIdUser())) {
			message.setMessage("You are not authorized to perform this action");
			message.setValue(false);
			return message;
		}
		if (visitAlreadyExist(dv)) {
			message.setMessage("RDV already exist");
			message.setValue(false);
			return message;
		} else {
			VisitHistory history = new VisitHistory();
			Visit visit = dao.get(Visit.class, v);
			history.setClient(visit.getClient().getIdclient());
			history.setUpdatedate(dateToUnixTimestamp(new Date()));
			history.setUsers(visit.getUsers());
			history.setVisit(visit);
			StatusVisit statusVisit = dao.get(StatusVisit.class, st);
			TypeVisit typeVisit = dao.get(TypeVisit.class, tp);

			if (dv != null) {
				history.setDatevisit(new Date(visit.getDatevisit() * 1000L));
				visit.setDatevisit(dateToUnixTimestamp(dv));
				logger.log(
						VERBOSE,
						"La visite avec id: "
								+ v
								+ " a été modifié , la date de la visite a été changée pour :"
								+ dv.toString());
			} else {
				history.setDatevisit(new Date(visit.getDatevisit()));

			}
			if (st > 0) {
				history.setStatusVisit(visit.getStatusVisit().getStatusVisit());
				visit.setStatusVisit(statusVisit);
				logger.log(
						VERBOSE,
						"La visite avec id: "
								+ v
								+ " a été modifié , le status de la visite a été changé pour :"
								+ statusVisit.getStatusVisit());

			} else {
				history.setStatusVisit(visit.getStatusVisit().getStatusVisit());

			}
			if (tp > 0) {
				history.setTypeVisit(visit.getTypeVisit().getTypeVisit());
				visit.setTypeVisit(typeVisit);
				logger.log(
						VERBOSE,
						"La visite avec id: "
								+ v
								+ " a été modifié , le type de la visite a été changé pour :"
								+ typeVisit.getTypeVisit());

			} else {
				history.setTypeVisit(visit.getTypeVisit().getTypeVisit());

			}
			if (note.length() == 0) {

				String n = visit.getNote();
				System.out.println("Voila n=>" + n);
				history.setNote(n);
				visit.setNote("");

			} else {
				history.setNote(visit.getNote());
				visit.setNote(note);

			}
			int version = getVersionNumber(v);

			history.setVersion(version + 1);

			dao.save(visit);
			dao.save(history);

		}
		message.setMessage("Visit updated with success");
		message.setValue(true);
		return message;
	}

	public List<Visit> getActifVisits(Date date, Date d) {

		Integer ud = dateToUnixTimestamp(date);
		DateTime dateTime = new DateTime(d);
		DateTime plusPeriod = dateTime.plus(Period.days(1));
		System.out.println("Date 1=>" + ud);
		Integer u = (int) (plusPeriod.getMillis() / 1000L);
		System.out.println("date 2 =>" + u);
		System.out.println("just before");
		for (Visit vis : dao.getVisits(ud, u)) {
			System.out.println("hello");
			System.out.println("visite=!>" + vis.getClient().getLastname()
					+ "___" + vis.getIdvisit());

		}
		return dao.getActifVisits(ud, u);
	}

	public List<Visit> getTodayVisits() {
		// TODO Auto-generated method stub
		Date date;
		Calendar cal = Calendar.getInstance(); // locale-specific

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		date = cal.getTime();
		return getActifVisits(date, date);
	}

	public List<Visit> getTomorrowVisits() {
		// TODO Auto-generated method stub
		Date date;
		Calendar cal = Calendar.getInstance(); // locale-specific
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		date = cal.getTime();
		return getActifVisits(date, date);
	}

	public List<Visit> getWeekVisits() {
		// TODO Auto-generated method stub
		LocalDate now = new LocalDate();
		LocalDate monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
		LocalDate friday = now.withDayOfWeek(DateTimeConstants.FRIDAY);
		Date date = monday.toDate();
		Date date2 = friday.toDate();
		return getActifVisits(date, date2);
	}

	public Date tomorrow() {

		Date date;
		Calendar cal = Calendar.getInstance(); // locale-specific
		cal.add(Calendar.DATE, 1);
		// cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		// long time = cal.getTimeInMillis();
		date = cal.getTime();

		return date;

	}

	public Date today() {

		Date date;
		Calendar cal = Calendar.getInstance(); // locale-specific

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		// long time = cal.getTimeInMillis();
		date = cal.getTime();
		System.out.println("Today is:=>" + dateToUnixTimestamp(date));

		return date;

	}

	public Date AfterTomorrow() {

		Date date;
		Calendar cal = Calendar.getInstance(); // locale-specific
		cal.add(Calendar.DATE, 2);
		// cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		// long time = cal.getTimeInMillis();
		date = cal.getTime();

		return date;

	}

	public static void main(String[] args) throws ParseException {
		VisitManagement management = new VisitManagement();
		System.out.println(management.getVersionNumber(1));

	}

	@Override
	public List<Visit> getVisits(Date date, Date d1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getVersionNumber(int v) {
		// TODO Auto-generated method stub
		int nv = 0;
		nv = dao.getByProperty(VisitHistory.class, "visit.idvisit", v).size();
		return nv;
	}

	public Boolean visitAlreadyExist(Date date) {
		// TODO Auto-generated method stub
		if(date==null){
			return false;
		}
		int n = dao.getByProperty(Visit.class, "datevisit",
				dateToUnixTimestamp(date)).size();
		if (n == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Message addVisit(String u, Client cl, int st, TypeVisit tp,
			String note, Date dv) {
		// TODO Auto-generated method stub
		Message message = new Message();
		User user = (User) dao.getByProperty(User.class, "username", u).get(0);

		if (!access.canUpdateRdv(user.getIdUser())) {
			message.setMessage("You are not authorized to perform this action");
			message.setValue(false);
			return message;
		}
		if (visitAlreadyExist(dv)) {
			message.setMessage("RDV already exist");
			message.setValue(false);
			return message;
		} else {
			StatusVisit statusVisit = dao.get(StatusVisit.class, st);
			Visit visit = new Visit();
			visit.setClient(cl);
			visit.setDatevisit(dateToUnixTimestamp(dv));
			visit.setStatusVisit(statusVisit);
			visit.setTypeVisit(tp);
			visit.setUsers(user);
			visit.setDatecreated(dateToUnixTimestamp(new Date()));
			visit.setNote(note);
			dao.save(visit);
			logger.log(VERBOSE,
					"Une visite a été prise pour Mr: " + cl.getLastname()
							+ " le :" + new Date(visit.getDatevisit()));
			message.setMessage("Visit added with success");
			message.setValue(true);
			return message;
		}
	}

}
