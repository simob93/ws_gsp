package it.gspRiva.utils;


import java.util.TimeZone;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import it.gspRiva.entity.Anagrafica;
import it.gspRiva.entity.AnagraficaCorso;
import it.gspRiva.entity.Calendar;
import it.gspRiva.entity.Comuni;
import it.gspRiva.entity.Corso;
import it.gspRiva.entity.EntityBase;
import it.gspRiva.entity.IscrittoCorso;
import it.gspRiva.entity.Istruttori;
import it.gspRiva.entity.Operatore;

public class HibernateUtils {
 
    //XML based configuration
    private static SessionFactory sessionFactory;
     
    //Annotation based configuration
    private static SessionFactory sessionAnnotationFactory;
 
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            System.out.println("Hibernate Configuration loaded");
             
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate serviceRegistry created");
             
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
             
            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    private static SessionFactory buildSessionAnnotationFactory() {
        try {
        	//TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
           // Properties properties = new Properties();
           // properties.setProperty("hibernate.connection.isolation", String.valueOf(Connection.TRANSACTION_SERIALIZABLE));
            configuration.configure("hibernate.cfg.xml");
            configuration.addPackage("it.gspRiva.entity"); //the fully qualified package name
            configuration.addAnnotatedClass(EntityBase.class);
            configuration.addAnnotatedClass(Operatore.class);
            configuration.addAnnotatedClass(Anagrafica.class);
            configuration.addAnnotatedClass(AnagraficaCorso.class);
            configuration.addAnnotatedClass(Corso.class);
            configuration.addAnnotatedClass(IscrittoCorso.class);
            configuration.addAnnotatedClass(Istruttori.class);
            configuration.addAnnotatedClass(Calendar.class);
            configuration.addAnnotatedClass(Comuni.class);
            System.out.println("Hibernate Annotation Configuration loaded");
             
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate Annotation serviceRegistry created");
             
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
             
            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
   
     
    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
     
    public static SessionFactory getSessionAnnotationFactory() {
        if(sessionAnnotationFactory == null) sessionAnnotationFactory = buildSessionAnnotationFactory();
        return sessionAnnotationFactory;
    }
     
}