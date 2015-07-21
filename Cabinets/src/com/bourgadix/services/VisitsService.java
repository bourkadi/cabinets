package com.bourgadix.services;

import java.util.Date;
import java.util.List;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.TypeVisit;
import com.bourgadix.dao.Visit;

public interface VisitsService {

	public Message addVisit(int u, int cl, int st, int tp, String note, Date dv);

	public Message addVisit(String u, int cl, int st, int tp, String note,
			Date dv);

	public Message addVisit(String u, Client cl, int st, TypeVisit tp,
			String note, Date dv);

	public Message changeVisitStatus(int v, int s);

	public Message updateVisit(int us, int v, Date dv, Integer st, Integer tp);

	public Message updateVisit(String u, int v, Date dv, Integer st,
			Integer tp, String note);

	public List<Visit> getVisits(Date date, Date d1);

	public List<Visit> getActifVisits(Date date, Date d);

	public List<Visit> getTodayVisits();

	public List<Visit> getTomorrowVisits();

	public List<Visit> getWeekVisits();

	public int getVersionNumber(int v);

	public Boolean visitAlreadyExist(Date date);

}
