/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.bejinariu.programm;

import at.htlstp.bejinariu.datamanager.HibernateDataMananger;
import at.htlstp.bejinariu.launch.Intro_Slide_FX;
import at.htlstp.bejinariu.models.Person;
import at.htlstp.bejinariu.graphictools.Utilities;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logindata.LoginDataHandler;

/**
 *
 * @author Niko
 */
public class Ausfuehrbar extends Application {

    @Override
    public void start(Stage primaryStage) {

        //Login
        /*
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
         */
        try {

            //Start des richtigen Programmsteils
            //-----------------------------------------------------------------------------------
            //switch (loginFXApplication.getUser()) {
         
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/personDetail.fxml"));
            Parent root =(AnchorPane) loader.load(); 
            primaryStage.setScene(new Scene(root));
            PersonDetailController controller = loader.getController();
            primaryStage.setOnCloseRequest(e -> controller.close());
            new Intro_Slide_FX(3000, primaryStage, Intro_Slide_FX.Position.TOP).slideAndShowStage();
           
           

        } catch (Exception e) {
            Utilities.showMessage("Fehler", "Problem beim Starten", "Das Laden der Applikation schlug fehl. Wenden Sie sich an den Hersteller", Alert.AlertType.ERROR, false);
        }
    }

    private void loadWithReadRights() {
        System.out.println("Benutzer eingeloggt.");
    }

    private void loadWithAllRights() {

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

}
