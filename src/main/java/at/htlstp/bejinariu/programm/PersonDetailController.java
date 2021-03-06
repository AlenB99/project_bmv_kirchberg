/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlstp.bejinariu.programm;

import at.htlstp.bejinariu.datamanager.HibernateDataMananger;
import at.htlstp.bejinariu.graphictools.Utilities;
import at.htlstp.bejinariu.models.Kleidungsstueck;
import at.htlstp.bejinariu.models.Person;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

//Das ist ein Kommentar 
/**
 * FXML Controller class
 *
 * @author Dru
 */
public class PersonDetailController implements Initializable {

    @FXML
    private AnchorPane anchor_main;
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
    private Person aktPerson = null;
    private boolean checkForChange = false;
    @FXML
    private ChoiceBox<Kleidungsstueck.Status> choise_hut;
    @FXML
    private DatePicker dpick_hut;
    @FXML
    private TextField fld_hut;
    private final static List<Kleidungsstueck.Status> MOEGLICHKEITEN = new LinkedList<>();
    private final static List<Kleidungsstueck.Groesse> GROESSEN = new LinkedList<>();

    private final static int HHHG_MINSIZE = 0; //Min Size für Hut Hose Hemd Gürtel
    private final static int HHHG_MAXSIZE = 100;//Max Size für Hut Hose Hemd Gürtel

    private final static double SCHUHE_MINSIZE = 0.0; //Min Size für Schuhe
    private final static double SCHUHE_MAXSIZE = 100.0;//Max Size für Schuhe
    private final static Comparator<Person> nameComparator; //Comparator fürs Sortieren nach dem Familiennamen
    private final LocalDate DATEMIN = LocalDate.of(1900, 1, 1);
    private static final ChangeListener<TextField> changerInt;

