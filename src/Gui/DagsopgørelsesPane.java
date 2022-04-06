package Gui;

import Application.Controller.Controller;
import Application.Models.Salg;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class DagsopgørelsesPane extends GridPane {

        private ListView<Salg> lvwsalg;
        private Button btnUpdate;

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
    }

    private void updateSalgAction() {
        lvwsalg.getItems().remove(0,lvwsalg.getItems().size());
        lvwsalg.getItems().addAll(Controller.getController().getSlag());
    }
}
