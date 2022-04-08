package Gui;

import Application.Controller.Controller;
import Application.Models.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DagsopgørelsesPane extends GridPane {

        private ListView<Salg> lvwsalg;
        private Button btnUpdate,btnSerundvisninger, btnUpdateKlip;
        private TextArea txaRundvisning;
        private Label lblKlipStatistik, lblStartPeriode, lblSlutPeriode, lblKlipBrugtIPeriode;
        private ListView<String> lvwVarerKøbtPåKlip;
        private DatePicker dpStartPeriode, dpSlutPeriode;
        private Controller controller;
    public DagsopgørelsesPane(){
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);

        controller = Controller.getController();
        createListview(this);
        createButton(this);
        createLabel(this);
        createKlipStatistikGui();
        
    }

    private void createKlipStatistikGui() {
        int heightScale = 4;
        lvwVarerKøbtPåKlip = new ListView<>();
        lblKlipStatistik = new Label("Statistik over klip.");
        lblStartPeriode = new Label("Start periode.");
        lblSlutPeriode = new Label("Slut periode.");
        lblKlipBrugtIPeriode = new Label("0");
        dpStartPeriode = new DatePicker();
        dpSlutPeriode = new DatePicker();
        lvwVarerKøbtPåKlip.setPrefHeight(Screen.getPrimary().getBounds().getMaxY() / heightScale);
        btnUpdateKlip = new Button("Update");

        HBox hbox = new HBox();
        this.add(lblKlipStatistik, 1,0);
        this.add(lvwVarerKøbtPåKlip,1,1);
        this.add(lblStartPeriode,1,2);
        this.add(lblSlutPeriode,1,4);
        hbox.getChildren().add(dpStartPeriode);
        hbox.getChildren().add(lblKlipBrugtIPeriode);
        hbox.setPrefWidth(lvwVarerKøbtPåKlip.getWidth());
        hbox.setSpacing(50);
        this.add(hbox,1,3);
        this.add(dpSlutPeriode,1,5);
        this.add(btnUpdateKlip,1,6);
        btnUpdateKlip.setOnAction(event -> opdaterKlipListe());
    }

    private void opdaterKlipListe() {
        HashMap<Vare, Integer> vareKlipMap = new HashMap<>();
        if(!(dpSlutPeriode.getValue() == null) || !(dpStartPeriode.getValue() == null)){
            for(Salg s : controller.getProduktSalg()){
                if(((ProduktSalg) s).getBetalingsform().equals(Betalingsform.KLIPPEKORT)){
                    if(s.getSalgsDato().isBefore(dpSlutPeriode.getValue()) || s.getSalgsDato().isAfter(dpStartPeriode.getValue())){
                        HashMap<Vare, Integer> varerMap = s.getVarer();
                        for(Vare v : varerMap.keySet())
                        {
                            int currentAmount = 0;
                            if(vareKlipMap.get(v) == null) {
                                vareKlipMap.put(v, currentAmount + (int)v.getPris("FredagsbarKlip"));
                            }
                            else{
                                currentAmount = vareKlipMap.get(v);
                                vareKlipMap.put(v, currentAmount + (int)v.getPris("FredagsbarKlip"));
                            }
                        }
                    }
                }
            }

            lvwVarerKøbtPåKlip.getItems().clear();
            int sumKlip = 0;
            for(Vare v : vareKlipMap.keySet()){
                int klip = vareKlipMap.get(v);
                lvwVarerKøbtPåKlip.getItems().add(v.getNavn() + " klip: " + klip);
                sumKlip += klip;
            }
            lblKlipBrugtIPeriode.setText(""+sumKlip);
        }
        else{
            String message = "Husk at indsætte datoer";
            JOptionPane.showMessageDialog(new JFrame(), message,"Fejl",JOptionPane.ERROR_MESSAGE);
        }
    }


    private void createLabel(DagsopgørelsesPane dagsopgørelsesPane) {
        Label lblDagsopgørsel = new Label("Dagsopgørelse");
        this.add(lblDagsopgørsel, 0, 0);
    }


    private void createListview(DagsopgørelsesPane dagsopgørelsesPane) {
        lvwsalg = new ListView<>();
        this.add(lvwsalg, 0, 1);
        ArrayList<Salg> dagsSalg = new ArrayList<>();
        for(int i = 0; i<Controller.getController().getSalg().size(); i++){
            if(Objects.equals(Controller.getController().getSalg().get(i).getSalgsDato(), LocalDate.now())){
                dagsSalg.add(Controller.getController().getSalg().get(i));
            }
        }
        lvwsalg.getItems().addAll(Controller.getController().getSalg());
    }

    private void createButton(DagsopgørelsesPane dagsopgørelsesPane){
        btnUpdate = new Button("Update");
        this.add(btnUpdate, 0, 2);
        btnUpdate.setOnAction(event -> updateSalgAction());


        btnSerundvisninger = new Button("Se kalender over rundvisninger");
        this.add(btnSerundvisninger, 0, 3);
        btnSerundvisninger.setOnAction(event -> launchKalenderAction());
    }

    private void launchKalenderAction() {
        ArrayList<Rundvisning> rundvisninger = new ArrayList<>(Controller.getController().getRundvisninger());
        ArrayList<LocalDate> datoer = new ArrayList<>();

        for(int i =0;i<rundvisninger.size();i++){
            datoer.add(rundvisninger.get(i).getTidspunkt().toLocalDate());
        }
        Stage stage = new Stage();
        stage.setTitle("Se rundvisninger");
        TilePane r = new TilePane();
        txaRundvisning = new TextArea();
        r.getChildren().add(txaRundvisning);
        DatePicker da = new DatePicker();
        da.setDayCellFactory(d ->
            new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty){
                super.updateItem(item, empty);
                    if(empty || datoer.contains(item) ){
                        setText(item.toString());
                    } else {
                        setText(null);
                        setDisable(true);
                    }}});
        r.getChildren().add(da);

        da.setOnAction(event -> updateTxaAction(da, rundvisninger));
        Scene sc = new Scene(r,500,500);
        Button btnOk = new Button("ok");
        r.getChildren().add(btnOk);
        btnOk.setOnAction(event -> stage.close());

        stage.setScene(sc);

        stage.showAndWait();


    }

    private void updateTxaAction(DatePicker da, ArrayList<Rundvisning> rundvisning) {
        ArrayList<Rundvisning> rundvisningDatepicker = new ArrayList<>();
        for(int i =0;i<rundvisning.size();i++){
            if(rundvisning.get(i).getTidspunkt().toLocalDate().isEqual(da.getValue())){
                rundvisningDatepicker.add(rundvisning.get(i));
            }
        }
        txaRundvisning.setText(rundvisningDatepicker.toString());
    }
    private void updateSalgAction() {
        lvwsalg.getItems().remove(0,lvwsalg.getItems().size());
        lvwsalg.getItems().addAll(Controller.getController().getSalg());
    }
}
