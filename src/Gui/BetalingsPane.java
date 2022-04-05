package Gui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import Application.Controller.Controller;
import Application.Models.*;
import javafx.scene.control.*;


public class BetalingsPane extends GridPane {

    private Controller controller = Controller.getController();
    private TextField txfBetalerNavn, txfTotal, txfProcentRabat, txfFastRabat;
    private ListView<Regning> lvwRegninger;
    private Button btnBetal;
    private RadioButton radBtnBetalingsform;
    private Label lblTotal;

    public BetalingsPane{
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
            this.setGridLinesVisible(false);

        // kald create på elementer

        createTextfields(this);
        createRaioButtons(this);


    }

    // create elementer

    private void createTextfields(BetalingsPane betalingsPane){
        txfBetalerNavn = new TextField("Kunde navn");
        txfBetalerNavn.setEditable(true);

        txfTotal = new TextField("");

    }

    private void createLabels(BetalingsPane betalingsPane){
        lblTotal = new Label("Total");
    }


    private void createRadioButtons(BetalingsPane betalingsPane){
        radBtnBetalingsform = new RadioButton();
    }

    private void createButtons(){

    }










}
