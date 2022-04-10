package Gui;

import javafx.beans.value.ChangeListener;
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
import java.util.HashMap;

/*
Enten skal regning fjernes fra radiobuttons eller også skal vi tolke regninger,
som at det kun gælder faste kunder der har en konto.



 */

public class BetalingsPane extends GridPane {

    private Controller controller = Controller.getController();

    private ListView<Regning> lvwRegninger;
    private ListView<String> lvwVarerIRegning;
    private Button btnBetal;
    private Label  lblRegninger ,lblvarer;
    private VBox vboxBetalBtn;

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

    }

    private void createListViewRegning(BetalingsPane betalingsPane) {
        lvwRegninger = new ListView<>();
        this.add(lvwRegninger, 1, 2);
        ChangeListener<Regning> regningChangeListener = (ov, oldRegning, newRegning) -> this.regningSelectedAction();
        lvwRegninger.getSelectionModel().selectedItemProperty().addListener(regningChangeListener);
    }

    private void createListViewVarer(BetalingsPane betalingsPane){
        lvwVarerIRegning = new ListView<>();
        this.add(lvwVarerIRegning, 2, 2);
    }

    private void createVbox(BetalingsPane betalingsPane) {

        // Vbox betalings-button
        vboxBetalBtn = new VBox();
        this.add(vboxBetalBtn, 7, 2);
        btnBetal = new Button("Regning betalt");
        vboxBetalBtn.getChildren().add(btnBetal);
        btnBetal.setOnAction(event -> afslutRegningAction());
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

        Regning valgtRegning = lvwRegninger.getSelectionModel().getSelectedItem();

        if(valgtRegning != null){
            for(Vare v : valgtRegning.getVarer().keySet()){
                lvwVarerIRegning.getItems().add(v.getNavn()+ " " + valgtRegning.getVarer().get(v));
            }
        }
    }

    private void afslutRegningAction(){
        Regning valgtRegning = lvwRegninger.getSelectionModel().getSelectedItem();
        if(valgtRegning == null){
            valgErrorMessage();
        }
        else{
            lvwRegninger.getItems().remove(valgtRegning);
            valgtRegning.setBetalt(true);
            bekraeftBetalingMessage();
            lvwVarerIRegning.getItems().clear();
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
