package Gui;

import Application.Controller.Controller;
import Application.Models.Klippekort;
import Application.Models.Vare;
import Application.Models.Varetype;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.util.ArrayList;

public class SalgsPane extends GridPane {

    private Controller controller = Controller.getController();

    private TextField txfprocentRabat, txfFastRabat;
    private ComboBox<Varetype> cbbVareType;

    private ComboBox<Klippekort> cbbKlippekort;
//    private comboBox<Prisgruppe> prisgrupper;
    private ListView<Vare> lvwKurv, lvwValgteVare;

    private Button btnTilføj,btnRemove;

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
    }

    private void createButtons(SalgsPane salgsPane) {
        btnTilføj = new Button("Tilføj vare til kurv");
        this.add(btnTilføj, 1, 2);
        btnTilføj.setOnAction(event -> updateKurvAction());

        btnRemove = new Button("Fjern vare fra kruv");
        this.add(btnRemove, 2, 2);
        btnRemove.setOnAction(event -> removeKurvAction());
    }

    private void removeKurvAction() {
        Vare valgtVare = lvwKurv.getSelectionModel().getSelectedItem();
    }

    private void updateKurvAction() {
        //try og catch, hvis der ikke er valgt nogen vare i Listviewet.

            Vare ValgtVare = lvwValgteVare.getSelectionModel().getSelectedItem();
            if(ValgtVare ==null) {
                errorMessageTilføj();
            }
            lvwKurv.getItems().add(ValgtVare);

    }

    private void errorMessageTilføj() {
        String message = "Du skal vælge en vare først i ''Valgte Vare'' listen";
        JOptionPane.showMessageDialog(new JFrame(), message,"Fejl",JOptionPane.ERROR_MESSAGE);
    }

    private void createListviewKurv(SalgsPane salgsPane) {
        lvwKurv = new ListView<>();
        this.add(lvwKurv, 2, 1);
    }

    private void createListviewValgteVare(SalgsPane salgsPane) {
        lvwValgteVare = new ListView<>();
        this.add(lvwValgteVare, 1, 1);
    }



    private void createRadioButtons(SalgsPane salgsPane) {
    }

    private void createComboboxKlippekort(SalgsPane salgsPane) {

    }

    private void createComboboxVareType(SalgsPane salgsPane) {
        cbbVareType = new ComboBox<>();
        this.add(cbbVareType, 0, 1);
        cbbVareType.getItems().addAll(Varetype.values());
        cbbVareType.setOnAction(event -> UpdateVagteVareAction());

    }
    private void UpdateVagteVareAction() {
        //Nulstiller listen
        lvwValgteVare.getItems().remove(0, lvwValgteVare.getItems().size());

        ArrayList<Vare> valgteVare = new ArrayList<>();
        ArrayList<Vare> alleVare = new ArrayList<>(controller.getVarer());
        for(int i =0;i< alleVare.size();i++){
            if(alleVare.get(i).getVaretype() == cbbVareType.getValue()){
                valgteVare.add(alleVare.get(i));
            }
        }
        lvwValgteVare.getItems().addAll(valgteVare);
    }

    private void createLabels(SalgsPane salgsPane) {
        Label lblVaregrupper = new Label("VareGrupper:");
        this.add(lblVaregrupper, 0, 0);

        Label lblValgteVare = new Label("Valgte Vare: ");
        this.add(lblValgteVare, 1, 0);

        Label lblKurv = new Label("Kurv");
        this.add(lblKurv, 2, 0);
    }



}
