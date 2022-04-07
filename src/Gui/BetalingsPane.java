package Gui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import Application.Controller.Controller;
import Application.Models.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/*
Enten skal regning fjernes fra radiobuttons eller også skal vi tolke regninger,
som at det kun gælder faste kunder der har en konto.



 */

public class BetalingsPane extends GridPane {

    private Controller controller = Controller.getController();
    private TextField txfTotal, txfRabat, txfNavnKunde;
    private ListView<Regning> lvwRegninger, lvwVarerIRegning;
    private Button btnBetal;
    //private RadioButton radBtnBetalingsform;
    private Label lblTotal, lblRegninger, lblRegningInfo, lblRabat, lblBetalingsform, lblKundeNavn, lblvarer;
    private VBox vboxBetalingform, vboxRegningInform, vboxRabat, vboxBetalBtn, vboxLblInfo;
    private ToggleGroup groupBetalingsform = new ToggleGroup();
    private ComboBox<String> cbbRabat;

    public BetalingsPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        // kald create på elementer

        createLabels(this);
        createListViewRegning(this);
        createVbox(this);
        createListViewVarer(this);
        updateRegninger();
    }

    // create elementer

    private void createLabels(BetalingsPane betalingsPane) {
        lblRegninger = new Label("Regninger");
        this.add(lblRegninger, 1, 0);

        lblvarer = new Label("Varer");
        this.add(lblvarer, 2,0);

        lblRegningInfo = new Label("Informationer");
        this.add(lblRegningInfo, 3, 0);

        lblBetalingsform = new Label("Betalingsform");
        this.add(lblBetalingsform, 6, 0);

    }


    private void createListViewRegning(BetalingsPane betalingsPane) {

        lvwRegninger = new ListView<>();
        this.add(lvwRegninger, 1, 2);

        ArrayList<Salg> regninger = controller.getRegninger();
        for (Salg s : regninger) {
            lvwRegninger.getItems().add((Regning) s);
        }
    }

    private void createListViewVarer(BetalingsPane betalingsPane){
        lvwVarerIRegning = new ListView<>();
        this.add(lvwVarerIRegning, 2, 2);
    }



    private void createVbox(BetalingsPane betalingsPane) {
        vboxBetalingform = new VBox();
        this.add(vboxBetalingform, 6, 2);
        Betalingsform[] betalingsform = Betalingsform.values();

        for (int i = 0; i < betalingsform.length; i++) {
            RadioButton rb = new RadioButton();
            vboxBetalingform.getChildren().add(rb);
            rb.setText(betalingsform[i].toString());
            rb.setUserData(betalingsform[i]);
            rb.setToggleGroup(groupBetalingsform);
        }

        // Vbox med informationer fra salget.
        txfNavnKunde = new TextField(); // get fra valgte regning
        txfNavnKunde.setEditable(false);
        txfRabat = new TextField();
        txfRabat.setEditable(false);
        txfTotal = new TextField();
        txfTotal.setEditable(false);


        vboxRegningInform = new VBox();
        this.add(vboxRegningInform, 4, 2);
        vboxRegningInform.getChildren().add(txfNavnKunde);
        vboxRegningInform.getChildren().add(txfRabat);
        vboxRegningInform.getChildren().add(txfTotal);


        // Vbox betalings-button
        vboxBetalBtn = new VBox();
        this.add(vboxBetalBtn, 7, 2);
        btnBetal = new Button("Betal");
        vboxBetalBtn.getChildren().add(btnBetal);
        btnBetal.setOnAction(event -> lavBetalingAction());

        // Vbox til infolabels.
        vboxLblInfo = new VBox();
        vboxLblInfo.setSpacing(10.0);
        this.add(vboxLblInfo, 3, 2);
        lblKundeNavn = new Label("Kunde navn");
        vboxLblInfo.getChildren().add(lblKundeNavn);
        lblRabat = new Label("Rabat");
        vboxLblInfo.getChildren().add(lblRabat);
        lblTotal = new Label("Total pris");
        vboxLblInfo.getChildren().add(lblTotal);



    }

    private void updateRegninger () {


    }

    private void lavBetalingAction(){

        bekraeftBetalingMessage();
    }

    private void bekraeftBetalingMessage(){
        String message = "Betalingen er gennemført";
        JOptionPane.showMessageDialog(new JFrame(), message, "Betaling", JOptionPane.INFORMATION_MESSAGE);
    }





}
