package Gui;

import Application.Controller.Controller;
import Application.Models.*;
import com.sun.source.tree.CatchTree;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class SalgsPane extends GridPane {

    private Controller controller = Controller.getController();
    private ToggleGroup groupBetalingsform = new ToggleGroup();
    private ToggleGroup groupRabat = new ToggleGroup();
    private TextField txfprocentRabat, txfFastRabat,txfTotalPris,txfRegning;
    private ComboBox<Varetype> cbbVareType;

    private ComboBox<Vare> cbbKlippekort;
    private ComboBox<String> cbbprisgrupper;
    private ListView<Vare> lvwKurv, lvwValgteVare;
    private Button btnTilføj,btnRemove,  btnLavSalg;
    private VBox vbox;

    public SalgsPane(){
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        createLabels(this);

        createComboboxVareType(this);

        createListviewValgteVare(this);
        
        createComboboxKlippekort(this);

        createRadioButtons(this);

        createListviewKurv(this);

        createButtons(this);

        createComboboxPrisgruppe(this);

        createTextfields(this);
    }

    private void createTextfields(SalgsPane salgsPane) {
        txfFastRabat = new TextField("Indtast fast rabat:");
        txfFastRabat.setEditable(false);

        txfprocentRabat = new TextField("Indtast procentvis rabat:");
        txfFastRabat.setEditable(false);
        VBox vBox = new VBox();
        this.add(vBox, 4, 4);
        vBox.getChildren().add(txfprocentRabat);
        vBox.getChildren().add(txfFastRabat);



        txfTotalPris = new TextField("0");
        txfTotalPris.setEditable(false);
        this.add(txfTotalPris, 4, 1);

        //Todo
        /*
        Vi skal have lavet nedenstående metode:
          //txfTotalPris.setText();
          setText() - Skal være prisen på varene i kurven kombineret.
         */

        txfRegning = new TextField("Indtast navn til regning");
        vbox.getChildren().add(txfRegning);
        disableRegningAction();

    }

    private void createComboboxPrisgruppe(SalgsPane salgsPane) {
        cbbprisgrupper = new ComboBox<>();
        this.add(cbbprisgrupper,0,1 );
        cbbprisgrupper.setOnAction(event -> enableVareTypeAction());
        cbbprisgrupper.getItems().addAll(controller.getPrisgrupperByName());
    }

    private void enableVareTypeAction() {
        cbbVareType.setDisable(false);
    }

    private void createButtons(SalgsPane salgsPane) {
        btnTilføj = new Button("Tilføj vare til kurv");
        this.add(btnTilføj, 2, 2);
        btnTilføj.setOnAction(event -> updateKurvAction());

        btnRemove = new Button("Fjern vare fra kruv");
        this.add(btnRemove, 3, 2);
        btnRemove.setOnAction(event -> removeKurvAction());

        btnLavSalg = new Button("Lav salg");
        this.add(btnLavSalg, 5, 5);
        btnLavSalg.setOnAction(event -> lavSalgAction());
        //todo
        /*
        skal have en action som sætter salget ind i systemet or something
         */
    }

    private void lavSalgAction() {
        HashMap<Vare, Integer> varer = new HashMap<>();
        for(int i =0;i<lvwKurv.getItems().size();i++){
            if(varer.containsKey(lvwKurv.getItems().get(i))){
                varer.put(lvwKurv.getItems().get(i), varer.get(lvwKurv.getItems().get(i))+1);
            } else {
                varer.put(lvwKurv.getItems().get(i), 1);
            }
        }
        if(groupBetalingsform.getSelectedToggle() != null) {
            Betalingsform bform = Betalingsform.valueOf(groupBetalingsform.getSelectedToggle().getUserData().toString());
            double total = Double.parseDouble(txfTotalPris.getText());
            if(groupRabat.getSelectedToggle().getUserData() == FastRabat.class || groupRabat.getSelectedToggle().getUserData() ==ProcentRabat.class){
                //laver rabat objekt.
                if(groupRabat.getSelectedToggle().getUserData().equals(FastRabat.class)){
                   Rabat rabat = Controller.createFastRabat(Double.parseDouble(txfTotalPris.getText()));
                    Controller.createProduktSalg(varer, bform, total, rabat);
                    salgOprettetMedRabatMessage(rabat);

                } else if (groupRabat.getSelectedToggle().getUserData().equals(ProcentRabat.class)){
                    Rabat rabat = Controller.createProcentRabat(Double.parseDouble(txfTotalPris.getText()));

                    Controller.createProduktSalg(varer, bform, total, rabat);
                    salgOprettetMedRabatMessage(rabat);

                }
                Controller.saveStorageToFile();
            } else {
                Controller.createProduktSalg(varer, bform, total, null);
                Controller.saveStorageToFile();
                salgOprettetMessage();
            }
        } else {
            errormessageBetalingform();
        }
    }

    private void salgOprettetMedRabatMessage(Rabat rabat) {
        String message = "Salget er oprettet, total prisen blev: " +  rabat.beregnRabat(Double.parseDouble(txfTotalPris.getText()));
        JOptionPane.showMessageDialog(new JFrame(), message,"Oprettet",JOptionPane.INFORMATION_MESSAGE);
    }

    private void salgOprettetMessage() {
        String message = "Salget er oprettet, total prisen blev: " + txfTotalPris.getText();
        JOptionPane.showMessageDialog(new JFrame(), message,"Oprettet",JOptionPane.INFORMATION_MESSAGE);
    }

    private void errormessageBetalingform() {
        String message = "Husk at vælge betalingsmetode.";
        JOptionPane.showMessageDialog(new JFrame(), message,"Fejl",JOptionPane.ERROR_MESSAGE);
    }

    private void removeKurvAction() {
        Vare valgtVare = lvwKurv.getSelectionModel().getSelectedItem();
        if(valgtVare == null){
            errorMessageRemove();
        }
        lvwKurv.getItems().remove(valgtVare);
    }

    private void errorMessageRemove() {
        String message = "Du skal vælge en vare først i ''Kurv'' listen";
        JOptionPane.showMessageDialog(new JFrame(), message,"Fejl",JOptionPane.ERROR_MESSAGE);
    }

    private void updateKurvAction() {

            Vare ValgtVare = lvwValgteVare.getSelectionModel().getSelectedItem();
            if(ValgtVare ==null) {
                errorMessageTilføj();
            }
            lvwKurv.getItems().add(ValgtVare);

            //Hashmap af alle vores vare.
        HashMap<Vare, Integer> varer = new HashMap<>();
        for(int i =0;i<lvwKurv.getItems().size();i++){
            if(varer.containsKey(lvwKurv.getItems().get(i))){
                varer.put(lvwKurv.getItems().get(i), varer.get(lvwKurv.getItems().get(i))+1);
            } else {
                varer.put(lvwKurv.getItems().get(i), 1);
            }
        }
//            opdatere totalPris.
            txfTotalPris.setText(String.valueOf(controller.totalPris(cbbprisgrupper.getSelectionModel().getSelectedItem(),varer)));

    }

    private void errorMessageTilføj() {
        String message = "Du skal vælge en vare først i ''Valgte Vare'' listen";
        JOptionPane.showMessageDialog(new JFrame(), message,"Fejl",JOptionPane.ERROR_MESSAGE);
    }

    private void createListviewKurv(SalgsPane salgsPane) {
        lvwKurv = new ListView<>();
        this.add(lvwKurv, 3, 1);
    }

    private void createListviewValgteVare(SalgsPane salgsPane) {
        lvwValgteVare = new ListView<>();
        this.add(lvwValgteVare, 2, 1);
    }

    private void createRadioButtons(SalgsPane salgsPane) {
       //Vbox til betalingsMetoder
        VBox box = new VBox();
        this.add(box, 1, 4);
        Betalingsform[] betalingsForm = Betalingsform.values();

        for(int i =0;i<betalingsForm.length;i++){
            RadioButton rb =new RadioButton();
            box.getChildren().add(rb);
            rb.setText((betalingsForm[i].toString()));
            rb.setUserData(betalingsForm[i]);
            rb.setToggleGroup(groupBetalingsform);
            //todo
            //UserData skal på en eller anden måde kobles til ordren.
            if(rb.getUserData().equals(Betalingsform.KLIPPEKORT)){
                rb.setOnAction(event -> enableKlippekortDisableRegningAction());
            } else if (rb.getUserData().equals(Betalingsform.REGNING)) {
                rb.setOnAction(event -> enableRegningDisableKlippekortAction());            }
            else {
                rb.setOnAction(event -> disableRegningAndKlippekortAction());
            }
        }
        VBox box1 = new VBox();
        this.add(box1, 3, 4);
        RadioButton rbProcent = new RadioButton("Procent Rabat");
        rbProcent.setUserData(ProcentRabat.class);
        box1.getChildren().add(rbProcent);
        rbProcent.setToggleGroup(groupRabat);
        rbProcent.setOnAction(event -> updateRbProcentAction());

        RadioButton rbFast = new RadioButton("Fast Rabat");
        box1.getChildren().add(rbFast);
        rbFast.setUserData(FastRabat.class);
        rbFast.setToggleGroup(groupRabat);
        rbFast.setOnAction(event -> updateRbFastAction());

    }

    private void enableRegningDisableKlippekortAction() {
        txfRegning.clear();
        txfRegning.setDisable(false);
        cbbKlippekort.setDisable(true);
    }

    private void enableKlippekortDisableRegningAction() {
        cbbKlippekort.setDisable(false);
        txfRegning.setDisable(true);
        txfRegning.setText("Indtast navn til regning");
    }

    private void disableRegningAndKlippekortAction() {
        txfRegning.setDisable(true);
        txfRegning.setText("Indtast navn til regning");
        cbbKlippekort.setDisable(true);
    }

    private void disableRegningAction() {
        txfRegning.setDisable(true);
        txfRegning.setText("Indtast navn til regning");
    }

    private void updateRbFastAction() {
        txfFastRabat.setEditable(true);
        txfFastRabat.clear();
        txfprocentRabat.setEditable(false);
        txfprocentRabat.setText("Indtast procentvis rabat:");
    }

    private void updateRbProcentAction() {
        txfprocentRabat.setEditable(true);
        txfprocentRabat.clear();
        txfFastRabat.setEditable(false);
        txfFastRabat.setText("Indtast fast rabat:");
    }

    private void createComboboxKlippekort(SalgsPane salgsPane) {
        cbbKlippekort = new ComboBox<>();
        vbox = new VBox();
        vbox.getChildren().add(cbbKlippekort);
        this.add(vbox, 2, 4);
        cbbKlippekort.getItems().addAll(controller.getKlippekort());
        cbbKlippekort.setDisable(true);
        cbbKlippekort.setPromptText("Vælg klippekort");
    }

    private void createComboboxVareType(SalgsPane salgsPane) {
        cbbVareType = new ComboBox<>();
        this.add(cbbVareType, 1, 1);
        cbbVareType.getItems().addAll(Varetype.values());
        cbbVareType.setOnAction(event -> UpdateVagteVareAction());
//        cbbVareType.setDisable(true);
        //todo
        //fjern kommentar når prisgrupper fungere.
    }

    private void UpdateVagteVareAction() {
        //Nulstiller listen
        lvwValgteVare.getItems().remove(0, lvwValgteVare.getItems().size());


        ArrayList<Vare> valgteVare = new ArrayList<>();
        ArrayList<Vare> alleVare = new ArrayList<>(controller.getVarer());
        controller.setActivePrisgruppe(cbbprisgrupper.getSelectionModel().getSelectedItem());
        for(int i =0;i< alleVare.size();i++) {
                if (alleVare.get(i).getVaretype() == cbbVareType.getValue() && !alleVare.get(i).toString().contains("NaN")) {
                    valgteVare.add(alleVare.get(i));
                }
        }

        lvwValgteVare.getItems().addAll(valgteVare);
    }

    private void createLabels(SalgsPane salgsPane) {
        Label lblPrisgrupper = new Label("Prisgruppe:");
        this.add(lblPrisgrupper, 0, 0);

        Label lblVaregrupper = new Label("VareGrupper:");
        this.add(lblVaregrupper, 1, 0);

        Label lblValgteVare = new Label("Valgte Vare: ");
        this.add(lblValgteVare, 2, 0);

        Label lblKurv = new Label("Kurv");
        this.add(lblKurv, 3, 0);

        Label lblBetalingMetoder = new Label("BetalingsMetoder: ");
        this.add(lblBetalingMetoder, 0, 4);

        Label lblTotalPris = new Label("Total Pris:");
        this.add(lblTotalPris, 4, 0);
    }
}
