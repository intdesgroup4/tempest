package WeatherApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class Controller {
    @FXML private Button editButton;
    @FXML private Button settingsButton;
    @FXML private Button addButton;
    @FXML private VBox dbcontent = new VBox();
    @FXML private ScrollPane dashboardList = new ScrollPane(dbcontent);

    @FXML
    private void editClicked(){
        System.out.println("edit clicked");
    }

    @FXML
    private void addClicked() throws IOException{
    	System.out.println("add clicked");
    	Node fieldcap = FXMLLoader.load(getClass().getResource("../scenes/FieldCapsuleTemplate.fxml"));
    	dbcontent.getChildren().add(fieldcap);
    	dashboardList.setContent(dbcontent);
    	/*
        Stage stage = (Stage)dashboardList.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../scenes/mapScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        */
        
    }
}
