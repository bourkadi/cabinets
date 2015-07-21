package com.bourgadix.ui.cabinets.rdv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.bourgadix.dao.Dao;
import com.bourgadix.dao.DaoService;
import com.bourgadix.dao.Visit;
import com.vaadin.ui.components.calendar.event.CalendarEvent;
import com.vaadin.ui.components.calendar.event.CalendarEventProvider;

public class EventsOfUserProvider implements CalendarEventProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3574567197898614716L;
	
	private Integer idc;

	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		DaoService daoService = new Dao();
		startDate = new Date("01/05/2015");
		endDate = new Date("07/05/2015");
		System.out.println("this is startDate: " + startDate);
		System.out.println("this is endDate:  " + endDate);

		for (Visit visit : daoService.getByProperty(Visit.class, "client.idclient", this.getIdc())) {

			MyCustomBasicEvent event = new MyCustomBasicEvent();
			
			event.setStart(visit.getDateVisitTime());
			event.setDescription(visit.getNote());
		
			event.setCaption("Un RDV avec Mr:"
					+ visit.getClient().getLastname());
			DateTime dateTime = new DateTime(visit.getDateVisitTime());
			DateTime plusPeriod = dateTime.plus(Period.hours(2));
			event.setEnd(plusPeriod.toDate());
			event.setIdVisit(visit.getIdvisit());
			events.add(event);
		}
		return events;
	}

	public Integer getIdc() {
		return idc;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

}
