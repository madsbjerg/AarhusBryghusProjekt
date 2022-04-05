package Gui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import Application.Controller.Controller;
import Application.Models.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class BetalingsPane extends GridPane {

    private Controller controller = Controller.getController();
    private TextField txfTotal, txfRabat, txfNavnKunde;
    private ListView<Regning> lvwRegninger;
    private Button btnBetal;
    //private RadioButton radBtnBetalingsform;
    private Label lblTotal;
    private VBox vboxBetalingform, vboxRegningInform;
    private ToggleGroup groupBetalingsform = new ToggleGroup();
    private ComboBox<String> cbbRabat;

    public BetalingsPane(){
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        // kald create på elementer

        createLabels(this);
        createButtons(this);
        createListViews(this);
        createVbox(this);

        updateRegningerAction();


    }

    // create elementer


    private void createLabels(BetalingsPane betalingsPane){
        lblTotal = new Label("Total");
    }


    private void createButtons(BetalingsPane betalingsPane){
        btnBetal = new Button();
    }

    private void createListViews(BetalingsPane betalingsPane){
        lvwRegninger = new ListView<>();
        this.add(lvwRegninger, 0, 1);

    }

    private void createVbox (BetalingsPane betalingsPane){

        // Vbox til betalingsform
        vboxBetalingform = new VBox();
        this.add(vboxBetalingform, 3, 1);
        Betalingsform[] betalingsform = Betalingsform.values();

        for(int i = 0; i < betalingsform.length; i++){
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
        this.add( vboxRegningInform,2, 1);
        vboxRegningInform.getChildren().add(txfNavnKunde);
        vboxRegningInform.getChildren().add(txfRabat);
        vboxRegningInform.getChildren().add(txfTotal);


    }






}
