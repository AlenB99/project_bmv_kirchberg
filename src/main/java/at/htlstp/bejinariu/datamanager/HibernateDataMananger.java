/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.bejinariu.datamanager;

import at.htlstp.bejinariu.models.Kleidungsstueck;
import at.htlstp.bejinariu.models.Person;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
            //Error Nullpointer
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
        try {
            session = HibernateJPAUtil.getSessionFactory().openSession();
            Person person = (Person) session.get(Person.class, i);
            return person;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    public ObservableList<Person> loadTestData() {
        List<Person> all = new ArrayList<>();

        try {
            all = Files.lines(Paths.get("src/main/resources/namen.csv"))
                    .map(s -> s.split(";"))
                    .map(s -> {

                        String vor = s[1];
                        String nach = s[0];
                        Person p = new Person(vor, nach, "02626264636", "alex@gmail.com", "nixda", false);
                        LocalDate zeitpunkt = LocalDate.now();
                        Kleidungsstueck k1 = new Kleidungsstueck("Hut", Kleidungsstueck.Status.Beim_Verein, Date.from(zeitpunkt.atStartOfDay(ZoneId.systemDefault()).toInstant()), "12");
                        Kleidungsstueck k2 = new Kleidungsstueck("Hemd", Kleidungsstueck.Status.Beim_Verein, Date.from(zeitpunkt.atStartOfDay(ZoneId.systemDefault()).toInstant()), "12");
                        Kleidungsstueck k3 = new Kleidungsstueck("Hose", Kleidungsstueck.Status.Beim_Verein, Date.from(zeitpunkt.atStartOfDay(ZoneId.systemDefault()).toInstant()), "12");
                        Kleidungsstueck k4 = new Kleidungsstueck("Winterjacke", Kleidungsstueck.Status.Beim_Verein, Date.from(zeitpunkt.atStartOfDay(ZoneId.systemDefault()).toInstant()), "L");
                        Kleidungsstueck k5 = new Kleidungsstueck("Trachtenjanker", Kleidungsstueck.Status.Beim_Verein, Date.from(zeitpunkt.atStartOfDay(ZoneId.systemDefault()).toInstant()), "maßgeschneidert");
                        Kleidungsstueck k6 = new Kleidungsstueck("Bündel", Kleidungsstueck.Status.Beim_Verein, Date.from(zeitpunkt.atStartOfDay(ZoneId.systemDefault()).toInstant()), "keine Größe");
                        Kleidungsstueck k7 = new Kleidungsstueck("Gillette", Kleidungsstueck.Status.Beim_Verein, Date.from(zeitpunkt.atStartOfDay(ZoneId.systemDefault()).toInstant()), "maßgeschneidert");
                        Kleidungsstueck k8 = new Kleidungsstueck("Dirndl", Kleidungsstueck.Status.Beim_Verein, Date.from(zeitpunkt.atStartOfDay(ZoneId.systemDefault()).toInstant()), "maßgeschneidert");
                        Kleidungsstueck k9 = new Kleidungsstueck("Gürtel", Kleidungsstueck.Status.Beim_Verein, Date.from(zeitpunkt.atStartOfDay(ZoneId.systemDefault()).toInstant()), "65");
                        Kleidungsstueck k10 = new Kleidungsstueck("Schuhe", Kleidungsstueck.Status.Beim_Verein, Date.from(zeitpunkt.atStartOfDay(ZoneId.systemDefault()).toInstant()), "14.5");
                        List<Kleidungsstueck> ks = new ArrayList<>(Arrays.asList(k1, k2, k3, k4, k5, k6, k7, k8, k9, k10));
                        ks.forEach(k
                                -> {
                            p.neuesKleidungsstueck(k);
                        });
                        p.setPersonId(INSTANCE.storePerson(p));
                        return p;
                    })
                    .collect(Collectors.toList());
            return FXCollections.observableArrayList(all);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

}
