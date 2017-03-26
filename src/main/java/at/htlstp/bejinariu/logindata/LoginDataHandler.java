/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logindata;

import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Dru
 */
public interface LoginDataHandler {

    //JDBC Verbindungsaufbau und -abbau Methoden 
    public void connect(String user, String pwd, String url, String driverClass) throws SQLException;

    public void close() throws SQLException;

    public boolean isConnected();

    //User holen 
    //Exceptions sind innerhalb der Funktion aufzufangen und null ist zurückzuliefern
    public Properties getLogins();

    //Je Flag aller User auf false setzen oder einen bestimmten user als "rememberer" markieren
    public void rememberAction(boolean rememberState, String user) throws SQLException;
    
    public String getUserRemembered(); 
}
