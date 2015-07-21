package com.bourgadix.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class DaoVisit extends Dao implements DaoService {

	public DaoVisit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Visit> getVisits(Integer a, Integer b) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		List<Visit> list = null;
		try {
			final Criteria crit = session.createCriteria(Visit.class);
			// crit.add(Restrictions.between("datevisit", a, b));
			crit.add(Restrictions.ge("datevisit", a));
			crit.add(Restrictions.le("datevisit", b));
			// crit.add(Restrictions.ne("statusVisit.idstatusVisit", 3));
			crit.addOrder(Order.asc("datevisit"));
			list = crit.list();
		} catch (RuntimeException e1) {
			e1.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	public List<Visit> getActifVisits(Integer a, Integer b) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		List<Visit> list = null;
		try {
			final Criteria crit = session.createCriteria(Visit.class);
			// crit.add(Restrictions.between("datevisit", a, b));
			crit.add(Restrictions.ge("datevisit", a));
			crit.add(Restrictions.le("datevisit", b));
			crit.add(Restrictions.ne("statusVisit.idstatusVisit", 3));// id 3
																		// correspand
																		// to
																		// Status
																		// Annulé
			crit.addOrder(Order.asc("datevisit"));
			list = crit.list();
		} catch (RuntimeException e1) {
			e1.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}
	
	

}
