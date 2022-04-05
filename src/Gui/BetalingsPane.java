package Gui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import Application.Controller.Controller;
import Application.Models.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class BetalingsPane extends GridPane {

    private Controller controller = Controller.getController();
    private TextField txfTotal, txfProcentRabat, txfFastRabat;
    private ListView<Regning> lvwRegninger;
    private Button btnBetal;
    //private RadioButton radBtnBetalingsform;
    private Label lblTotal;

    public BetalingsPane(){
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
            this.setGridLinesVisible(false);

        // kald create på elementer

        createTextfields(this);
        createLabels(this);
        createButtons(this);
        createListViews(this);
        createVbox(this);
    }

    // create elementer

    private void createTextfields(BetalingsPane betalingsPane){

        txfTotal = new TextField("");
        txfTotal.setEditable(false);
        //this.add(txfTotal);

        txfFastRabat = new TextField();
        txfFastRabat.setEditable(true);


        txfProcentRabat = new TextField();
        txfProcentRabat.setEditable(true);


    }

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
        vboxBetalingform = new VBox();
        this.add(vboxBetalingform, 2, 1);
        Betalingsform[] betalingsform = Betalingsform.values();

        for(int i = 0; i < betalingsform.length; i++){
            RadioButton rb = new RadioButton();
            vboxBetalingform.getChildren().add(rb);
            rb.setText(betalingsform[i].toString());
            rb.setUserData(betalingsform[i]);
            rb.setToggleGroup(groupBetalingsform);
        }

        vboxRabat = new VBox();
        this.add(vboxRabat, 3, 1);
        vboxRabat.getChildren().add(txfFastRabat);
        vboxRabat.getChildren().add(txfProcentRabat);

    }










}
