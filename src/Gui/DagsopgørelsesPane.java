package Gui;

import Application.Controller.Controller;
import Application.Models.Rundvisning;
import Application.Models.Salg;
import Application.Models.Vare;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class DagsopgørelsesPane extends GridPane {

        private ListView<Salg> lvwsalg;
        private Button btnUpdate,btnSerundvisninger;
        private TextArea txaRundvisning;

    public DagsopgørelsesPane(){
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);

        
        createListview(this);
        createButton(this);
        createLabel(this);
        
    }

    private void createLabel(DagsopgørelsesPane dagsopgørelsesPane) {
        Label lblDagsopgørsel = new Label("Dagsopgørelse");
        this.add(lblDagsopgørsel, 0, 0);
    }


    private void createListview(DagsopgørelsesPane dagsopgørelsesPane) {
        lvwsalg = new ListView<>();
        this.add(lvwsalg, 0, 1);
        ArrayList<Salg> dagsSalg = new ArrayList<>();
        for(int i =0;i<Controller.getController().getSlag().size();i++){
            if(Objects.equals(Controller.getController().getSlag().get(i).getSalgsDato(), LocalDate.now())){
                dagsSalg.add(Controller.getController().getSlag().get(i));
            }
        }
        lvwsalg.getItems().addAll(Controller.getController().getSlag());
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
        lvwsalg.getItems().addAll(Controller.getController().getSlag());
    }
}
