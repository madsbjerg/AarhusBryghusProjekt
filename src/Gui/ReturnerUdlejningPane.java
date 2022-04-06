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

import javax.swing.*;
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
    private HashMap<Vare, Integer> returVare;
    private Button btnOpdaterListe;
    private Label lblAntalAfVare;
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

        lblAntalAfVare = new Label("");

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

        btnOpdaterListe = new Button("Opdater");

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
        pane.add(btnOpdaterListe, 1,0);

        // ACTIONS
        ChangeListener<Udlejning> udlejningChangeListener = (ov, oldUdlejning, newUdlejning) -> this.udlejningSelectedAction();
        lvwUdlejninger.getSelectionModel().selectedItemProperty().addListener(udlejningChangeListener);

        ChangeListener<Udlejningsvare> vareChangedListener = (ov, oldUdlejning, newUdlejning) -> this.vareSelectedAction();
        lvwUdlejedeVarer.getSelectionModel().selectedItemProperty().addListener(vareChangedListener);

        btnTilføjVare.setOnAction(event -> addVareAction());

        btnFærdiggør.setOnAction(event -> færdiggørUdlejningAction());

        btnOpdaterListe.setOnAction(eveent -> opdaterListeAction());
    }

    private void vareSelectedAction() {
        Udlejning u = lvwUdlejninger.getSelectionModel().getSelectedItem();
        if(u != null){
            lblAntalAfVare.setText("" + u.getVarer().get(lvwUdlejedeVarer.getSelectionModel().getSelectedItem()));
        }
    }

    private void opdaterListeAction() {
        lvwUdlejninger.getItems().clear();
        lvwUdlejedeVarer.getItems().clear();
        lvwReturneredeVarer.getItems().clear();
        ArrayList<Salg> ul = controller.getUdlejninger();
        for(Salg s : ul){
            if(!((Udlejning) s).isBetalt()){
                lvwUdlejninger.getItems().add((Udlejning)s);
            }
        }
    }

    private void færdiggørUdlejningAction() {
        Udlejning u = lvwUdlejninger.getSelectionModel().getSelectedItem();
        if(u != null){
            u.setReturVarer(returVare);
            u.setBetalt(true);
            lvwUdlejninger.getItems().removeAll();
            opdaterListeAction();
            controller.saveStorageToFile();
            udlejningAfleveretMessage();
        }
    }

    private void udlejningAfleveretMessage() {
        String message = "Udlejning sat som betalt og returneret i systemet.";
        JOptionPane.showMessageDialog(new JFrame(), message,"Oprettet",JOptionPane.INFORMATION_MESSAGE);
    }

    private void addVareAction() {
        Udlejningsvare vare = lvwUdlejedeVarer.getSelectionModel().getSelectedItem();
        if(vare != null){
            lvwReturneredeVarer.getItems().add(vare);

            int count;
            count = returVare.get(vare) != null ? returVare.get(vare) : 0;
            count++;

            returVare.put(vare, count);
            lblTotalPris.setText(""+controller.totalUdlejning(lvwUdlejninger.getSelectionModel().getSelectedItem().getVarer(), returVare));
            Rabat rabat = lvwUdlejninger.getSelectionModel().getSelectedItem().getRabat();
            double totalEfterRabat = rabat != null ? rabat.beregnRabat(Double.parseDouble(lblTotalPris.getText())) : Double.parseDouble(lblTotalPris.getText());
            lblTotalPrisEfterRabat.setText("" + totalEfterRabat);
        }
    }

    private void udlejningSelectedAction() {
        returVare = new HashMap<>();
        Udlejning u = lvwUdlejninger.getSelectionModel().getSelectedItem();
        if(u != null){
            for(Vare uv : u.getVarer().keySet()){
                lvwUdlejedeVarer.getItems().add((Udlejningsvare) uv);
            }
            //lvwUdlejedeVarer.getItems().addAll((Udlejningsvare) u.getVarer().keySet());
        }
    }
}
