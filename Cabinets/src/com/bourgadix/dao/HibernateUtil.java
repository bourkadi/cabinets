package com.bourgadix.dao;

/*import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil
{
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    static
    {
        try
        {
//          Configuration configuration = new Configuration();
            Configuration configuration = new Configuration().configure();

            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        catch (HibernateException he)
        {
            System.err.println("Error creating Session: " + he);
            throw new ExceptionInInitializerError(he);
        }
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    } 
}*/
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

   private static ServiceRegistry serviceRegistry;
   private static final ThreadLocal<Session> threadLocal = new ThreadLocal();
   private static SessionFactory sessionFactory;
    private static SessionFactory configureSessionFactory() {
        try {

            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new ServiceRegistryBuilder()
                                 .applySettings(configuration.getProperties())
                                 .buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        } catch (HibernateException e) {
            System.out.append("** Exception in SessionFactory **");
            e.printStackTrace();
        }
       return sessionFactory;
  }


  static {
    try {
      sessionFactory = configureSessionFactory();
    } catch (Exception e) {
      System.err.println("%%%% Error Creating SessionFactory %%%%");
      e.printStackTrace();
    }
  }

  private HibernateUtil() {
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static Session getSession() throws HibernateException {
    Session session = threadLocal.get();

    if (session == null || !session.isOpen()) {
      if (sessionFactory == null) {
        rebuildSessionFactory();
      }
      session = (sessionFactory != null) ? sessionFactory.openSession() : null;
      threadLocal.set(session);
    }

    return session;
  }

  public static void rebuildSessionFactory() {
    try {
      sessionFactory = configureSessionFactory();
    } catch (Exception e) {
      System.err.println("%%%% Error Creating SessionFactory %%%%");
      e.printStackTrace();
    }
  }

  public static void closeSession() throws HibernateException {
    Session session = (Session) threadLocal.get();
    threadLocal.set(null);

    if (session != null) {
      session.close();
    }
  }
}