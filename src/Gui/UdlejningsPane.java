package Gui;

import Application.Controller.Controller;
import Application.Models.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class UdlejningsPane  extends GridPane {

    private Controller controller = Controller.getController();
    private ToggleGroup groupBetalingsform = new ToggleGroup();
    private ToggleGroup groupRabat = new ToggleGroup();
    private TextField txfprocentRabat, txfFastRabat,txfTotalPris, txfStartDato, txfSlutDato, txfPant;
    private ComboBox<Varetype> cbbVareType;

    private ComboBox<String> cbbprisgrupper;
    private ListView<Vare> lvwKurv, lvwValgteVare;
    private Button btnTilføj,btnRemove,  btnLavSalg, btnStartDato, btnSlutDato;

    public UdlejningsPane(){
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        createLabels(this);

        createComboboxVareType(this);

        createListviewValgteVare(this);

        createRadioButtons(this);

        createListviewKurv(this);

        createButtons(this);

        createComboboxPrisgruppe(this);

        createTextfields(this);


    }

    private void DatepickerAction(int metode) {
        Stage stage = new Stage();
        stage.setTitle("Vælg en dato");

        TilePane r = new TilePane();

        DatePicker da = new DatePicker();
        if(metode == 2) {
            //Hvis der ikke er valgt dato i txfStartDato
            LocalDate minDate = LocalDate.now();
            LocalDate maxDate = LocalDate.now().plusWeeks(4);
            //Hvis der er valgt dato i txfStartDato
            try {
                minDate = LocalDate.parse(txfStartDato.getText());
                maxDate = LocalDate.parse(txfStartDato.getText()).plusDays(14);
            }
            catch (NullPointerException | DateTimeParseException e ){
                System.out.println(e.getMessage());
                System.out.println("Start dato ikke valgt");
            }
            LocalDate finalMaxDate = maxDate;
            LocalDate finalMinDate = minDate;
            da.setDayCellFactory(d ->
                    new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            setDisable(item.isAfter(finalMaxDate) || item.isBefore(finalMinDate));
                        }
                    });
        }
        r.getChildren().add(da);

        Scene sc = new Scene(r, 200, 200);
        Button btnOk = new Button("ok");
        r.getChildren().add(btnOk);
        btnOk.setOnAction(event -> stage.close());

        stage.setScene(sc);

        stage.showAndWait();

        if(metode ==1) {
            txfStartDato.setText(String.valueOf(da.getValue()));
            stage.close();
            btnStartDato.setDisable(true);
        } else {
            txfSlutDato.setText(String.valueOf(da.getValue()));
            stage.close();
            btnSlutDato.setDisable(true);
        }
    }

    private void createTextfields(UdlejningsPane udlejningsPane) {
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

        txfPant = new TextField("0");
        txfPant.setEditable(false);
        this.add(txfPant, 5, 1);

        txfStartDato = new TextField();
        txfStartDato.setText("00-00-00");
        txfSlutDato = new TextField();
        txfSlutDato.setText("00-00-00");
        VBox vboxDato = new VBox();
        this.add(vboxDato, 5, 2);
        vboxDato.getChildren().add(txfStartDato);
        vboxDato.getChildren().add(txfSlutDato);
    }

    private void createComboboxPrisgruppe(UdlejningsPane udlejningsPane) {
        cbbprisgrupper = new ComboBox<>();
        this.add(cbbprisgrupper,0,1 );
        ArrayList<Vare> v1 = controller.getVarer();
        ArrayList<Prisgruppe> prisgrupper = new ArrayList<>();
        for(Vare v : v1){
            if(v instanceof Udlejningsvare){
                for(int i =0;i<v.getPrisgrupper().size();i++)
                if(!cbbprisgrupper.getItems().contains(String.valueOf(v.getPrisgrupper().get(i)))){
                    cbbprisgrupper.getItems().add(String.valueOf(v.getPrisgrupper().get(i)));
                }
            }
        }
        //Fjerner manuelt fredagsbar.. Det giver simpelthen ikke mening, at have den i listen.
        cbbprisgrupper.getItems().remove("Fredagsbar");
        cbbprisgrupper.setOnAction(event ->  enableVareTypeAction());
    }

    private void enableVareTypeAction() {
        cbbVareType.setDisable(false);

    }

    private void createButtons(UdlejningsPane udlejningsPane) {
        btnTilføj = new Button("Tilføj vare til kurv");
        this.add(btnTilføj, 2, 2);
        btnTilføj.setOnAction(event -> updateKurvAction());

        btnRemove = new Button("Fjern vare fra kruv");
        this.add(btnRemove, 3, 2);
        btnRemove.setOnAction(event -> removeKurvAction());

        btnLavSalg = new Button("Lav Udlejning");
        this.add(btnLavSalg, 5, 5);
        btnLavSalg.setOnAction(event -> lavSalgAction());

        VBox vBox = new VBox();
        this.add(vBox, 5, 3);
        btnStartDato = new Button("Vælg start Dato");
        vBox.getChildren().add(btnStartDato);
        btnStartDato.setOnAction(event -> DatepickerAction(1));

        btnSlutDato = new Button("Vælg slut Dato");
        vBox.getChildren().add(btnSlutDato);
        btnSlutDato.setOnAction(event ->  DatepickerAction(2));
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

        if(btnStartDato.isDisable() && btnSlutDato.isDisable()) {
            if (groupBetalingsform.getSelectedToggle() != null) {
                Betalingsform bform = Betalingsform.valueOf(groupBetalingsform.getSelectedToggle().getUserData().toString());
                double pant = Double.parseDouble(txfPant.getText());
                LocalDate startDato = LocalDate.parse(txfStartDato.getText());
                LocalDate slutDato = LocalDate.parse(txfSlutDato.getText());

                if (groupRabat.getSelectedToggle() != null) {
                    if (groupRabat.getSelectedToggle().getUserData().toString().contains("FastRabat")) {
                        Rabat rabat = controller.createFastRabat(Double.parseDouble(txfFastRabat.getText()));
                        controller.createUdlejning(varer, pant, startDato, slutDato, bform, rabat);
                        salgOprettetMedRabatMessage(rabat);

                    } else if (groupRabat.getSelectedToggle().getUserData().toString().contains("ProcentRabat")) {
                        Rabat rabat = controller.createProcentRabat(Double.parseDouble(txfprocentRabat.getText()));
                        controller.createUdlejning(varer, pant, startDato, slutDato, bform, rabat);
                        salgOprettetMedRabatMessage(rabat);
                    }
                    controller.saveStorageToFile();
                } else {
                        controller.createUdlejning(varer, pant, startDato, slutDato, bform, null);
                    controller.saveStorageToFile();
                    salgOprettetMessage();
                }
            } else {
                errormessageBetalingform();
            }
        } else {
            errormessageDato();
        }
    }

    private void errormessageDato() {
        String message = "Husk at vælge Start og slut dato!.";
        JOptionPane.showMessageDialog(new JFrame(), message,"Fejl",JOptionPane.ERROR_MESSAGE);
    }

    private void salgOprettetMedRabatMessage(Rabat rabat) {
        String message = "Salget er oprettet, total prisen blev: " +  rabat.beregnRabat(Double.parseDouble(txfTotalPris.getText()))

                + "\nPant på varene blev:  " + txfPant.getText();
        JOptionPane.showMessageDialog(new JFrame(), message,"Oprettet",JOptionPane.INFORMATION_MESSAGE);
    }

    private void salgOprettetMessage() {
        String message = "Salget er oprettet, total prisen blev: " + txfTotalPris.getText()
                + "\nPant på varene blev: " + txfPant.getText();
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
        if (ValgtVare == null) {
            errorMessageTilføj();
        } else {
            lvwKurv.getItems().add(ValgtVare);

            //Hashmap af alle vores vare.
            HashMap<Vare, Integer> varer = new HashMap<>();
            for (int i = 0; i < lvwKurv.getItems().size(); i++) {
                if (varer.containsKey(lvwKurv.getItems().get(i))) {
                    varer.put(lvwKurv.getItems().get(i), varer.get(lvwKurv.getItems().get(i)) + 1);
                } else {
                    varer.put(lvwKurv.getItems().get(i), 1);
                }
            }
//            opdatere totalPris og pant.
            txfTotalPris.setText(String.valueOf(controller.totalPris(cbbprisgrupper.getSelectionModel().getSelectedItem(), varer)));
            txfPant.setText(String.valueOf(controller.beregnPant(varer)));
        }
    }

    private void errorMessageTilføj() {
        String message = "Du skal vælge en vare først i ''Valgte Vare'' listen";
        JOptionPane.showMessageDialog(new JFrame(), message,"Fejl",JOptionPane.ERROR_MESSAGE);
    }

    private void createListviewKurv(UdlejningsPane udlejningsPane) {
        lvwKurv = new ListView<>();
        this.add(lvwKurv, 3, 1);
    }

    private void createListviewValgteVare(UdlejningsPane udlejningsPane) {
        lvwValgteVare = new ListView<>();
        this.add(lvwValgteVare, 2, 1);
    }

    private void createRadioButtons(UdlejningsPane udlejningsPane) {
        //Vbox til betalingsMetoder
        VBox box = new VBox();
        this.add(box, 1, 4);
        Betalingsform[] betalingsForm = Betalingsform.values();

        for(int i =0;i<betalingsForm.length;i++){
            RadioButton rb =new RadioButton();
            if(betalingsForm[i].equals(Betalingsform.KLIPPEKORT) || betalingsForm[i].equals(Betalingsform.REGNING)) {} else {
                box.getChildren().add(rb);
                rb.setText((betalingsForm[i].toString()));
                rb.setUserData(betalingsForm[i]);
                rb.setToggleGroup(groupBetalingsform);
            }
        }
        VBox box1 = new VBox();
        this.add(box1, 3, 4);
        RadioButton rbProcent = new RadioButton("Procent Rabat");
        rbProcent.setUserData("ProcentRabat");
        box1.getChildren().add(rbProcent);
        rbProcent.setToggleGroup(groupRabat);
        rbProcent.setOnAction(event -> updateRbProcentAction());

        RadioButton rbFast = new RadioButton("Fast Rabat");
        box1.getChildren().add(rbFast);
        rbFast.setUserData("FastRabat");
        rbFast.setToggleGroup(groupRabat);
        rbFast.setOnAction(event -> updateRbFastAction());

    }

    private void updateRbFastAction() {
        txfFastRabat.setEditable(true);
        txfFastRabat.clear();
        txfprocentRabat.setEditable(false);
        txfprocentRabat.setText("Indtast procentvis rabat:");
        txfFastRabat.setText("0");
    }

    private void updateRbProcentAction() {
        txfprocentRabat.setEditable(true);
        txfprocentRabat.clear();
        txfFastRabat.setEditable(false);
        txfFastRabat.setText("Indtast fast rabat:");
        txfprocentRabat.setText("0");
    }

    /**
     * Laver comboboxVareType
     * Tjekker samtlige udlejningsVare igennem for deres varetype og tilføjer dem til comboboxen.
     * @param udlejningsPane
     */
    private void createComboboxVareType(UdlejningsPane udlejningsPane) {
        cbbVareType = new ComboBox<>();
        this.add(cbbVareType, 1, 1);
        ArrayList<Vare> v1 = controller.getVarer();
        ArrayList<Varetype> varetyper = new ArrayList<>();
        for(Vare v : v1){
            if(v instanceof Udlejningsvare){
            if(!varetyper.contains(v.getVaretype())){
                varetyper.add(v.getVaretype());
            }
            }
        }
        cbbVareType.getItems().addAll(varetyper);
        cbbVareType.setOnAction(event -> UpdateVagteVareAction());
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

    private void createLabels(UdlejningsPane udlejningsPane) {
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

        Label lblPant = new Label("Pant:");
        this.add(lblPant, 5, 0);
    }
}
