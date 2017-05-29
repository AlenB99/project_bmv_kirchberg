package at.htlstp.bejinariu.programm;

import at.htlstp.bejinariu.models.Person;
import at.htlstp.bejinariu.programm.Ausfuehrbar;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javafx.stage.FileChooser;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * Bejinariu Alexandru Klasse: 3AHIF AufnahmeNummer: 20130041 Katalognummer: 1
 */
public class ReportGenerator {

    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_hhmm"); 
    public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/db_trachtenverein";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "dodge1970";

    public static void newReport(Person p) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("POSTGRESQL-Treiber nicht gefunden. Erstellung des Berichtes fehlgeschlagen!");
        }
        JasperPrint jasperPrint;
        try (Connection con = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {

            // JasperPrint - Objekt erzeugen
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF-Documente", ".pdf"));
            Map<String, Object> parameters = new HashMap<>();
            String jasper;

            if (p == null) {
                jasper = "reports/mitarbeiter_bulky.jasper";
                fc.setInitialFileName("Report_" + LocalDateTime.now().format(dtf) + "_Verein-Report");
                parameters = null;
            } else {
                fc.setInitialFileName("Report_" + LocalDateTime.now().format(dtf) + "_" + p.getNachname() + "_" + p.getVorname());
                jasper = "reports/mitarbeiter_report.jasper";
                parameters.put("nummer", p.getPersonId());

            }

            InputStream is = ReportGenerator.class.getClassLoader().getResourceAsStream(jasper);
           
            File document = fc.showSaveDialog(Ausfuehrbar.getScene().getOwner());
            if (document == null) {
                return;
            }
            // Report Exportieren
            jasperPrint = JasperFillManager.fillReport(is, parameters, con);
            System.out.println("Report erfolgreich befullt");

            JasperExportManager.exportReportToPdfFile(jasperPrint, document.getPath());
            System.out.println("Report erfolgreich erzeugt");

        } catch (JRException ex) {
            System.out.println("Jasper-Exception: " + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQL-Exception: " + ex.getMessage());
        }
    }
}
