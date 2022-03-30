package Gui;

import Application.Models.Klippekort;
import Application.Models.Vare;
import Application.Models.Varetype;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class SalgsPane extends GridPane {

    private TextField txfprocentRabat, txfFastRabat;
    private ComboBox<Varetype> cbbVareType;

    private ComboBox<Klippekort> cbbKlippekort;
//    private comboBox<Prisgruppe> prisgrupper;
    private ListView<Vare> lvwKurv, lvwValgteVare;

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
    }

    private void createListviewValgteVare(SalgsPane salgsPane) {
        lvwValgteVare = new ListView<>();
        this.add(lvwValgteVare, 1, 0);
    }

    private void createRadioButtons(SalgsPane salgsPane) {
    }

    private void createComboboxKlippekort(SalgsPane salgsPane) {

    }

    private void createComboboxVareType(SalgsPane salgsPane) {
        cbbVareType = new ComboBox<>();
        this.add(cbbVareType, 0, 0);
        cbbVareType.getItems().addAll(Varetype.values());
        cbbVareType.setOnAction(event -> UpdateVagteVareAction());

    }

    private void UpdateVagteVareAction() {
        lvwValgteVare.getItems().addAll();
    }

    private void createLabels(SalgsPane salgsPane) {
        
    }



}
