package Gui;

import Application.Controller.Controller;
import Application.Models.Salg;
import Application.Models.Udlejning;
import Application.Models.Udlejningsvare;
import Application.Models.Vare;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

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

        createListViews(this);

    }

    private void createListViews(GridPane pane){
        lblUdlejninger = new Label("Ubetalte udlejninger");
        lvwUdlejninger = new ListView<>();
        lblReturneredeVarer = new Label("Returnerede varer");
        lvwReturneredeVarer = new ListView<>();
        lblUdlejedeVarer = new Label("Varer i udlejning");
        lvwUdlejedeVarer = new ListView<>();

        ArrayList<Salg> ul = controller.getUdlejninger();
        for(Salg s : ul){
            if(!((Udlejning) s).isBetalt()){
                lvwUdlejninger.getItems().add((Udlejning)s);
            }
        }

        pane.add(lblUdlejninger, 0,0);
        pane.add(lvwUdlejninger, 0,1);
        pane.add(lblUdlejedeVarer, 0,2);
        pane.add(lvwUdlejedeVarer, 0,3);
    }


}
