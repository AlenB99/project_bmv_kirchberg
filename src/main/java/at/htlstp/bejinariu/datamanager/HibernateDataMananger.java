/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.bejinariu.datamanager;

import at.htlstp.bejinariu.models.Person;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Dru
 */
public class HibernateDataMananger implements DataManager, AutoCloseable {

    private static HibernateDataMananger INSTANCE = new HibernateDataMananger();

    public static HibernateDataMananger getINSTANCE() {
        return INSTANCE;
    }

    public List<Person> loadAll() {
        Session session = null;
        try {
            session = HibernateJPAUtil.getSessionFactory().openSession();
            Query qu = session.createQuery("from Person");
            return qu.list();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public void close() throws Exception {
        HibernateJPAUtil.close();
    }

    @Override
    public Integer storePerson(Person newPerson) {
        Session session = null;
        Transaction t = null;
        try {
            session = HibernateJPAUtil.getSessionFactory().openSession();
            t = session.beginTransaction();
            Integer id = (Integer) session.save(newPerson);
            t.commit();
            return id;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            if (t.isActive()) {
                t.rollback();
                return null;
            }
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public boolean refreshPerson(Person personToUpdate) {
        Session session = null;
        Transaction t = null;
        try {
            session = HibernateJPAUtil.getSessionFactory().openSession();
            System.out.println(session);
            t = session.beginTransaction();
            session.update(personToUpdate);
            t.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            if (t.isActive()) {
                t.rollback();
                return false;
            }
        } finally {
            session.close();
        }
        return false;

    }

    @Override
    public boolean deletePerson(Person personToDelete) {
        Session session = null;
        Transaction t = null;
        try {
            session = HibernateJPAUtil.getSessionFactory().openSession();
            t = session.beginTransaction();
            session.delete(personToDelete);
            t.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            if (t.isActive()) {
                t.rollback();
                return false;
            }
        } finally {
            session.close();
        }
        return false;

    }

    @Override
    public Person getPersonByMitgliedNr(Integer i) {
        Session session = null;
        Transaction t = null;
        try {
            session = HibernateJPAUtil.getSessionFactory().openSession();
            t = session.beginTransaction();
            Person person = (Person) session.get(Person.class,
                     i);
            t.commit();
            return person;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            if (t.isActive()) {
                t.rollback();
                return null;
            }
        } finally {
            session.close();
        }
        return null;
    }

}
