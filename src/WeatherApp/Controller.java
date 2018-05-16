package WeatherApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;


public class Controller {
    @FXML
    private Button editButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button addButton;
    @FXML
    private ListView<String> dashboardList;
    private ObservableList<String> listContent = FXCollections.observableArrayList();



    @FXML
    private void editClicked(){
        System.out.println("edit clicked");

        listContent.add("edit clicked");
        dashboardList.setItems(listContent);
    }

    @FXML
    private void addClicked() throws IOException{

        Stage stage = (Stage)dashboardList.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../scenes/mapScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
