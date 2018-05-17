package WeatherApp.Controllers;

import WeatherApp.model.Field;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jxmapviewer.viewer.GeoPosition;

import java.io.IOException;

public class FieldCustomisationController {

    @FXML
    private Button cancelButton;
    @FXML
    private TextField nameInput;
    @FXML
    private Button doneButton;

    private GeoPosition loc;

    @FXML
    public void cancelClicked() throws IOException {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void doneClicked() throws IOException {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/dashboard.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        Controller controller = loader.<Controller>getController();
        controller.addFarm(new Field(nameInput.getText(), loc.getLatitude(), loc.getLongitude()));

    }

    public void setLoc(GeoPosition position) {
        loc = position;
    }

}
