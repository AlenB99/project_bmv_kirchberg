/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.bejinariu.programm;

import at.htlstp.bejinariu.launch.Intro_Slide_FX;
import at.htlstp.bejinariu.graphictools.Utilities;
import at.htlstp.bejinariu.launch.Login_FX;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logindata.JDBCPostgreLoginDataHandler;
import logindata.LoginDataHandler;

/**
 *
 * @author Niko
 */

//"src/main/resources/images/Logo.png"
public class Ausfuehrbar extends Application {

    private static Stage stage;

    public static Stage getScene() {
        return stage;
    }

    @Override
    public void start(Stage primaryStage) {

        //Login
        LoginDataHandler postgreConnection = new JDBCPostgreLoginDataHandler();
        Login_FX loginFXApplication = new Login_FX();
        //Anzeigen des Login-Screens, warten bis sich der User einloggt 

        try {
            postgreConnection.connect("postgres", "dodge1970", "jdbc:postgresql://localhost:5432/db_trachtenverein", "org.postgresql.Driver");
            loginFXApplication.setData(() -> postgreConnection.getLogins());
            loginFXApplication.showLogin();
            loginFXApplication.getLoginStage().setOpacity(0.0);
            loginFXApplication.setRemberedUser(postgreConnection.getUserRemembered());
            new Intro_Slide_FX(1000, loginFXApplication.getLoginStage(), Intro_Slide_FX.Position.MID).slideAndShowStage();
            loginFXApplication.getLoginStage().setOnCloseRequest(w -> closeStage(postgreConnection));
            loginFXApplication.getLoginStage().getIcons().add(new Image(this.getClass().getResource("/images/Logo.png").toString()));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Utilities.showMessage("Fehler", "Datenbankverbindungsproblem", "Ein Problem mit der Datenbank beim Laden der User ist aufgetreten. Wenden Sie sich an den Hersteller", Alert.AlertType.ERROR, false);
        }

        //Programm arbeitet erst nach dem Einloggen des Benutzers weiter 
        loginFXApplication.closeProperty().addListener((Observable e) -> {

            try {

                //Rememberer User eintragen, Datenbankconncetion schließn 
                postgreConnection.rememberAction(loginFXApplication.getRememberState(), loginFXApplication.getUser());
                closeStage(postgreConnection);

                //try {
                //Start des richtigen Programmsteils
                //-----------------------------------------------------------------------------------
                stage = primaryStage;
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/personDetail.fxml"));
                Parent root = (BorderPane) loader.load();
                primaryStage.setScene(new Scene(root));
                PersonDetailController controller = loader.getController();
                if ("User".equals(loginFXApplication.getUser())) {
                    controller.readingUser();           //Bestimmte Programmteile ausblenden
                }
                primaryStage.setOnCloseRequest(e1 -> controller.close());
                primaryStage.getIcons().add(new Image(this.getClass().getResource("/images/Logo.png").toString()));
                primaryStage.setTitle("Trachtenverwaltungsprogramm v1.0");

                new Intro_Slide_FX(1000, primaryStage, Intro_Slide_FX.Position.MID).slideAndShowStage();
                stage = primaryStage;

            } catch (Exception e2) {
                System.out.println(e2.getMessage());
                Utilities.showMessage("Fehler", "Problem beim Starten", "Das Laden der Applikation schlug fehl. Wenden Sie sich an den Hersteller", Alert.AlertType.ERROR, false);
                System.out.println(e2.getMessage());
            }

        });
    }

    private void closeStage(LoginDataHandler postgreConnection) {

        if ((postgreConnection != null)) {
            if (postgreConnection.isConnected()) {
                try {
                    System.out.println("JDBC - Verbindung geschlossen!");
                    postgreConnection.close();
                } catch (SQLException ex) {
                    //Fehlerbehandlung
                    System.out.println("Schließen der Connection fehlgeschlagen!");
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
