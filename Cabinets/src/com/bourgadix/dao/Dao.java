package com.bourgadix.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class Dao implements DaoService, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -25626743054512674L;
	private static Logger logger = LogManager.getLogger(Dao.class);
	private static Level VERBOSE = Level.forName("VERBOSE", 190);

	public <T> void save(final T o) {
		Transaction trns = null;
		Session session = HibernateUtils.getSessionFactory().openSession();

		try {
			trns = session.beginTransaction();
			logger.log(VERBOSE, "Trying to save object from " + o.getClass().getCanonicalName());
			session.saveOrUpdate(o);
			session.getTransaction().commit();
			logger.log(VERBOSE, "Object saved from " + o.getClass().getCanonicalName());

		} catch (RuntimeException e1) {
			if (trns != null) {
				logger.log(VERBOSE, "Object failed to be save of " + o.getClass().getCanonicalName());

				trns.rollback();
			}
			e1.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}

	}

	public <T> void update(final T o) {
		Transaction trns = null;
		Session session = HibernateUtils.getSessionFactory().openSession();

		try {
			trns = session.beginTransaction();
			logger.log(VERBOSE, "Trying to save object from " + o.getClass().getCanonicalName());
			session.update(o);
			session.getTransaction().commit();
			logger.log(VERBOSE, "Object saved from " + o.getClass().getCanonicalName());

		} catch (RuntimeException e1) {
			if (trns != null) {
				logger.log(VERBOSE, "Object failed to be save of " + o.getClass().getCanonicalName());

				trns.rollback();
			}
			e1.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}

	}

	public <T> List<T> getAll(final Class<T> type) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction trns = null;
		trns = session.beginTransaction();
		final Criteria crit = session.createCriteria(type);
		List<T> list = null;
		try {

			list = crit.list();
			logger.log(VERBOSE, "Fetching objects from " + type.getClass().getCanonicalName());
			trns.commit();
		} catch (RuntimeException e1) {

			e1.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	public <T> T get(final Class<T> type, final Integer id) {
		Transaction trns = null;
		Session session = HibernateUtils.getSessionFactory().openSession();
		T t = null;
		try {
			logger.log(VERBOSE,
					"Trying to get object from " + type.getClass().getCanonicalName() + " with identifier " + id);

			trns = session.beginTransaction();
			t = (T) session.get(type, id);
			session.getTransaction().commit();
			logger.log(VERBOSE, "Object fetched with success from " + type.getClass().getCanonicalName()
					+ " with identifier " + id);

		} catch (RuntimeException e1) {
			if (trns != null) {
				logger.log(VERBOSE,
						"Failed to get object from " + type.getClass().getCanonicalName() + " with identifier " + id);

				trns.rollback();
			}
			e1.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return t;

	}

	public <T> List<T> getByProperty(final Class<T> type, String propertyName, Object value) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		List<T> list = null;
		try {
			final Criteria crit = session.createCriteria(type);
			crit.add(Restrictions.eq(propertyName, value));
			list = crit.list();
			logger.log(VERBOSE, "Trying to get object from " + type.getClass().getCanonicalName() + " with property "
					+ propertyName + " for the value " + value);
		} catch (RuntimeException e1) {
			e1.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	public static void main(String[] args) {
		Dao dao = new Dao();
		List<Prescription> list = dao.getPrescriptionsByClient(5);
		for (Prescription visit : list) {
			System.out.println(visit.getIdprescription());
		}
	}

	@Override
	public List<Visit> getVisits(Integer ud, Integer a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Visit> getActifVisits(Integer a, Integer b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Visit> getVisitsOfClient(int c, Integer a, Integer b) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		List<Visit> list = null;
		try {
			final Criteria crit = session.createCriteria(Visit.class);
			// crit.add(Restrictions.between("datevisit", a, b));
			if (a > 0) {
				crit.add(Restrictions.ge("datevisit", a));
			}
			if (b > 0) {
				crit.add(Restrictions.le("datevisit", b));
				// crit.add(Restrictions.ne("statusVisit.idstatusVisit", 3));
				crit.addOrder(Order.asc("datevisit"));
			}
			crit.add(Restrictions.eq("client.idclient", c));
			list = crit.list();
		} catch (RuntimeException e1) {
			e1.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@Override
	public List<Sexe> getSexeList() {
		// TODO Auto-generated method stub
		Session session = HibernateUtils.getSessionFactory().openSession();
		List<Sexe> list = null;
		try {
			final Criteria crit = session.createCriteria(Sexe.class);

			crit.addOrder(Order.asc("idsexe"));

			list = crit.list();
		} catch (RuntimeException e1) {
			e1.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@Override
	public List<Country> getCountriesList() {
		// TODO Auto-generated method stub
		Session session = HibernateUtils.getSessionFactory().openSession();
		List<Country> list = null;
		try {
			final Criteria crit = session.createCriteria(Country.class);

			crit.addOrder(Order.asc("idcountry"));

			list = crit.list();
		} catch (RuntimeException e1) {
			e1.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}

	@Override
	public List<Prescription> getPrescriptionsByClient(int client) {
		// TODO Auto-generated method stub
		Session session = HibernateUtils.getSessionFactory().openSession();
		List<Prescription> list = null;
		try {
			final Criteria crit = session.createCriteria(Prescription.class);

			// crit.addOrder(Order.asc("idcountry"));
			crit.add(Restrictions.eq("client.idclient", client));
			crit.addOrder(Order.desc("idprescription"));
			list = crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		} catch (RuntimeException e1) {
			e1.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return list;
	}
	
}
