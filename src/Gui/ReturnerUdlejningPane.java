package Gui;

import Application.Controller.Controller;
import Application.Models.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.HashMap;

public class ReturnerUdlejningPane extends GridPane {
    private Controller controller = Controller.getController();
    private Label lblUdlejninger;                                   // Label til nedenstående list view.
    private ListView<Udlejning> lvwUdlejninger;                     // List view over alle ubetalte udlejninger.
    private Label lblUdlejedeVarer;                                 // Label til nedenstående list view.
    private ListView<Udlejningsvare> lvwUdlejedeVarer;              // List view over udlejede vare for specifik udlejning.
    private Label lblReturneredeVarer;                              // Label til nedenstående list view.
    private ListView<Udlejningsvare> lvwReturneredeVarer;           // List view over returnerede vare med pant.
    private Label lblTotalPris;                                     // Label med info om total pris.
    private Label lblTotalPrisEfterRabat;                           // Label med infor om total pris efter rabat hvis rabat.
    private Button btnFærdiggør;                                    // Knap til færdiggørelse
    private Button btnTilføjVare;                                   // Knap til tilføjelse af vare.

    public ReturnerUdlejningPane(){
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        createLayout(this);



    }

    private void createLayout(GridPane pane){
        int heightScale = 5;
        lblUdlejninger = new Label("Ubetalte udlejninger");
        lvwUdlejninger = new ListView<>();
        lblReturneredeVarer = new Label("Returnerede varer med pant.");
        lvwReturneredeVarer = new ListView<>();
        lblUdlejedeVarer = new Label("Varer i udlejning");
        lvwUdlejedeVarer = new ListView<>();

        lblTotalPris = new Label("Total pris");
        lblTotalPrisEfterRabat = new Label("Total pris rabat");

        VBox prisBox = new VBox();
        prisBox.getChildren().add(lblTotalPris);
        prisBox.getChildren().add(lblTotalPrisEfterRabat);

        btnTilføjVare = new Button("Tilføj til returliste.");
        btnFærdiggør = new Button("Færdiggør udlejning.");

        lvwUdlejninger.setPrefHeight(Screen.getPrimary().getBounds().getMaxY() / heightScale);
        lvwUdlejedeVarer.setPrefHeight(Screen.getPrimary().getBounds().getMaxY() / heightScale);
        lvwReturneredeVarer.setPrefHeight(Screen.getPrimary().getBounds().getMaxY() / heightScale);

        ArrayList<Salg> ul = controller.getUdlejninger();
        for(Salg s : ul){
            if(!((Udlejning) s).isBetalt()){
                lvwUdlejninger.getItems().add((Udlejning)s);
            }
        }

        // PANE ADD

        pane.add(lblUdlejninger, 0,0);
        pane.add(lvwUdlejninger, 0,1);
        pane.add(lblUdlejedeVarer, 0,2);
        pane.add(lvwUdlejedeVarer, 0,3);
        pane.add(lblReturneredeVarer,1,2);
        pane.add(lvwReturneredeVarer, 1,3);
        pane.add(prisBox, 2,3);
        pane.add(btnTilføjVare, 0,4);
        pane.add(btnFærdiggør, 2,4);


        // ACTIONS
        ChangeListener<Udlejning> udlejningChangeListener = (ov, oldUdlejning, newUdlejning) -> this.udlejningSelectedAction();
        lvwUdlejninger.getSelectionModel().selectedItemProperty().addListener(udlejningChangeListener);

        //ChangeListener<Udlejningsvare> vareChangedListener = (ov, oldUdlejning, newUdlejning) -> this.vareSelectedAction();
        //lvwUdlejedeVarer.getSelectionModel().selectedItemProperty().addListener(vareChangedListener);

        btnTilføjVare.setOnAction(event -> addVareAction());


    }

    private void addVareAction() {
        Udlejningsvare vare = lvwUdlejedeVarer.getSelectionModel().getSelectedItem();
        if(vare != null){
            lvwReturneredeVarer.getItems().add(vare);
            HashMap<Vare, Integer> returVare = new HashMap<>();
            lblTotalPris.setText(""+controller.totalUdlejning(lvwUdlejninger.getSelectionModel().getSelectedItem().getVarer(), returVare));
            Rabat rabat = lvwUdlejninger.getSelectionModel().getSelectedItem().getRabat();
            double totalEfterRabat = rabat != null ? rabat.beregnRabat(Double.parseDouble(lblTotalPris.getText())) : 0.0;
            lblTotalPrisEfterRabat.setText("" + totalEfterRabat);
        }
    }

    private void udlejningSelectedAction() {
        Udlejning u = lvwUdlejninger.getSelectionModel().getSelectedItem();
        if(u != null){
            lvwUdlejedeVarer.getItems().addAll((Udlejningsvare) u.getVarer().keySet());
        }
    }
}
