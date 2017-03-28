/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.bejinariu.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Dru
 */
@Entity
@Table(name = "person", schema = "public")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    //Identifikation der Person 
    @Id
    @Column(name = "p_nummer")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer personId;

    //Kontaktdaten 
    @Column(name = "p_vorname", nullable = false)
    private String vorname;
    @Column(name = "p_nachname", nullable = false)
    private String nachname;
    @Column(name = "p_telefonnr")
    private String telefonnr;
    @Column(name = "p_email")
    private String email;
    @Column(name = "p_anmerkung")
    private String anmerkung;
    @Column(name = "p_marketender")
    private boolean isMarkentender;

    //FetchType.EAGER ist notwendig, da ein FetchType.LAZY die Collection nur bei Bedarf lädt und in den meisten Fälle ist die Session dann geschlossen
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "mitglied")
    private final List<Kleidungsstueck> kleidungsstuecke = new ArrayList<>();

    //Konstruktoren 
    public Person() {
    }

    public Person(String vorname, String nachname, String telefonnr, String email, String anmerkung, boolean markentender) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.telefonnr = telefonnr;
        this.email = email;
        this.isMarkentender = markentender;
        this.anmerkung = anmerkung;
    }

    //Getter, Setter 
    public String getAnmerkung() {
        return anmerkung;
    }

    public boolean isIsMarkentender() {
        return isMarkentender;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getTelefonnr() {
        return telefonnr;
    }

    public String getEmail() {
        return email;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public void setTelefonnr(String telefonnr) {
        this.telefonnr = telefonnr;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAnmerkung(String anmerkung) {
        this.anmerkung = anmerkung;
    }

    public void setIsMarkentender(boolean isMarkentender) {
        this.isMarkentender = isMarkentender;
    }

    //Keine Setter, Getter liefert ein Kopie 
    public List<Kleidungsstueck> getKleidungsstuecke() {
        return new ArrayList<>(kleidungsstuecke);
    }

    //Beziehung aufbauen 
    public void neuesKleidungsstueck(Kleidungsstueck ks) {
        ks.setMitglied(this);
        this.kleidungsstuecke.add(ks);
    }

    public Kleidungsstueck getKS(String bezeichnung) {
        for (Kleidungsstueck ks : kleidungsstuecke) {
            if (ks.getBezeichnung().equals(bezeichnung)) {
                return ks;
            }

        }
        return null;
    }

    //Equals und HashCode 
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.personId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (!Objects.equals(this.personId, other.personId)) {
            return false;
        }
        return true;
    }

    //Kontrollausgaben 
   
    public String toStringAdvanced() {
        String result = "Person{" + "personId=" + personId + ", vorname=" + vorname + ", nachname=" + nachname + ", telefonnr=" + telefonnr + ", email=" + email + ", anmerkung=" + anmerkung + ", isMarkentender=" + isMarkentender + '}';
        result += result + "\n";
        for (Kleidungsstueck k : kleidungsstuecke) {
            result = result + k.getBezeichnung() + ", " + k.getKleidungsgroesse() + ", " + k.getAenderungsdatum() + ", " + k.getStatus() + "\n";
        }
        return result;
    }
    
    public String toString(){
        return  personId != null? vorname + " " + nachname: "Neue Person"; 
    }

}
