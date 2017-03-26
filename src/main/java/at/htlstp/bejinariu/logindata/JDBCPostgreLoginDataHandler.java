package logindata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * Bejinariu Alexandru Klasse: 3AHIF AufnahmeNummer: 20130041 Katalognummer: 1
 */
public class JDBCPostgreLoginDataHandler implements LoginDataHandler {

    //Variablen 
    private String userRemembered;
    private Connection con = null;

    //Getter
    public String getUserRemembered() {
        return userRemembered;
    }

    //Datenbankzugriffe 
    public void connect(String user, String pwd, String url, String driverClass) throws SQLException {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Treiber konnte nicht geladen werden");
        }

        con = DriverManager.getConnection(url, user, pwd); //Verbinden mit dem Server.
    }

    public boolean isConnected() {
        return con != null;
    }

    public void close() throws SQLException {
        if (con != null) {
            con.close();
            con = null;
        }
    }

    public Properties getLogins() {
        try {
            if (!isConnected()) {
                throw new SQLException();
            }
            Properties p = new Properties();
            Statement state = con.createStatement();
            ResultSet RS = state.executeQuery("select bez, passwd, remember from benutzer");
            while (RS.next()) {
                if (RS.getBoolean(3)) {
                    this.userRemembered = RS.getString(1);
                }
                p.put(RS.getString(1), RS.getString(2));
            }
            return p;
        } catch (SQLException s) {
            return null;
        }

    }

    public void rememberAction(boolean rememberState, String user) throws SQLException {
        if (rememberState) {
            PreparedStatement pre = con.prepareStatement("Update benutzer set remember = true where bez = ?");
            pre.setString(1, user);
            pre.execute();
        } else {
            PreparedStatement pre = con.prepareStatement("Update benutzer set remember = false");
            pre.execute();
        }
    }

}
