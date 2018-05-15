package WeatherApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


public class Controller {
    @FXML
    private Button editButton;
    @FXML
    private Button settingsButton;
    @FXML
    private ListView<String> dashboardList;
    private ObservableList<String> listContent = FXCollections.observableArrayList();

    @FXML
    private void editClicked(){
        System.out.println("edit clicked");

        listContent.add("edit clicked");
        dashboardList.setItems(listContent);
    }
}
