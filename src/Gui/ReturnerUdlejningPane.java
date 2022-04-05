package Gui;

import Application.Controller.Controller;
import Application.Models.Udlejning;
import Application.Models.Udlejningsvare;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class ReturnerUdlejningPane extends GridPane {
    private Controller controller = Controller.getController();
    private ListView<Udlejning> lvwUdlejninger;
    private ListView<Udlejningsvare> lvwUdlejedeVarer;
    private ListView<Udlejningsvare> lvwReturneredeVarer;
    
}
