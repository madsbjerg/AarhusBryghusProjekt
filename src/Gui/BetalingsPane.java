package Gui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import Application.Controller.Controller;
import Application.Models.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class BetalingsPane extends GridPane {

    private Controller controller = Controller.getController();
    private TextField txfTotal, txfRabat, txfNavnKunde;
    private ListView<Regning> lvwRegninger;
    private Button btnBetal;
    //private RadioButton radBtnBetalingsform;
    private Label lblTotal, lblRegninger, lblRegningInfo, lblRabat, lblBetalingsform;
    private VBox vboxBetalingform, vboxRegningInform, vboxRabat, vboxBetalBtn;
    private ToggleGroup groupBetalingsform = new ToggleGroup();
    private ComboBox<String> cbbRabat;

    public BetalingsPane() {
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

        updateRegninger();
    }

    // create elementer

    private void createTextfields(BetalingsPane betalingsPane) {

//        txfTotal = new TextField("");
//        txfTotal.setEditable(false);
//        //this.add(txfTotal);




    }

    private void createLabels(BetalingsPane betalingsPane) {
        lblRegninger = new Label("Regninger");
        this.add(lblRegninger, 1, 1);

        lblRegningInfo = new Label("Informationer");
        this.add(lblRegningInfo, 2, 1);

        lblRabat = new Label("Rabat");
        this.add(lblRabat, 3, 1);

        lblBetalingsform = new Label("Betalingsform");
        this.add(lblBetalingsform, 4, 1);

    }


    private void createButtons(BetalingsPane betalingsPane) {

    }

    private void createListViews(BetalingsPane betalingsPane) {

        lvwRegninger = new ListView<>();
        this.add(lvwRegninger, 1, 2);

        ArrayList<Salg> regninger = controller.getRegninger();
        for (Salg s : regninger) {
            lvwRegninger.getItems().add((Regning) s);
        }
    }


    private void createVbox(BetalingsPane betalingsPane) {
        vboxBetalingform = new VBox();
        this.add(vboxBetalingform, 4, 2);
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
        this.add(vboxRegningInform, 2, 2);
        vboxRegningInform.getChildren().add(txfNavnKunde);
        vboxRegningInform.getChildren().add(txfRabat);
        vboxRegningInform.getChildren().add(txfTotal);

        // Vbox rabat
        txfRabat = new TextField();
        txfRabat.setEditable(false);
        vboxRabat = new VBox();
        this.add(vboxRabat, 3, 2);
        vboxRabat.getChildren().add(txfRabat);

        // Vbox betalings-button
        vboxBetalBtn = new VBox();
        this.add(vboxBetalBtn, 5, 2);
        btnBetal = new Button("Betal");
        vboxBetalBtn.getChildren().add(btnBetal);


    }

    private void updateRegninger () {

    }


}
