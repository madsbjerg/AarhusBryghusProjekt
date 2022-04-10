package Gui;

import Application.Controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;

public class StartWindow extends Application {


    @Override
    public void init() {
        Controller.getController().loadStorageFromFile();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Tilmeldings-System");
        BorderPane pane = new BorderPane();
        this.initContent(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

//----------------------------------------------------------------------------------------------------------
private void initContent(BorderPane pane) {

    AnchorPane anchorPane = new AnchorPane();
    this.initAnchorPane(anchorPane);
    pane.setCenter(anchorPane);
}

    private void initAnchorPane(AnchorPane anchorPane) {
        final TabPane tabs = new TabPane();
        final Button btnAdminAccess = new Button("");
        btnAdminAccess.setPrefWidth(70);
        btnAdminAccess.setPrefHeight(15);

        tabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        Tab tabSalg = new Tab("Salg");
        tabs.getTabs().add(tabSalg);
        SalgsPane salgsPane = new SalgsPane();
        tabSalg.setContent(salgsPane);

        Tab tabRundvisning = new Tab("Opret Rundvisning");
        tabs.getTabs().add(tabRundvisning);
        RundvisningPane rundvisningPane = new RundvisningPane();
        tabRundvisning.setContent(rundvisningPane);

        Tab tabUdlejning = new Tab("Udlejning");
        tabs.getTabs().add(tabUdlejning);

        UdlejningsPane udlejningsPane = new UdlejningsPane();
        tabUdlejning.setContent(udlejningsPane);

        Tab tabBetaling = new Tab("Regninger");
        tabs.getTabs().add(tabBetaling);
        BetalingsPane betalingsPane = new BetalingsPane();
        tabBetaling.setContent(betalingsPane);

        Tab tabDagsopgørelse = new Tab("Statestik");
        tabs.getTabs().add(tabDagsopgørelse);
        DagsopgørelsesPane dagsopgørelsesPane = new DagsopgørelsesPane();
        tabDagsopgørelse.setContent(dagsopgørelsesPane);


        AnchorPane.setTopAnchor(tabs, 5.0);
        AnchorPane.setLeftAnchor(tabs, 5.0);
        AnchorPane.setRightAnchor(tabs, 5.0);
        AnchorPane.setTopAnchor(btnAdminAccess, 10.0);
        AnchorPane.setRightAnchor(btnAdminAccess, 10.0);

        ReturnerUdlejningPane returPane = new ReturnerUdlejningPane();
        Tab tabRetur = new Tab("Returnér");
        tabRetur.setContent(returPane);
        tabs.getTabs().add(tabRetur);

        tabs.setStyle("-fx-padding: 2 0 0 50;");
        anchorPane.getChildren().addAll(tabs,btnAdminAccess);


    }





}
