/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.bejinariu.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Dru
 */
@Entity
@IdClass(KleidungsstueckId.class)
public class Kleidungsstueck implements Serializable {

    private static final long serialVersionUID = 1L;

    //Felder 
    @Column(name = "k_status")
    private Kleidungsstueck.Status status;
    @Column(name = "k_aenderungsdatum")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date aenderungsdatum;
    @Column(name = "k_groesse")
    private String kleidungsgroesse;
    //Zusammengesetzte Id, IdClasse oben angegeben 
    @Id
    @ManyToOne(optional = false)    //Ein Kleidungsstück ohne Besitzer ist nicht möglich 
    @JoinColumn(name = "k_mitglied_nr")
    private Person mitglied;
    @Id
    @Column(name = "k_bezeichnung")
    private String bezeichnung;

    //Konstruktoren 
    public Kleidungsstueck() {
    }

    public Kleidungsstueck(String bezeichnung, Status status, Date aenderungsdatum, String kleidungsgroesse) {
        this.bezeichnung = bezeichnung;
        this.status = status;
        this.aenderungsdatum = aenderungsdatum;
        this.kleidungsgroesse = kleidungsgroesse;
    }

    //Getter, Setter 
    public Person getMitglied() {
        return mitglied;
    }

    public void setMitglied(Person mitglied) {
        this.mitglied = mitglied;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getAenderungsdatum() {
        return aenderungsdatum;
    }

    public void setAenderungsdatum(Date aenderungsdatum) {
        this.aenderungsdatum = aenderungsdatum;
    }

    public String getKleidungsgroesse() {
        return kleidungsgroesse;
    }

    public void setKleidungsgroesse(String kleidungsgroesse) {
        this.kleidungsgroesse = kleidungsgroesse;
    }

    //Enums 
    public static enum Status {
        Beim_Verein, Beim_Mitglied, Nicht_im_Besitz;

        @Override
        public String toString() {
            return this.name().replace("_", " ");
        }

    }

    public static enum Groesse {
        XLL, L, XL;         //Zu verfolständigen 
    }
}
