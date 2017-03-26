package at.htlstp.bejinariu.graphictools;

//Imports 
import java.util.HashSet;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/*
 * @author Bejinariu Alexandru Klasse: 4AHIF Katalognummer: 01, Schuljahr:
 * 2016/17, Fach: POS1
 */
public class Utilities {

    public static HashSet<TextField> fehlerCount = new HashSet<>();

    //Statische Methoden - werden von unterschiedliche Klassen dieses Pakets verwendet 
    /**
     * Erstellt und blendet ein Alert-Fenster ein. Empfohlemene Verwendung:
     * Aufzeigen von aufgetretenen Ereignissen während des Programmablaufs
     * Methode darf nur von grafischen Threads aufgerufen werden
     *
     * @param title Title des Alert Fensters
     * @param headerMessage Title innerhalb des Fensters
     * @param message einzublendender Text
     * @param type Typ
     * @param expanded bei true wird das Fenster mithilfe einer TextArea
     * erweitert
     * @param fontFamily - Schriftart, bezieht sich auf den Text in der Textarea
     * @param weight - Schriftdicke, bezieht sich auf den Text in der Textarea
     * @param posture - Italic oder normal, bezieht sich auf den Text in der
     * Textarea
     * @param fontSize - Größe der Schrift, bezieht sich auf den Text in der
     * Textarea erweitert
     */
    public static void showMessage(String title, String headerMessage, String message, Alert.AlertType type, boolean expanded, String fontFamily, FontWeight weight, FontPosture posture, int fontSize) {

        Alert alert = new Alert(type);
        alert.initOwner(null);
        alert.setTitle(title);
        alert.setHeaderText(headerMessage);

        if (expanded) {     //Bei true wird der Text in einer TextArea gepackt(Scrollable - daher beliebig lang)
            alert.setContentText("More Information: ");
            TextArea messageNode = new TextArea(message);
            messageNode.setEditable(false);
            messageNode.setFont(Font.font(fontFamily, weight, posture, fontSize));     //Schrift setzen 
            alert.getDialogPane().setExpandableContent(messageNode);
            alert.getDialogPane().setExpanded(true);
        } else {
            alert.setContentText(message);
        }
        //Anzeigen und warten bis der User die Information gelesen hat 
        alert.showAndWait();

    }

    /**
     * Erstellt und blendet ein Alert-Fenster ein. Empfohlemene Verwendung:
     * Aufzeigen von aufgetretenen Ereignissen während des Programmablaufs
     * Methode darf nur von grafischen Threads aufgerufen werden
     *
     * @param title Title des Alert Fensters
     * @param headerMessage Title innerhalb des Fensters
     * @param message einzublendender Text
     * @param type Typ
     * @param expanded bei true wird das Fenster mithilfe einer TextArea
     * erweitert
     */
    public static void showMessage(String title, String headerMessage, String message, Alert.AlertType type, boolean expanded) {
        //Normale Schrift 
        showMessage(title, headerMessage, message, type, expanded, "Arial", FontWeight.MEDIUM, FontPosture.ITALIC, 12);
    }

    public static ButtonType showJesNoDialog(String text, String title) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(null);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().add(ButtonType.NO);
        alert.showAndWait();
        return alert.getResult();
    }

    public static void setRedErrorBorder(TextField txtField) {
        fehlerCount.add(txtField);
        txtField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
    }

    public static void removeRedErrorBorder(TextField txtField) {
        fehlerCount.remove(txtField);
        txtField.setStyle("-fx-border-color: none; -fx-border-width: 2px;");
    }
}
