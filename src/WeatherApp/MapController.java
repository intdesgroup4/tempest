package WeatherApp;

import WeatherApp.Map.MapCreator;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MapController implements Initializable{


    @FXML
    private SwingNode swingNode;
    @FXML
    private StackPane pane;
    @FXML
    private Button doneButton;
    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MapCreator.createAndSetMapViewer(swingNode);
    }

    @FXML
    public void cancelClicked() throws IOException{
        Stage stage = (Stage)pane.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../scenes/dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
