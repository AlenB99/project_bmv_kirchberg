/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.bejinariu.datamanager;
import at.htlstp.bejinariu.models.Person;



/**
 *
 * @author Dru
 */
public interface DataManager {
    
    public Integer storePerson(Person newPerson); 
    
    public boolean refreshPerson(Person personToUpdate); 
    
    public Person getPersonByMitgliedNr(Integer mitgliedNr); 
    
    public boolean deletePerson(Person personToDelete); 
    
}
