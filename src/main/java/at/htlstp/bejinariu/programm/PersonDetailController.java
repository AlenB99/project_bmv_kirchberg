/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.bejinariu.programm;

import at.htlstp.bejinariu.datamanager.HibernateDataMananger;
import at.htlstp.bejinariu.graphictools.Utilities;
import static at.htlstp.bejinariu.graphictools.Utilities.fehlerCount;
import at.htlstp.bejinariu.models.Kleidungsstueck;
import at.htlstp.bejinariu.models.Person;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

//Das ist ein Kommentar 
/**
 * FXML Controller class
 *
 * @author Dru
 */
public class PersonDetailController implements Initializable {

    @FXML
    private TextField fld_vorname;
    @FXML
    private TextField fld_nachname;
    @FXML
    private TextField fld_telefonnr;
    @FXML
    private TextField fld_email;
    @FXML
    private Button btn_speichern;
    private HibernateDataMananger instance;
    private Person aktPerson;
    @FXML
    private ChoiceBox<Kleidungsstueck.Status> choise_hut;
    @FXML
    private DatePicker dpick_hut;
    @FXML
    private TextField fld_hut;
    private final static List<Kleidungsstueck.Status> MOEGLICHKEITEN = new LinkedList<>();
    private final static List<Kleidungsstueck.Groesse> GROESSEN = new LinkedList<>();

    private final int HHHG_MINSIZE = 0; //Min Size für Hut Hose Hemd Gürtel
    private final int HHHG_MAXSIZE = 100;//Max Size für Hut Hose Hemd Gürtel

    private final long TELE_MAXSIZE = 999999999;//Max Size für Hut Hose Hemd Gürtel

    private final double SCHUHE_MINSIZE = 0.0; //Min Size für Schuhe
    private final double SCHUHE_MAXSIZE = 100.0;//Max Size für Schuhe

    private final LocalDate DATEMIN = LocalDate.of(1900, 1, 1);

    static {
        MOEGLICHKEITEN.add(Kleidungsstueck.Status.Beim_Verein);
        MOEGLICHKEITEN.add(Kleidungsstueck.Status.Nicht_im_Besitz);
        MOEGLICHKEITEN.add(Kleidungsstueck.Status.Beim_Mitglied);

        GROESSEN.add(Kleidungsstueck.Groesse.L);
        GROESSEN.add(Kleidungsstueck.Groesse.XLL);
        GROESSEN.add(Kleidungsstueck.Groesse.XL);
    }
    @FXML
    private ChoiceBox<Kleidungsstueck.Status> choise_hemd;
    @FXML
    private DatePicker dpick_hemd;
    @FXML
    private TextField fld_hemd;
    @FXML
    private ChoiceBox<Kleidungsstueck.Status> choise_hose;
    @FXML
    private DatePicker dpick_hose;
    @FXML
    private TextField fld_hose;
    @FXML
    private DatePicker dpick_wjacke;
    @FXML
    private ChoiceBox<Kleidungsstueck.Status> choise_wjacke;
    @FXML
    private ChoiceBox<Kleidungsstueck.Groesse> choise_wjacke_groesse;
    @FXML
    private ChoiceBox<Kleidungsstueck.Status> choise_tracht;
    @FXML
    private DatePicker dpick_tracht;
    @FXML
    private TextField fld_tracht;
    @FXML
    private TextField fld_buendel;
    @FXML
    private ChoiceBox<Kleidungsstueck.Status> choise_buendel;
    @FXML
    private DatePicker dpick_buendel;
    @FXML
    private TextField fld_gillette;
    @FXML
    private ChoiceBox<Kleidungsstueck.Status> choise_gillette;
    @FXML
    private DatePicker dpick_gillette;
    @FXML
    private TextField fld_dirndl;
    @FXML
    private ChoiceBox<Kleidungsstueck.Status> choise_dirndl;
    @FXML
    private DatePicker dpick_dirndl;
    @FXML
    private TextField fld_guertel;
    @FXML
    private TextField fld_schuhe;
    @FXML
    private ChoiceBox<Kleidungsstueck.Status> choise_schuhe;
    @FXML
    private ChoiceBox<Kleidungsstueck.Status> choise_guertel;
    @FXML
    private DatePicker dpick_guertel;
    @FXML
    private DatePicker dpick_schuhe;
    private final List<ChoiceBox<Kleidungsstueck.Status>> choiseBoxen = new ArrayList<>();
    @FXML
    private ListView<Person> lstview_personen;
    @FXML
    private TextArea area_zk;
    @FXML
    private Button btn_loesche;
    @FXML
    private Button btn_sort;
    private ObservableList<Person> people;
    @FXML
    private RadioButton rdb_markentender;
    @FXML
    private Button btn_report;
    @FXML
    private Button btn_neu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Voreinstellungen
            choiseBoxen.add(choise_hut);
            choiseBoxen.add(choise_buendel);
            choiseBoxen.add(choise_dirndl);
            choiseBoxen.add(choise_gillette);
            choiseBoxen.add(choise_hemd);
            choiseBoxen.add(choise_hose);
            choiseBoxen.add(choise_wjacke);
            choiseBoxen.add(choise_tracht);
            choiseBoxen.add(choise_guertel);
            choiseBoxen.add(choise_schuhe);
            for (ChoiceBox<Kleidungsstueck.Status> ks : choiseBoxen) {
                ks.setItems(FXCollections.observableArrayList(MOEGLICHKEITEN));
            }
            choise_wjacke_groesse.setItems(FXCollections.observableArrayList(GROESSEN));
            instance = HibernateDataMananger.getINSTANCE();
            //Testprogramm
            //Neue Person 
            textFieldToIntField(fld_hut, HHHG_MINSIZE, HHHG_MAXSIZE); //Nur INTs(0-100) werden erlaubt
            textFieldToIntField(fld_hemd, HHHG_MINSIZE, HHHG_MAXSIZE); //Nur INTs(0-100) werden erlaubt
            textFieldToIntField(fld_hose, HHHG_MINSIZE, HHHG_MAXSIZE);//Nur INTs(0-100) werden erlaubt
            textFieldToIntField(fld_guertel, HHHG_MINSIZE, HHHG_MAXSIZE); //Nur INTs(0-100) werden erlaubt

