/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.bejinariu.programm;

import at.htlstp.bejinariu.launch.Intro_Slide_FX;
import java.io.File;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Dru
 */
public class CreditsGenerator {

    private static Stage myStage;

    public static void showCredits(Stage myStage, File videoFile) {
        
        CreditsGenerator.myStage = myStage; 
        
        System.out.println(videoFile.exists());
        Media media = new Media(videoFile.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

        MediaView mediaView = new MediaView(mediaPlayer);
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root);

        root.getChildren().add(mediaView);
        root.setPrefWidth(1280);
        root.setPrefHeight(720);
        scene.setOnKeyPressed(k -> onActionKeyTyped(k));
        myStage.setScene(scene);
        new Intro_Slide_FX(500, myStage, Intro_Slide_FX.Position.MID).slideAndShowStage(); //Anpassen

    }

  
    private static void onActionKeyTyped(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ESCAPE)) {
            myStage.fireEvent(new WindowEvent(myStage, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
    }
}
