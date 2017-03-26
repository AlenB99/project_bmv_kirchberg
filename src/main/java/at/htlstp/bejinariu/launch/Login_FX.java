package at.htlstp.bejinariu.launch;

import java.io.IOException;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login_FX {

    //Variaben 
    private LoginFileController controller;
    private String user, password;
    private LoginData loginData;
    private Stage loginStage;
    private final ReadOnlyBooleanWrapper close = new ReadOnlyBooleanWrapper(false);

    // Konstruktoren 
    public Login_FX(LoginData data) {
        this.loginData = data;
        this.loginStage = new Stage();
    }

    public Login_FX() {
        this.loginStage = new Stage();
    }

    /*
    Kann nur innerhalb des Paketes aufgrufen werden. Dient dazu den Zugriff 
    auf das BooleanWrapper-Objekt close von außen zu schützen, wobei der 
    Controller die Berechtigung benötigt
     */
    void close() {
        close.set(true);
        this.loginStage.close();
    }

    //Methoden 
    public void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginFile.fxml"));
            Parent root = loader.load();
            controller = loader.getController();
            controller.setLoginInfo(loginData, this, loginStage);
            Scene scene = new Scene(root);
            loginStage.setResizable(false);
            loginStage.setScene(scene);
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    /*Die MEthode soll nur vom Controller aufgerufen werden, wenn ein User sich 
    erfolgreich eingeloggt hat d.h. default Zugriff
     */
    void setLoginInfo(String user, String pwd) {
        this.password = pwd;
        this.user = user;
    }

    /*
    Soll die darüberliegende Anwendung über den Zustand der CheckBox(Login Merken)
    informieren 
    */
    public boolean getRememberState() {
        return controller.remember();
    }

    //Getter und Setter 
    public boolean isClose() {
        return close.get();
    }

    public ReadOnlyBooleanProperty closeProperty() {
        return close;
    }

    public String getUser() {
        return close.get() ? this.user : null;
    }

    public String getPassword() {
        return close.get() ? this.password : null;
    }

    public Stage getLoginStage() {
        return loginStage;
    }
    public void setData(LoginData logins) {
        this.loginData = logins;
    }
     public void setRemberedUser(String user) {
        controller.setRember(user);
    }

}
