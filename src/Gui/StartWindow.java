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
        Controller.initStorage();
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
        final Button btnAdminAccess = new Button("ADMIN");
        btnAdminAccess.setPrefWidth(70);
        btnAdminAccess.setPrefHeight(15);

        tabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        Tab tabSalg = new Tab("Salg");
        tabs.getTabs().add(tabSalg);

        Tab tabRundvisning = new Tab("Opret Rundvisning");
        tabs.getTabs().add(tabRundvisning);
        RundvisningPane rundvisningPane = new RundvisningPane();
        tabRundvisning.setContent(rundvisningPane);



        SalgsPane salgsPane = new SalgsPane();
        tabSalg.setContent(salgsPane);

        AnchorPane.setTopAnchor(tabs, 5.0);
        AnchorPane.setLeftAnchor(tabs, 5.0);
        AnchorPane.setRightAnchor(tabs, 5.0);
        AnchorPane.setTopAnchor(btnAdminAccess, 10.0);
        AnchorPane.setRightAnchor(btnAdminAccess, 10.0);


        tabs.setStyle("-fx-padding: 2 0 0 50;");
        anchorPane.getChildren().addAll(tabs,btnAdminAccess);


    }

    private boolean passwordAction(){
        final boolean[] passwordCorrect = {false};
        Stage password = new Stage();
        password.initModality(Modality.APPLICATION_MODAL);
        password.setTitle("SKRIV PASSWORD");

        TilePane r = new TilePane();

        Scene sc = new Scene(r,300,200);

        final Label message = new Label("");
        r.getChildren().add(message);
        VBox vb = new VBox();
        vb.setPadding(new Insets(10, 0, 0, 10));
        vb.setSpacing(10);
        r.getChildren().add(vb);
        HBox hb = new HBox();
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER_LEFT);
        r.getChildren().add(hb);

        Label label = new Label("Password");
        r.getChildren().add(label);
        final PasswordField pb = new PasswordField();
        r.getChildren().add(pb);


        pb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!pb.getText().equals("admin")) {
                    message.setText("Your password is incorrect!");
                    message.setTextFill(Color.rgb(210, 39, 30));
                } else {
                    message.setText("Your password has been confirmed");
                    message.setTextFill(Color.rgb(21, 117, 84));
                    passwordCorrect[0] = true;
                    String message = "Du kan lukke vinduet nu";
                    JOptionPane.showMessageDialog(new JFrame(), message, "Password Korrekt",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                pb.clear();
            }
        });
        hb.getChildren().addAll(label, pb);
        vb.getChildren().addAll(hb, message);
        password.setScene(sc);
        password.showAndWait();

        return passwordCorrect[0];
    }



}
