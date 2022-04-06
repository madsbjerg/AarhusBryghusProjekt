package Gui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import Application.Controller.Controller;
import Application.Models.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
    private Label lblTotal, lblRegninger, lblRegningInfo, lblRabat, lblKundeNavn, lblvarer;
    private VBox vboxRegningInform, vboxBetalBtn, vboxLblInfo;

    public BetalingsPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        // ---- initialize ------------------------------------

        createLabels(this);
        createListViewRegning(this);
        createVbox(this);
        createListViewVarer(this);
        updateRegningerAction();

    }


    // ---- Create elementer ----------------------------------

    private void createLabels(BetalingsPane betalingsPane) {
        lblRegninger = new Label("Regninger");
        this.add(lblRegninger, 1, 0);
        lblRegninger.setFont(Font.font("",FontWeight.BOLD, 12));

        lblvarer = new Label("Varer i salg");
        this.add(lblvarer, 2,0);
        lblvarer.setFont(Font.font("",FontWeight.BOLD, 12));

        lblRegningInfo = new Label("Salgsinformationer");
        this.add(lblRegningInfo, 3, 0);
        lblRegningInfo.setFont(Font.font("",FontWeight.BOLD, 12));
    }

    private void createListViewRegning(BetalingsPane betalingsPane) {
        lvwRegninger = new ListView<>();
        this.add(lvwRegninger, 1, 2);
    }

    private void createListViewVarer(BetalingsPane betalingsPane){
        lvwVarerIRegning = new ListView<>();
        this.add(lvwVarerIRegning, 2, 2);
    }

    private void createVbox(BetalingsPane betalingsPane) {

        // Vbox med informationer fra salget.
        txfNavnKunde = new TextField();
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
        btnBetal = new Button("Regning betalt");
        vboxBetalBtn.getChildren().add(btnBetal);
        btnBetal.setOnAction(event -> afslutRegningAction());

        // Vbox til infolabels.
        vboxLblInfo = new VBox();
        vboxLblInfo.setSpacing(10.0);
        this.add(vboxLblInfo, 3, 2);
        lblKundeNavn = new Label("Kunde navn:");
        vboxLblInfo.getChildren().add(lblKundeNavn);
        lblRabat = new Label("Rabat:");
        vboxLblInfo.getChildren().add(lblRabat);
        lblTotal = new Label("Total pris:");
        vboxLblInfo.getChildren().add(lblTotal);
    }


    // ---- Actions ---------------------------------

    private void updateRegningerAction () {
        ArrayList<Salg> regninger = controller.getRegninger();
        for (Salg s : regninger) {
            if(!((Regning)s).isBetalt()){
                lvwRegninger.getItems().add((Regning) s);
            }
        }
    }

    private void regningSelectedAction(){
        Regning valgtRegning = lvwVarerIRegning.getSelectionModel().getSelectedItem();

        // set text

    }

    private void afslutRegningAction(){
        // Skal vi skifte boolean isBetalt??
        Regning valgtRegning = lvwRegninger.getSelectionModel().getSelectedItem();
        if(valgtRegning == null){
            valgErrorMessage();
        }
        else{
            lvwRegninger.getItems().remove(valgtRegning);
            bekraeftBetalingMessage();
        }
    }

    private void bekraeftBetalingMessage(){
        String message = "Regningen er blevet afsluttet";
        JOptionPane.showMessageDialog(new JFrame(), message, "Afslut regning", JOptionPane.INFORMATION_MESSAGE);
    }

    private void valgErrorMessage(){
        String message = "Ingen regning er valgt";
        JOptionPane.showMessageDialog(new JFrame(), message, "Fejl", JOptionPane.ERROR_MESSAGE);
    }


}
