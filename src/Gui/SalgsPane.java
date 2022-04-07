package Gui;

import Application.Controller.Controller;
import Application.Models.*;
import Storage.Storage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.*;
import java.util.*;

public class SalgsPane extends GridPane {

    private Controller controller = Controller.getController();
    private ToggleGroup groupBetalingsform = new ToggleGroup();
    private ToggleGroup groupRabat = new ToggleGroup();
    private TextField txfprocentRabat, txfFastRabat,txfTotalPris,txfRegning;
    private ComboBox<Varetype> cbbVareType;
    private String klippekortNavn;
    private ComboBox<Vare> cbbKlippekort;
    private ComboBox<String> cbbprisgrupper;
    private ListView<Vare> lvwKurv, lvwValgteVare;
    private Button btnTilføj,btnRemove,  btnLavSalg;
    private VBox vbox;
    private RadioButton rbklippekort;
    private Button btnKøbKlippekort;
    private TextField txfKlippekortNavn;
    private Button btnOpretklippekort;

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

        createKøbKlippekortGUI();

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

        if(cbbprisgrupper.getSelectionModel().getSelectedItem().toLowerCase(Locale.ROOT).contains("Klip".toLowerCase(Locale.ROOT))){
            rbklippekort.setDisable(false);
        } else {
            rbklippekort.setDisable(true);
        }
        UpdateVagteVareAction();
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
            //laver klippekort(ene)

            Betalingsform bform = Betalingsform.valueOf(groupBetalingsform.getSelectedToggle().getUserData().toString());
            double total = Double.parseDouble(txfTotalPris.getText());

            if(groupRabat.getSelectedToggle() != null){
                //laver rabat objekt.
                if(groupRabat.getSelectedToggle().getUserData().toString().contains("FastRabat")){
                    Rabat rabat = controller.createFastRabat(Double.parseDouble(txfFastRabat.getText()));
                    if(Objects.equals(bform.toString(), "REGNING")){
                        controller.createRegning(varer, bform, rabat, total, txfRegning.getText());
                    } else {
                       controller.createProduktSalg(varer, bform, total, rabat);
                    }
                    salgOprettetMedRabatMessage(rabat);
                } else if (groupRabat.getSelectedToggle().getUserData().toString().contains("ProcentRabat")){
                    Rabat rabat = controller.createProcentRabat(Double.parseDouble(txfprocentRabat.getText()));
                    if(Objects.equals(bform.toString(), "REGNING")){
                        controller.createRegning(varer, bform, rabat, total, txfRegning.getText());
                    } else {
                        controller.createProduktSalg(varer, bform, total, rabat);
                    }
                    salgOprettetMedRabatMessage(rabat);
                }
                controller.saveStorageToFile();
            } else {
                  if(Objects.equals(bform.toString(), "REGNING")){
                      controller.createRegning(varer, bform, null, total, txfRegning.getText());
                  }
                  else if(bform.equals(Betalingsform.KLIPPEKORT)){
                      Klippekort k = (Klippekort) cbbKlippekort.getSelectionModel().getSelectedItem();
                      try{
                          k.brugKlip(Double.parseDouble(""+total));
                          controller.createProduktSalg(varer, bform, total, null);
                          cbbKlippekort.getItems().clear();
                          cbbKlippekort.getItems().addAll(controller.getKlippekort());

                      }catch (IllegalArgumentException ex){
                          String message = ex.getMessage();
                          JOptionPane.showMessageDialog(new JFrame(), message,"Fejl",JOptionPane.ERROR_MESSAGE);
                      }

                  }
                  else {
                      controller.createProduktSalg(varer, bform, total, null);
                  }
                controller.saveStorageToFile();
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

    private void createKlippekortAction(){
        Stage stage = new Stage();
        stage.setTitle("Lav klippekort");

        TilePane r = new TilePane();
        TextField txfKlippekortNavn= new TextField();
        r.getChildren().add(txfKlippekortNavn);

        Button btnCreate = new Button("Lav klippekortet med dette navn");
        btnCreate.setOnAction(event -> stage.close());

        r.getChildren().add(btnCreate);

        Scene sc = new Scene(r,200,50);
        stage.setScene(sc);
        stage.showAndWait();

        klippekortNavn = txfKlippekortNavn.getText();
        stage.close();
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
        }

            else {

            // Hvis man vælger sampakning, kommer man ind i sampakning GUI
            if(ValgtVare.getVaretype().equals(Varetype.SAMPAKNING)){
               Sampakning sampakning = createSampakningAction((Sampakning) ValgtVare);
               lvwKurv.getItems().add(sampakning);
            }
                //Hvis man vælger klippekort, og den ikke er tom

                   if (lvwValgteVare.getSelectionModel().getSelectedItem().getVaretype().equals(Varetype.KLIPPEKORT) || klippekortNavn =="") {
                       createKlippekortAction();
                   }

                   //Tilføjer ikke her, da den bliver tilføjet ovenover.
                   if(!lvwValgteVare.getSelectionModel().getSelectedItem().getVaretype().equals(Varetype.SAMPAKNING)) {
                       lvwKurv.getItems().add(ValgtVare);
                   }

                //Hashmap af alle vores vare.
                HashMap<Vare, Integer> varer = new HashMap<>();
                for (int i = 0; i < lvwKurv.getItems().size(); i++) {
                    if (varer.containsKey(lvwKurv.getItems().get(i))) {
                        varer.put(lvwKurv.getItems().get(i), varer.get(lvwKurv.getItems().get(i)) + 1);
                    } else {
                        varer.put(lvwKurv.getItems().get(i), 1);
                    }
                }
//            opdatere totalPris.
                txfTotalPris.setText(String.valueOf(controller.totalPris(cbbprisgrupper.getSelectionModel().getSelectedItem(), varer)));
            }
    }

