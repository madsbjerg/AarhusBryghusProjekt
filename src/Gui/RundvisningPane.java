package Gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;import Application.Controller.Controller;
import Application.Models.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

import java.awt.*;
import java.util.function.UnaryOperator;

public class RundvisningPane extends GridPane {


    private TextField txfAntalPersoner,txfPrisPrPerson, txfTotalPris, txfDato, txfTidspunkt, txfNavn;
    private Button btnValgDato, btnOpret;


    public RundvisningPane(){
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        createTextFields(this);

        createLabels(this);

        createButtons(this);


    }

    private void createButtons(RundvisningPane rundvisningPane) {

        btnValgDato = new Button("Vælg dato");
        this.add(btnValgDato, 3, 4);
        btnValgDato.setOnAction(event -> datePickerAction());

        btnOpret = new Button("Opret Rundvisning");
        this.add(btnOpret, 5, 4);
        btnOpret.setOnAction(event -> createRundvisningAction());
    }

    private void createRundvisningAction() {
        try{
            LocalDate date = LocalDate.parse(txfDato.getText());
            LocalDateTime time = date.atTime(LocalTime.parse(txfTidspunkt.getText()));
            if(!Objects.equals(txfTotalPris.getText(), "Total Pris") && !txfNavn.getText().isEmpty()) {
                Controller.createRundvisning(txfNavn.getText(), Integer.parseInt(txfAntalPersoner.getText()), time);
                txfNavn.clear();
                txfTidspunkt.setText("00:00");
                txfTotalPris.clear();
                txfDato.clear();
                txfAntalPersoner.clear();
                txfPrisPrPerson.clear();
                btnValgDato.setDisable(false);
                doneMessage();
            }
            else {
                errormessage();
            }
        }

        catch (NumberFormatException e){
            System.out.println("Fejl");
        }
        catch (DateTimeParseException e){
            errormessageTid();
        }
    }

    private void doneMessage() {
        String message = "Rundvisningen er oprettet";
        JOptionPane.showMessageDialog(new JFrame(), message,"Oprettet!",JOptionPane.INFORMATION_MESSAGE);

    }

    private void errormessageTid() {
        String message = "Du skal vælge en dato";
        JOptionPane.showMessageDialog(new JFrame(), message,"Fejl",JOptionPane.ERROR_MESSAGE);
    }

    private void errormessage() {
        String message = "Indtast hvor mange personer der skal deltage og prisPrPerson";
        JOptionPane.showMessageDialog(new JFrame(), message,"Fejl",JOptionPane.ERROR_MESSAGE);
}

    private int subString(String string, int start, int slut){
        String ny = string;
        return Integer.parseInt(string.substring(start, slut));
    }


    private void datePickerAction() {

        Stage stage = new Stage();
        stage.setTitle("Vælg en dato");

        TilePane r = new TilePane();

        DatePicker da = new DatePicker();
        LocalDate minDate = LocalDate.now();
        LocalDate maxDate = LocalDate.now().plusDays(14);
        da.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                    }});
        r.getChildren().add(da);

        Scene sc = new Scene(r, 200, 200);
        Button btnOk = new Button("ok");
        r.getChildren().add(btnOk);
        btnOk.setOnAction(event -> stage.close());

        stage.setScene(sc);

        stage.showAndWait();

        txfDato.setText(String.valueOf(da.getValue()));
        stage.close();
        btnValgDato.setDisable(true);
    }


    private void createLabels(RundvisningPane rundvisningPane) {
        Label lblAntalPersoner = new Label("Antal personer: ");
        this.add(lblAntalPersoner, 0, 0);

        Label lblPrisPrPerson = new Label("Pris Pr. Person: ");
        this.add(lblPrisPrPerson, 1,0);

        Label lblTotalPris = new Label("Total Pris:");
        this.add(lblTotalPris, 2, 0);

        Label lblValgtDato = new Label("Valgte Dato");
        this.add(lblValgtDato, 3, 2);

        Label lblValgtTidspunkt = new Label("Vælg Tidspunkt:");
        this.add(lblValgtTidspunkt, 4, 2);

        Label lblNavn = new Label("Indtast navn");
        this.add(lblNavn, 0, 3);
    }

    private void createTextFields(RundvisningPane rundvisningPane) {
        txfAntalPersoner = new TextField();
        this.add(txfAntalPersoner, 0, 1);

        txfAntalPersoner.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txfAntalPersoner.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        txfAntalPersoner.setOnKeyReleased(event -> updateTotalPrisAction());

        txfPrisPrPerson = new TextField();
        this.add(txfPrisPrPerson, 1, 1);
        txfPrisPrPerson.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txfPrisPrPerson.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        txfPrisPrPerson.setOnKeyReleased(event -> updateTotalPrisAction());

        txfTotalPris = new TextField("Total Pris");
        this.add(txfTotalPris, 2, 1);
        txfTotalPris.setEditable(false);

        txfDato = new TextField();
        this.add(txfDato, 3, 3);
        txfDato.setEditable(false);

        txfTidspunkt = new TextField("00:00");
        this.add(txfTidspunkt, 4, 3);
        txfTidspunkt.setEditable(true);

        txfNavn = new TextField();
        this.add(txfNavn, 0, 4);

    }

    private void updateTotalPrisAction() {
        if(!txfAntalPersoner.getText().isEmpty() && !txfPrisPrPerson.getText().isEmpty()){

            try {
                int prisPrPerson = Integer.parseInt(txfPrisPrPerson.getText());
                int antalPersoner = Integer.parseInt(txfAntalPersoner.getText());
                txfTotalPris.setText(String.valueOf(prisPrPerson * antalPersoner));
            }
            catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                System.out.println("fejl i updateTotalPrisAction Metoden i RundvisningPane");
            }


        }
    }

}