            textFieldToTeleField(fld_telefonnr);
            textFieldToEmailField(fld_email);
            textFieldToDoubleField(fld_schuhe, SCHUHE_MINSIZE, SCHUHE_MAXSIZE); //Nur Doubles(0.0-100.0) werden erlaubt

            people = FXCollections.observableArrayList((ArrayList<Person>) instance.loadAll());
            System.out.println(people);
            lstview_personen.setItems(people);
            System.out.println(lstview_personen.toString());
            lstview_personen.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {

                if (newV == null) {
                    lstview_personen.getSelectionModel().select(oldV);
                } else {
                    setPersonDetails(newV);
                }
            });
            if (people.size() > 0) {
                lstview_personen.getSelectionModel().selectFirst();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getClass());
        }

    }

    public void setPersonDetails(Person p) {
        //Kontaktdaten 
        aktPerson = p;
        fld_vorname.setText(p.getVorname());
        fld_nachname.setText(p.getNachname());
        fld_telefonnr.setText(p.getTelefonnr());
        fld_email.setText(p.getEmail());
        area_zk.setText(p.getAnmerkung());
        rdb_markentender.setSelected(p.isIsMarkentender());

        for (Kleidungsstueck stueck : p.getKleidungsstuecke()) {
            switch (stueck.getBezeichnung()) {
                //Kleidungsstück werden je nach Bezeichnung identifiziert
                case "Hut":
                    speichereKleidungsstueckInDieGrafik(stueck, choise_hut, dpick_hut, fld_hut, TextField.class
                    );
                    break;

                case "Hemd":
                    speichereKleidungsstueckInDieGrafik(stueck, choise_hemd, dpick_hemd, fld_hemd, TextField.class
                    );
                    break;

                case "Hose":
                    speichereKleidungsstueckInDieGrafik(stueck, choise_hose, dpick_hose, fld_hose, TextField.class
                    );
                    break;

                case "Winterjacke":
                    speichereKleidungsstueckInDieGrafik(stueck, choise_wjacke, dpick_wjacke, choise_wjacke_groesse, ChoiceBox.class
                    );
                    break;

                case "Trachtenjanker":
                    speichereKleidungsstueckInDieGrafik(stueck, choise_tracht, dpick_tracht, fld_tracht, TextField.class
                    );
                    break;

                case "Dirndl":
                    speichereKleidungsstueckInDieGrafik(stueck, choise_dirndl, dpick_dirndl, fld_dirndl, TextField.class
                    );
                    break;

                case "Bündel":
                    speichereKleidungsstueckInDieGrafik(stueck, choise_buendel, dpick_buendel, fld_buendel, TextField.class
                    );
                    break;

                case "Schuhe":
                    speichereKleidungsstueckInDieGrafik(stueck, choise_schuhe, dpick_schuhe, fld_schuhe, TextField.class
                    );
                    break;

                case "Gillette":
                    speichereKleidungsstueckInDieGrafik(stueck, choise_gillette, dpick_gillette, fld_gillette, TextField.class
                    );
                    break;

                case "Gürtel":
                    speichereKleidungsstueckInDieGrafik(stueck, choise_guertel, dpick_guertel, fld_guertel, TextField.class
                    );
                    break;

            }
        }

    }

    @FXML
    private void onActionSpeichern(ActionEvent event) {
        ButtonType answer = at.htlstp.bejinariu.graphictools.Utilities.showJesNoDialog("Änderungen speichern", "Bestätigung");
        if (ButtonType.YES.equals(answer) && fehlerCount.isEmpty()) {
            getPersonDetails(aktPerson);
            if (aktPerson.getPersonId() == null) {
                //Speichern einer neuen Person 
                instance.storePerson(aktPerson);
                people.add(aktPerson);
            } else {
                //Aktualisieren 
                instance.refreshPerson(aktPerson);
            }

        } else if (ButtonType.YES.equals(answer) && !fehlerCount.isEmpty()) {
            Utilities.showMessage("Fehler beim Speicheren", "Speichern war nicht erfolgreich", "Es sind " + fehlerCount.size() + " Fehler aufgetreten, bitte überprüfen Sie die Felder auf Gültigkeit", Alert.AlertType.ERROR, false);
        }

    }

    private void getPersonDetails(Person person) {
        //Kontaktdaten aktualisieren
        person.setEmail(fld_email.getText());
        person.setVorname(fld_vorname.getText());
        person.setNachname(fld_nachname.getText());
        person.setTelefonnr(fld_telefonnr.getText());
        person.setAnmerkung(area_zk.getText());
        person.setIsMarkentender(rdb_markentender.isSelected());

        //Kleidungsstuecksliste aktualisieren
        List<String> exist = new ArrayList<>();
        person.getKleidungsstuecke().forEach(
                k -> exist.add(k.getBezeichnung())
        );

        speichereKleidungsstueckeVonGrafik(person, "Hut", choise_hut, dpick_hut, fld_hut, TextField.class,
                exist);
        speichereKleidungsstueckeVonGrafik(person, "Hemd", choise_hemd, dpick_hemd, fld_hemd, TextField.class,
                exist);
        speichereKleidungsstueckeVonGrafik(person, "Hose", choise_hose, dpick_hose, fld_hose, TextField.class,
                exist);
        speichereKleidungsstueckeVonGrafik(person, "Winterjacke", choise_wjacke, dpick_wjacke, choise_wjacke_groesse, ChoiceBox.class,
                exist);
        speichereKleidungsstueckeVonGrafik(person, "Trachtenjanker", choise_tracht, dpick_tracht, fld_tracht, TextField.class,
                exist);
        speichereKleidungsstueckeVonGrafik(person, "Gillette", choise_gillette, dpick_gillette, fld_gillette, TextField.class,
                exist);
        speichereKleidungsstueckeVonGrafik(person, "Dirndl", choise_dirndl, dpick_dirndl, fld_dirndl, TextField.class,
                exist);
        speichereKleidungsstueckeVonGrafik(person, "Gürtel", choise_guertel, dpick_guertel, fld_guertel, TextField.class,
                exist);
        speichereKleidungsstueckeVonGrafik(person, "Bündel", choise_buendel, dpick_buendel, fld_buendel, TextField.class,
                exist);
        speichereKleidungsstueckeVonGrafik(person, "Schuhe", choise_schuhe, dpick_schuhe, fld_schuhe, TextField.class,
                exist);

    }

    public void close() {
        try {
            instance.close();
            System.out.println("HibernateJPAUtil geschlossen!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void speichereKleidungsstueckeVonGrafik(Person person, String kleidungsstueck, ChoiceBox<Kleidungsstueck.Status> status, DatePicker zeitpunkt, Node groesse, Class<?> groesseNodeClass, List<String> exist) {
        Kleidungsstueck ks;
        if (exist.contains(kleidungsstueck)) {  //Kleidungsstück gibt es bereits im Inventar der Person 
            ks = person.getKS(kleidungsstueck);

        } else {
            ks = new Kleidungsstueck();
            ks.setBezeichnung(kleidungsstueck);
            person.neuesKleidungsstueck(ks);

        }
        ks.setStatus(status.getValue());
        ks.setAenderungsdatum(Date.from(zeitpunkt.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        if (groesseNodeClass.equals(TextField.class
        )) {
            ks.setKleidungsgroesse(((TextField) groesse).getText());

        } else if (groesseNodeClass.equals(ChoiceBox.class
        )) {
            ks.setKleidungsgroesse(((ChoiceBox<Kleidungsstueck.Groesse>) groesse).getValue().toString());
        }

    }

    private void speichereKleidungsstueckInDieGrafik(Kleidungsstueck ks, ChoiceBox<Kleidungsstueck.Status> choise, DatePicker dpick, Node node, Class<?> groesseNodeClass) {

        choise.setValue(ks.getStatus());
        dpick.setValue(ks.getAenderungsdatum().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        if (groesseNodeClass.equals(TextField.class)) {
            ((TextField) node).setText(ks.getKleidungsgroesse());
        } else if (groesseNodeClass.equals(ChoiceBox.class)) {
            ((ChoiceBox<Kleidungsstueck.Groesse>) node).setValue(Kleidungsstueck.Groesse.valueOf(ks.getKleidungsgroesse()));
        }
    }

    private void textFieldToIntField(TextField txtField, int minSize, int maxSize) {
        txtField.setText(minSize + "");
        txtField.textProperty().addListener((observable, oldValue, newValue) -> {
            int intSize = 0;
            try {
                intSize = Integer.parseInt(txtField.getText());
                if (intSize < minSize) {
                    Utilities.setRedErrorBorder(txtField);
                } else if (intSize > maxSize) {
                    Utilities.setRedErrorBorder(txtField);
                } else {
                    Utilities.removeRedErrorBorder(txtField);
                }

            } catch (NumberFormatException e) {
                Utilities.setRedErrorBorder(txtField);
            }
        });
    }

    private void textFieldToEmailField(TextField txtField) {

        txtField.textProperty().addListener((observable, oldValue, newValue) -> {
            String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
            java.util.regex.Matcher m = p.matcher(newValue);
            if (m.matches()) {
                Utilities.removeRedErrorBorder(txtField);
            } else {
                Utilities.setRedErrorBorder(txtField);
            }
        });
    }

    private void textFieldToTeleField(TextField txtField) {
        txtField.setText("0");
        txtField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void textFieldToDoubleField(TextField txtField, double minSize, double maxSize) {
        txtField.setText(minSize + "");
        txtField.textProperty().addListener((observable, oldValue, newValue) -> {
            Utilities.removeRedErrorBorder(txtField);
            double doubleSize = 0;
            try {
                doubleSize = Double.parseDouble(txtField.getText());
                if (doubleSize < minSize) {
                    Utilities.setRedErrorBorder(txtField);
                } else if (doubleSize > maxSize) {
                    Utilities.setRedErrorBorder(txtField);
                } else {
                    Utilities.removeRedErrorBorder(txtField);
                }

            } catch (NumberFormatException e) {
                Utilities.setRedErrorBorder(txtField);
            }
        });
    }

    private void dPickInit(DatePicker dpDate, LocalDate min) {
        dpDate.setValue(LocalDate.now());
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item.isBefore(min)) {
                    setStyle("-fx-background-color: #ffc0cb;");
                    Platform.runLater(() -> setDisable(true));
                    dpDate.setValue(min);
                }
            }
        };

        dpDate.setDayCellFactory(dayCellFactory);

    }

    @FXML
    private void onActionLoeschen(ActionEvent event) {
    }

    @FXML
    private void onActionReportGenerate(ActionEvent event) {
    }

    @FXML
    private void onActionSortPersonen(ActionEvent event) {
    }

    @FXML
    private void onActionNeuePerson(ActionEvent event) {
    }

    public void newPerson() {
        aktPerson = new Person();
        fld_nachname.setText("Mustermann");
        fld_vorname.setText("Max");
        fld_email.setText("max@gmail.com");
        fld_telefonnr.setText("0660060606060");
        fld_hose.setText("0");
        fld_hemd.setText("0");
        fld_hut.setText("0");
        fld_guertel.setText("0");
        fld_schuhe.setText("0.0");
        choise_wjacke_groesse.getSelectionModel().selectFirst();
        dPickInit(dpick_wjacke, DATEMIN);
        dPickInit(dpick_dirndl, DATEMIN);
        dPickInit(dpick_guertel, DATEMIN);
        dPickInit(dpick_gillette, DATEMIN);
        dPickInit(dpick_buendel, DATEMIN);
        dPickInit(dpick_hemd, DATEMIN);
        dPickInit(dpick_hut, DATEMIN);
        dPickInit(dpick_hose, DATEMIN);
        dPickInit(dpick_schuhe, DATEMIN);
        dPickInit(dpick_tracht, DATEMIN);
        choise_wjacke.setValue(Kleidungsstueck.Status.Beim_Verein);
        choise_dirndl.setValue(Kleidungsstueck.Status.Beim_Verein);
        choise_guertel.setValue(Kleidungsstueck.Status.Beim_Verein);
        choise_gillette.setValue(Kleidungsstueck.Status.Beim_Verein);
        choise_buendel.setValue(Kleidungsstueck.Status.Beim_Verein);
        choise_hemd.setValue(Kleidungsstueck.Status.Beim_Verein);
        choise_hut.setValue(Kleidungsstueck.Status.Beim_Verein);
        choise_hose.setValue(Kleidungsstueck.Status.Beim_Verein);
        choise_schuhe.setValue(Kleidungsstueck.Status.Beim_Verein);
        choise_tracht.setValue(Kleidungsstueck.Status.Beim_Verein);
    }

}
