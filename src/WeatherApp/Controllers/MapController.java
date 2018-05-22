package WeatherApp.Controllers;

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
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

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

    private DefaultWaypoint waypoint;

    /*
    intialises the map for display with a deault waypoint at cambridge
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GeoPosition cambridge = new GeoPosition(52.20433529134397, 0.11675804138182588);
        waypoint = new DefaultWaypoint(cambridge);

        MapCreator.createAndSetMapViewer(swingNode, waypoint);
    }

    /*
    returns to dashboard and cancels any changes
     */
    @FXML
    public void cancelClicked() throws IOException{
        Stage stage = (Stage)pane.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /*
    loads the next page and sends it the location
     */
    @FXML
    public void doneClicked() throws IOException {

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/fieldCustomisationScreen.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        FieldCustomisationController customisationController = loader.<FieldCustomisationController>getController();
        customisationController.setLoc(waypoint.getPosition());
    }

}