    private void errorMessageTilføj() {
        String message = "Du skal vælge en vare først i ''Valgte Vare'' listen";
        JOptionPane.showMessageDialog(new JFrame(), message,"Fejl",JOptionPane.ERROR_MESSAGE);
    }

    private void createKøbKlippekortGUI(){
        btnOpretklippekort = new Button("Opret nyt klippekort.");
        btnKøbKlippekort = new Button("Køb klippekort.");
        txfKlippekortNavn = new TextField("Navn på ejer af nyt kort");
        this.add(btnOpretklippekort, 0,5);
        this.add(txfKlippekortNavn, 1,5);
        this.add(btnKøbKlippekort, 2,5);
        btnOpretklippekort.setOnAction(event -> opretKlippekortAction());
        btnKøbKlippekort.setOnAction(event -> købKlippekortAction());
        txfKlippekortNavn.setDisable(true);
        btnKøbKlippekort.setDisable(true);
    }

    private void opretKlippekortAction() {
        txfKlippekortNavn.setDisable(false);
        btnKøbKlippekort.setDisable(false);
        txfTotalPris.setText("130");
    }

    private void købKlippekortAction() {
        if(txfKlippekortNavn.getText().equals("Navn på ejer af nyt kort") || txfKlippekortNavn.getText().equalsIgnoreCase("")){
            String message = "Du skal indtaste navn i tekstfeltet.";
            JOptionPane.showMessageDialog(new JFrame(), message,"Fejl",JOptionPane.ERROR_MESSAGE);
        }
        else if(groupBetalingsform.getSelectedToggle() == null){
            String message = "Vælg betalingsform.";
            JOptionPane.showMessageDialog(new JFrame(), message,"Fejl",JOptionPane.ERROR_MESSAGE);
        }
        else{
            Klippekort k = controller.createKlippekort(txfKlippekortNavn.getText());
            HashMap<Vare, Integer> varer = new HashMap<>();
            varer.put(k, 1);
            Betalingsform bform = Betalingsform.valueOf(groupBetalingsform.getSelectedToggle().getUserData().toString());
            controller.createProduktSalg(varer, bform, Double.parseDouble(txfTotalPris.getText()), null);
            controller.saveStorageToFile();
            txfKlippekortNavn.setDisable(true);
            btnKøbKlippekort.setDisable(true);
            cbbKlippekort.getItems().clear();
            cbbKlippekort.getItems().addAll(controller.getKlippekort());
            salgOprettetMessage();
        }
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

            if(betalingsForm[i].equals(Betalingsform.KLIPPEKORT)){
                rbklippekort = rb;
                rbklippekort.setOnAction(event -> enableKlippekortDisableRegningAction());
                rbklippekort.setDisable(true);
            } else if (betalingsForm[i].equals(Betalingsform.REGNING)) {
                rb.setOnAction(event -> enableRegningDisableKlippekortAction());            }
            else {
                rb.setOnAction(event -> disableRegningAndKlippekortAction());
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
        txfFastRabat.setText("0");
    }

    private void updateRbProcentAction() {
        txfprocentRabat.setEditable(true);
        txfprocentRabat.clear();
        txfFastRabat.setEditable(false);
        txfFastRabat.setText("Indtast fast rabat:");
        txfprocentRabat.setText("0");
    }

    private void createComboboxKlippekort(SalgsPane salgsPane) {
        cbbKlippekort = new ComboBox<>();
        vbox = new VBox();
        vbox.getChildren().add(cbbKlippekort);
        this.add(vbox, 2, 4);
        //ArrayList<Klippekort> alKlip = new ArrayList<>();
        for(Vare k : controller.getKlippekort()){
            if(((Klippekort) k).getNavnKunde() != null){
                cbbKlippekort.getItems().add(k);
            }
        }
        cbbKlippekort.setDisable(true);
        cbbKlippekort.setPromptText("Vælg klippekort");
    }

    private void createComboboxVareType(SalgsPane salgsPane) {
        cbbVareType = new ComboBox<>();
        this.add(cbbVareType, 1, 1);
        cbbVareType.getItems().addAll(Varetype.values());
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
                if(!(alleVare.get(i) instanceof Klippekort) || !(alleVare.get(i) instanceof Udlejningsvare)) {
                    valgteVare.add(alleVare.get(i));
                }
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

    public Sampakning createSampakningAction(Sampakning sampakning){
        Stage stage = new Stage();
        stage.setTitle("Lav sampakning");
        GridPane r = new GridPane();
        Scene sc = new Scene(r,500,500);

        Label lblFlaskeøl = new Label("Vælg øl til sampakning");
        r.add(lblFlaskeøl, 0, 0);

        Sampakning sampakning1 = new Sampakning(sampakning.getNavn(), sampakning.getAntalOel(), sampakning.getAntalGlas());
        int beercount = sampakning1.getAntalOel();
        Label lblAntalØltilbage = new Label();
        r.add(lblAntalØltilbage, 0, 4);
        lblAntalØltilbage.setText(String.valueOf(beercount));

        ListView<Drikkevare> lvwValgteøl = new ListView<>();
        r.add(lvwValgteøl, 1, 1);

        ListView<Drikkevare> lvwFlaskeøl = new ListView<>();
        r.add(lvwFlaskeøl, 0, 1);
        ArrayList<Drikkevare> flaskeØl = new ArrayList<>();
        for(int i =0;i<controller.getVarer().size();i++){
            if(controller.getVarer().get(i).getVaretype().equals(Varetype.FLASKE)){
                flaskeØl.add((Drikkevare) controller.getVarer().get(i));
            }
        }
        lvwFlaskeøl.getItems().addAll(flaskeØl);

        Button btnTilføjSampakning = new Button("Tilføj øl til sampakning");
        r.add(btnTilføjSampakning, 0, 3);
        btnTilføjSampakning.setOnAction(event -> tilføjTilSampakningAction(sampakning1, lvwFlaskeøl, lvwValgteøl, lblAntalØltilbage));

        Button btnRemoveSampakning = new Button("Fjern fra sampakning");
        r.add(btnRemoveSampakning, 1, 3);
        btnRemoveSampakning.setOnAction(event -> removeFraSampakningAction(sampakning1, lvwValgteøl,lblFlaskeøl));

        Label lblSampakning = new Label("Øl i sampakning");
        r.add(lblSampakning, 1,0);

        Button btnOk = new Button("Lav sampakning");
        r.add(btnOk,1 , 4);
        btnOk.setOnAction(event -> stage.close());

        if(Objects.equals(cbbprisgrupper.getSelectionModel().getSelectedItem(), "Fredagsbar")){
            if(sampakning1.getAntalOel() ==2){
                Prisgruppe p = new Prisgruppe(110, "Fredagsbar");
                sampakning1.addPrisgruppe(p);
            } else if (sampakning1.getAntalOel() == 4){
                Prisgruppe p = new Prisgruppe(140, "Fredagsbar");
                sampakning1.addPrisgruppe(p);
            } else if (sampakning1.getAntalOel() ==6 && sampakning1.getAntalGlas() <2){
                Prisgruppe p = new Prisgruppe(260, "Fredagsbar");
                sampakning1.addPrisgruppe(p);
            } else if(sampakning1.getAntalOel() ==6 && sampakning1.getAntalGlas() <6){
                Prisgruppe p = new Prisgruppe(260, "Fredagsbar");
                sampakning1.addPrisgruppe(p);
            } else if(sampakning1.getAntalOel() ==6 && sampakning1.getAntalGlas() ==6){
                Prisgruppe p = new Prisgruppe(350, "Fredagsbar");
                sampakning1.addPrisgruppe(p);
            } else if(sampakning1.getAntalOel() == 12){
                Prisgruppe p = new Prisgruppe(410, "Fredagsbar");
                sampakning1.addPrisgruppe(p);
            }
        }
        if(Objects.equals(cbbprisgrupper.getSelectionModel().getSelectedItem(), "Butik")){
            if(sampakning1.getAntalOel() ==2){
                Prisgruppe p = new Prisgruppe(110, "Butik");
                sampakning1.addPrisgruppe(p);
            } else if (sampakning1.getAntalOel() == 4){
                Prisgruppe p = new Prisgruppe(140, "Butik");
                sampakning1.addPrisgruppe(p);
            } else if (sampakning1.getAntalOel() ==6 && sampakning1.getAntalGlas() <2){
                Prisgruppe p = new Prisgruppe(260, "Butik");
                sampakning1.addPrisgruppe(p);
            } else if(sampakning1.getAntalOel() ==6 && sampakning1.getAntalGlas() <6){
                Prisgruppe p = new Prisgruppe(260, "Butik");
                sampakning1.addPrisgruppe(p);
            } else if(sampakning1.getAntalOel() ==6 && sampakning1.getAntalGlas() ==6){
                Prisgruppe p = new Prisgruppe(350, "Butik");
                sampakning1.addPrisgruppe(p);
            } else if(sampakning1.getAntalOel() == 12){
                Prisgruppe p = new Prisgruppe(410, "Butik");
                sampakning1.addPrisgruppe(p);
            }
        }

        stage.setScene(sc);
        stage.showAndWait();

        return sampakning1;

    }

    private void removeFraSampakningAction(Sampakning sampakning, ListView<Drikkevare> lvwValgteøl, Label lbl) {
        if(lvwValgteøl.getSelectionModel().getSelectedItem() != null){
            Drikkevare valgteøl = lvwValgteøl.getSelectionModel().getSelectedItem();
            sampakning.removeDrikkevare(valgteøl);
            lvwValgteøl.getItems().remove(valgteøl );

            lbl.setText("Øl tilbage: " + (sampakning.getAntalOel() - lvwValgteøl.getItems().size()));
        } else {
            errorMessageRemove();
        }
    }

    private void tilføjTilSampakningAction(Sampakning sampakning, ListView<Drikkevare> lvwFlaskeøl, ListView<Drikkevare> lvwValgteøl, Label lbl) {
        if(lvwFlaskeøl.getSelectionModel().getSelectedItem() != null) {
            Drikkevare valgteøl = lvwFlaskeøl.getSelectionModel().getSelectedItem();
            sampakning.addDrikkevare(valgteøl);
            lvwValgteøl.getItems().add(valgteøl);

            lbl.setText("Øl tilbage: " + (sampakning.getAntalOel() - lvwValgteøl.getItems().size()));
        } else {
            errorMessageTilføj();
        }
    }

}