    static {
        MOEGLICHKEITEN.add(Kleidungsstueck.Status.Beim_Verein);
        MOEGLICHKEITEN.add(Kleidungsstueck.Status.Nicht_im_Besitz);
        MOEGLICHKEITEN.add(Kleidungsstueck.Status.Beim_Mitglied);

        GROESSEN.addAll(Arrays.asList(Kleidungsstueck.Groesse.XXS, Kleidungsstueck.Groesse.XS, Kleidungsstueck.Groesse.XXXL, Kleidungsstueck.Groesse.S, Kleidungsstueck.Groesse.M, Kleidungsstueck.Groesse.L, Kleidungsstueck.Groesse.XL, Kleidungsstueck.Groesse.XXL));

        nameComparator = (p1, p2) -> {
            int diff = p1.getNachname().compareTo(p2.getNachname());
            if (diff == 0) {
                return p1.getVorname().compareTo(p2.getNachname());
            }
            return diff;

        };
        changerInt = null;
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
    private final List<DatePicker> datePickers = new ArrayList<>();
    @FXML
    private ListView<Person> lstview_personen;
    @FXML
    private TextArea area_zk;
    @FXML
    private Button btn_loesche;
    private ObservableList<Person> people;
    @FXML
    private RadioButton rdb_markentender;
    @FXML
    private Button btn_neu;
    @FXML
    private Label lbl_info;
    private RadioButton rdb_aufsteigend;
    private RadioButton rdb_absteigend;
    @FXML
    private TextField fld_FilterName;
    private Map<Node, ChoiceBox<Kleidungsstueck.Status>> infos = new HashMap<>();
    @FXML
    private Button btn_sort;
    @FXML
    private TitledPane pane_Verwaltungstools;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            //Voreinstellungen
            infos.put(fld_buendel, choise_buendel);
            infos.put(fld_dirndl, choise_dirndl);
            infos.put(fld_gillette, choise_gillette);
            infos.put(fld_guertel, choise_guertel);
            infos.put(fld_hemd, choise_hemd);
            infos.put(fld_hose, choise_hose);
            infos.put(fld_hut, choise_hut);
            infos.put(fld_schuhe, choise_schuhe);
            infos.put(fld_tracht, choise_tracht);
            infos.put(choise_wjacke_groesse, choise_wjacke);

            datePickers.addAll(Arrays.asList(dpick_buendel, dpick_dirndl, dpick_gillette, dpick_guertel, dpick_hemd, dpick_hose, dpick_hut, dpick_schuhe, dpick_tracht, dpick_wjacke));
            infos.values().forEach((ks) -> ks.setItems(FXCollections.observableArrayList(MOEGLICHKEITEN)));
            choise_wjacke_groesse.setItems(FXCollections.observableArrayList(GROESSEN));

            instance = HibernateDataMananger.getINSTANCE();
            //Fehlerquellen werdne hier anerkannt 
            textFieldToIntField(fld_hut, HHHG_MINSIZE, HHHG_MAXSIZE); //Nur INTs(0-100) werden erlaubt
            textFieldToIntField(fld_hemd, HHHG_MINSIZE, HHHG_MAXSIZE); //Nur INTs(0-100) werden erlaubt
            textFieldToIntField(fld_hose, HHHG_MINSIZE, HHHG_MAXSIZE);//Nur INTs(0-100) werden erlaubt
            textFieldToIntField(fld_guertel, HHHG_MINSIZE, HHHG_MAXSIZE); //Nur INTs(0-100) werden erlaubt

            textFieldToTeleField(fld_telefonnr);           //Nur Zahlen werden erlaubt 
            textFieldToEmailField(fld_email);               //Nur eine gängige Email wird zugelassen 
            textFieldToDoubleField(fld_schuhe, SCHUHE_MINSIZE, SCHUHE_MAXSIZE); //Nur Doubles(0.0-100.0) werden erlaubt
            textFieldName(fld_vorname);                        //Nur Buchstaben 
            textFieldName(fld_nachname);                       //Nur Buchstaben 

            //Nur Daten ab 1900
            datePickers.stream().forEach(d -> dPickInit(d, DATEMIN));

            //BusinessRule: Mitarbeiter besitzt kleidungsstuecke nicht 
            for (Node key : infos.keySet()) {
                infos.get(key).valueProperty().addListener((o, oldV, newV) -> {
                    if (newV == null) {
                        return;
                    }
                    if (newV.equals(Kleidungsstueck.Status.Nicht_im_Besitz)) {
                        key.disableProperty().set(true);
                        if (key instanceof TextField) {
                            ((TextField) key).setText("");
                            Utilities.removeRedErrorBorder((TextField) key);
                        } else {
                            ((ChoiceBox<Kleidungsstueck.Groesse>) key).getSelectionModel().select(null);
                        }
                    } else if (oldV != null && oldV.equals(Kleidungsstueck.Status.Nicht_im_Besitz)) {
                        key.disableProperty().set(false);
                        if (key instanceof TextField) {
                            if (((TextField) key).isEditable()) {
                                ((TextField) key).setText("0");
                            } else if (key == fld_tracht || key == fld_gillette || key == fld_dirndl) {
                                ((TextField) key).setText("maßgeschneidert");
                            } else {
                                ((TextField) key).setText("keine Größe");
                            }
                        } else {
                            ((ChoiceBox<Kleidungsstueck.Groesse>) key).getSelectionModel().selectFirst();
                        }
                        key.requestFocus();
                    }
                });
            }
            area_zk.textProperty().addListener((ob, oldV, newV) -> {
                if (newV == null) {
                    return;
                }
                if (newV.length() > 255) {
                    Utilities.setRedErrorBorder(area_zk);
                } else {
                    Utilities.removeRedErrorBorder(area_zk);
                }
            });

            //Alle Personen werden in die Liste geladen, Liste wird mit der ListView gekoppelt 
            people = FXCollections.observableArrayList((ArrayList<Person>) instance.loadAll());

            //  people = instance.loadTestData(); 
            if (people == null) {
                people = FXCollections.observableArrayList();
            }
            lstview_personen.setItems(people);

            //Listener für die Liste 
            lstview_personen.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
                if (oldV != null && aktPerson != null) {
                    remember(aktPerson);
                }
                if (newV == null) {
                    //Deselektieren verhindern 
                    lstview_personen.getSelectionModel().select(oldV);
                } else {
                    //Details der Person werden in die Grafik geladen 
                    aktPerson = newV;
                    for (Node key : infos.keySet()) {
                        key.setDisable(false);
                        infos.get(key).setValue(null);
                    }
                    setPersonDetails(aktPerson);
                }
            });

            //Aufzeigen von Fehlermeldungen in Echtzeit 
            Utilities.fehlerCountProperty().addListener((Observable observable) -> {
                if (Utilities.fehlerCountProperty().isEmpty()) {
                    lbl_info.setText("Info: Alle Eingaben sind richtig*");
                    lbl_info.setVisible(true);
                    lbl_info.setTextFill(Color.web("green"));
                } else {
                    lbl_info.setText("Info: " + Utilities.fehlerCountProperty().size() + " Textfelder beinhalten falsche Werte, sind leer oder beinhalten zu viel Text!*");
                    lbl_info.setVisible(true);
                    lbl_info.setTextFill(Color.web("red"));
                }
            });

            //Wenn keine Personen in der Datenbank vorhanden sind    
            if (people.size() > 0) {
                //Erstes Element selektieren(immer vorhanden)
                lstview_personen.getSelectionModel().selectFirst();
                onActionSortPersonen(null);
            } else {
                //Eine neue Person wird benötigt 
                onActionNeuePerson(null);
            }
            
            
            System.out.println("Initialised");
        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getClass());
        }

    }

    public void setPersonDetails(Person p) {
        //Kontaktdaten 

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
        //Elemente werden nur aus der observableList people gelöscht, niemals aus der 
        //Liste die man durch listview_personen.getItems() bekommt. Grund: beim Sortieren und Filtern werden
        //Wrapperlisten verwendet. Diese werden dann der ListView zugewiesen. Aus SortedLists und FilteredList
        //kann weder geöscht noch eingefügt werden.
        ButtonType answer = at.htlstp.bejinariu.graphictools.Utilities.showJesNoDialog("Sollten die Daten der aktuellen Person gespeichert werden?", "Bestätigung");
        if (ButtonType.YES.equals(answer) && Utilities.fehlerCountProperty().isEmpty()) {
            getPersonDetails(aktPerson);
            if (aktPerson.getPersonId() == null) {
                //Speichern einer neuen Person 
                instance.storePerson(aktPerson);
                //Person wird in die aktuelle Liste ebenfalls aufgenommen 
                people.add(aktPerson);
                //Person selektieren
                lstview_personen.getSelectionModel().select(aktPerson);
            } else {
                //Aktualisieren 
                instance.refreshPerson(aktPerson);
            }
            lstview_personen.refresh();
        } else if (ButtonType.YES.equals(answer) && !Utilities.fehlerCountProperty().isEmpty()) {   //Speichern nicht möglich 
            Utilities.showMessage("Fehler", "Speichern war nicht erfolgreich", "Es sind " + Utilities.fehlerCountProperty().size() + " Fehler aufgetreten, bitte überprüfen Sie die Felder auf Gültigkeit und speichern Sie danach erneut", Alert.AlertType.ERROR, false);
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
        remember(aktPerson);   //Falls die Aktuelel Person verändert 
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

        if (groesseNodeClass.equals(TextField.class)) {
            ks.setKleidungsgroesse(((TextField) groesse).getText().trim());
        } else if (groesseNodeClass.equals(ChoiceBox.class)) {
            Kleidungsstueck.Groesse value = ((ChoiceBox<Kleidungsstueck.Groesse>) groesse).getValue();
            ks.setKleidungsgroesse(value == null ? "" : value.toString());
        }

    }

    private void speichereKleidungsstueckInDieGrafik(Kleidungsstueck ks, ChoiceBox<Kleidungsstueck.Status> choise, DatePicker dpick, Node node, Class<?> groesseNodeClass) {

        if (groesseNodeClass.equals(TextField.class)) {
            ((TextField) node).setText(ks.getKleidungsgroesse());

        } else if (groesseNodeClass.equals(ChoiceBox.class)) {
            if (ks.getKleidungsgroesse().isEmpty()) {
                ((ChoiceBox<Kleidungsstueck.Groesse>) node).setValue(null);
            } else {
                ((ChoiceBox<Kleidungsstueck.Groesse>) node).setValue(Kleidungsstueck.Groesse.valueOf(ks.getKleidungsgroesse()));
            }

        }
        choise.setValue(ks.getStatus());
        dpick.setValue(ks.getAenderungsdatum().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
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
        txtField.setText("0");
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
            if (newValue.isEmpty()) {
                Utilities.setRedErrorBorder(txtField);
            } else if (!newValue.matches("\\d*")) {
                Utilities.setRedErrorBorder(txtField);
            } else {
                Utilities.removeRedErrorBorder(txtField);
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
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(min)) {
                    Platform.runLater(() -> setDisable(true));
                    dpDate.setValue(min);
                }
            }
        };

        dpDate.setDayCellFactory(dayCellFactory);

    }

    @FXML
    private void onActionLoeschen(ActionEvent event) {
        //Elemente werden nur aus der observableList people gelöscht, niemals aus der 
        //Liste die man durch listview_personen.getItems() bekommt. Grund: beim Sortieren und Filtern werden
        //Wrapperlisten verwendet. Diese werden dann der ListView zugewiesen. Aus SortedLists und FilteredList
        //kann weder geöscht noch eingefügt werden.
        ButtonType response = Utilities.showJesNoDialog("Person wirklich unwiderruflich löschen?", "Bestätigung benötigt");
        System.out.println(aktPerson);
        Person loesche = aktPerson;
        if (response.equals(ButtonType.YES)) {
            int index = lstview_personen.getItems().indexOf(aktPerson);  //Index der Person in der Liste 
            if (aktPerson.getPersonId() != null) {
                instance.deletePerson(aktPerson);   //Person aus der Datenbank löschen, wenn nötig
            }

            if (people.contains(aktPerson)) {
                aktPerson = null;
                lstview_personen.getSelectionModel().select(null);
                //Person wird von der Liste gestrichen 

                people.remove(loesche);
            }

            lstview_personen.getSelectionModel().select(index);

            if (people.isEmpty()) {
                newPerson();    //Keine Elemente vorhanden
            }

            if (lstview_personen.getItems().isEmpty() && !lstview_personen.getItems().equals(people)) { //Bei der Suche 
                anchor_main.setDisable(true);
            }

        }

    }

    @FXML
    private void onActionSingleReportGenerate(ActionEvent event) {
        remember(aktPerson);    //Änderungen bei Bedarf vorher speichern 
        ReportGenerator.newReport(aktPerson);
    }

    @FXML
    private void onActionSortPersonen(ActionEvent event) {

        if ("⬇".equals(btn_sort.getText())) {
            lstview_personen.setItems(lstview_personen.getItems().sorted(nameComparator));
            btn_sort.setText("⬆");
        } else {
            lstview_personen.setItems(lstview_personen.getItems().sorted((p1, p2) -> -nameComparator.compare(p1, p2)));
            btn_sort.setText("⬇");
        }
        lstview_personen.refresh();
    }

    @FXML
    private void onActionNeuePerson(ActionEvent event) {
        newPerson();
    }

    public void newPerson() {
        System.out.println("Aufgerufen");
        remember(aktPerson);
        aktPerson = new Person();
        fld_nachname.setText("");
        fld_vorname.setText("");
        fld_email.setText("");
        fld_telefonnr.setText("");
        fld_hose.setText("");
        fld_hemd.setText("");
        fld_hut.setText("");
        fld_guertel.setText("");
        fld_schuhe.setText("");
        choise_wjacke_groesse.getSelectionModel().selectFirst();
        datePickers.stream().forEach(dp -> dp.setValue(LocalDate.now()));
        infos.values().stream().forEach(ch -> ch.setValue(Kleidungsstueck.Status.Beim_Verein));
        area_zk.setText("");
        rdb_markentender.setSelected(false);
    }

    private void textFieldName(TextField f) {
        f.setText("0");
        f.textProperty().addListener((obs, olD, newV) -> {
            if (newV.isEmpty()) {
                Utilities.setRedErrorBorder(f);
            } else if (!newV.matches("[a-zA-ZäöüÄÖÜ ]+")) {
                Utilities.setRedErrorBorder(f);
            } else {
                Utilities.removeRedErrorBorder(f);
            }
        });

    }

    private void remember(Person aktPerson) {
        if (aktPerson == null) {
            return;
        }
        Person person = new Person();
        getPersonDetails(person);

        person.setPersonId(aktPerson.getPersonId());
        if (!person.deepEquals(aktPerson)) {
            onActionSpeichern(null);
            System.out.println("Grafik: " + person.toStringAdvanced());
            System.out.println("Akt: " + aktPerson.toStringAdvanced());
        }
    }

    private int i = 0;

    @FXML
    private void onActionFiltern(KeyEvent event) {

        FilteredList<Person> filteredData = new FilteredList<>(people, s -> true);

        String filter = fld_FilterName.getText().trim();
        if (filter == null || filter.length() == 0) {
            filteredData.setPredicate(s -> true);
        } else {
            filteredData.setPredicate(s -> s.toString().toLowerCase().contains(filter.toLowerCase()));
        }
        anchor_main.setDisable(filteredData.isEmpty());

        lstview_personen.setItems(filteredData);
        onActionSortPersonen(null);
    }

    @FXML
    private void onActionBulkyReport(ActionEvent event) {
        remember(aktPerson);
        ReportGenerator.newReport(null);
    }

    public void readingUser() {
        pane_Verwaltungstools.setCollapsible(false);
        pane_Verwaltungstools.setText("Willkommen");

    }

    @FXML
    private void onActionCredits(ActionEvent event) {
        try {
            File video = new File(this.getClass().getResource("/videos/video.mp4").getFile());
            System.out.println(video.exists());
            CreditsGenerator.showCredits(new Stage(StageStyle.UNDECORATED), video);
        } catch (Exception e) {
            System.out.println("URL falsch" + e.getMessage());
        }
    }

}
