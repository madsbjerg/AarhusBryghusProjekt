package Gui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import Application.Controller.Controller;
import Application.Models.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class BetalingsPane extends GridPane {

    private Controller controller = Controller.getController();
    private TextField txfTotal, txfRabat;
    private ListView<Regning> lvwRegninger;
    private Button btnBetal;
    //private RadioButton radBtnBetalingsform;
    private Label lblTotal;
    private VBox vboxBetalingform, vboxRabat;
    private ToggleGroup groupBetalingsform = new ToggleGroup();
    private ComboBox<String> cbbRabat;

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
        createComboBox(this);
    }

    // create elementer

    private void createTextfields(BetalingsPane betalingsPane){

        txfTotal = new TextField("");
        txfTotal.setEditable(false);
        //this.add(txfTotal);

        txfRabat = new TextField();
        txfRabat.setEditable(true);
        this.add(txfRabat, 4, 1);


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
    }

    private void createComboBox(BetalingsPane betalingsPane){
        cbbRabat = new ComboBox<>();
        this.add(cbbRabat, 3, 1);
        cbbRabat.getItems().add(0, "Fast rabat");
        cbbRabat.getItems().add(1, "Procent rabat");
    }










}
