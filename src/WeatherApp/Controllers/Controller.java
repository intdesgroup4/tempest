package WeatherApp.Controllers;

import WeatherApp.model.Field;
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
import org.jxmapviewer.viewer.GeoPosition;

import java.io.IOException;


public class Controller {
    @FXML
    private Button editButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button addButton;
    @FXML
    private VBox dbContent = new VBox();
    @FXML
    private ScrollPane dashboardList = new ScrollPane(dbContent);


    @FXML
    private void editClicked(){
        System.out.println("edit clicked");
    }

    @FXML
    private void addClicked() throws IOException{

        Stage stage = (Stage)addButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/fieldPositionChoice.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addFarm(Field field) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/FieldCapsuleTemplate.fxml"));
        Node fieldCapNode = loader.load();
        dbContent.getChildren().add(fieldCapNode);
        dashboardList.setContent(dbContent);
        FieldCapController capController = loader.<FieldCapController>getController();
        capController.setcoords(new GeoPosition(field.getLat(), field.getLng()));
        capController.setName(field.getName());
    }
}
