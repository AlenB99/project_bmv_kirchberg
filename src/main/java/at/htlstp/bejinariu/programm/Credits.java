/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.bejinariu.programm;

import at.htlstp.bejinariu.launch.Intro_Slide_FX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Dru
 */
public class Credits extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/Credits"));
            Parent root = (AnchorPane) loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Credits");
            new Intro_Slide_FX(1000, primaryStage, Intro_Slide_FX.Position.MID).slideAndShowStage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
