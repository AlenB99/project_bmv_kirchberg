/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.bejinariu.launch;

import at.htlstp.bejinariu.graphictools.Utilities;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Niko
 */
public class LoginFileController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Text txtInteraction;
    @FXML
    private CheckBox cbMerken;

    private final Map<String, String> loginData = new HashMap<>();
    private Login_FX main;
    private Stage myStage;
    private boolean capsLockActive;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setInteractionText("Willkommen!");
        capsLockActive = (Toolkit.getDefaultToolkit().getLockingKeyState(20));
        if (capsLockActive) {
            setInteractionText("Info: Feststelltaste gedrückt");
        }
    }

    @FXML
    private void onActionLogin(ActionEvent event) {

        String name = txtName.getText(), pwd = txtPassword.getText();
        if (pwd.isEmpty() && name.isEmpty()) {
            return;
        }
        if (!loginData.containsKey(name)) {
            txtName.requestFocus();
            txtName.setPromptText("Benutzer nicht gefunden");
            Utilities.showMessage("Fehler", "Authentifikationsfehler", "Benutzer nicht gefunden", Alert.AlertType.ERROR, false);
            return;
        } else if (!loginData.get(name).equals(pwd)) {
            txtPassword.requestFocus();
            Utilities.showMessage("Fehler", "Authentifikationsfehler", "Passwort ist falsch", Alert.AlertType.ERROR, false);
            return;
        }

        main.setLoginInfo(name, pwd);
        main.close();

    }

    void setLoginInfo(LoginData data, Login_FX main, Stage myStage) {
        this.myStage = myStage;

        Properties p = data.getLoginData();
        p.keySet().stream().forEach((user) -> {
            loginData.put((String) user, (String) p.get(user));
        });
        this.main = main;
    }

    @FXML
    private void onActionKeyTyped(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ESCAPE)) {
            myStage.fireEvent(new WindowEvent(myStage, WindowEvent.WINDOW_CLOSE_REQUEST));
        } else if (event.getCode().equals(KeyCode.CAPS)) {
            capsLockActive = !capsLockActive;
            if (capsLockActive) {
                setInteractionText("Info: Feststelltaste gedrückt");
            } else {
                txtInteraction.setVisible(false);
            }
        }

    }

    private void setInteractionText(String text) {
        txtInteraction.setText(text);
        txtInteraction.setVisible(true);

    }

    public boolean remember() {
        return cbMerken.isSelected();
    }

    public void setRember(String rememberedUser) {
        if (!(rememberedUser == null)) {

            txtName.setText(rememberedUser);
            txtPassword.requestFocus();
        }
    }
}
