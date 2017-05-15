/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.bejinariu.datamanager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateJPAUtil {

    private static final SessionFactory SESSIONFACTORY;
    private static final EntityManagerFactory ENTITYMANAGERFACTORY;

    static {
        try {
            // loads configuration and mappings
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

            ENTITYMANAGERFACTORY = Persistence.createEntityManagerFactory("PersistanceUnit");

            // builds a session factory from the service registry
            SESSIONFACTORY = configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception ex) {
            System.out.println("EntityManagerFactory creation faild: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void close() {
        ENTITYMANAGERFACTORY.close();
        SESSIONFACTORY.close();
    }

    public static SessionFactory getSessionFactory() {
        return SESSIONFACTORY;
    }
}
