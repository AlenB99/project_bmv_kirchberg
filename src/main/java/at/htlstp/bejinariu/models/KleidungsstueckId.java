/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.bejinariu.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Dru
 */
@Embeddable
public class KleidungsstueckId implements Serializable {

    //ID-Felder aus der Klasse Kleidungsstueck 
    private Person mitglied;
    private String bezeichnung;
    
    //Konstruktoren 
    public KleidungsstueckId(Person mitglied, String bezeichnung) {
        this.mitglied = mitglied;
        this.bezeichnung = bezeichnung;
    }

    public KleidungsstueckId() {
    }

    //Getter und Setter
    public Person getMitglied() {
        return mitglied;
    }

    public void setMitgliedId(Person mitglied) {
        this.mitglied = mitglied;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    public String toString() {
        return "KleidungsstueckId{" + "mitglied=" + mitglied + ", bezeichnung=" + bezeichnung + '}';
    }

    //HashCode und equals 
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.mitglied);
        hash = 89 * hash + Objects.hashCode(this.bezeichnung);
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
        final KleidungsstueckId other = (KleidungsstueckId) obj;
        if (this.mitglied != other.mitglied) {
            return false;
        }
        if (!Objects.equals(this.bezeichnung, other.bezeichnung)) {
            return false;
        }
        return true;
    }

}
