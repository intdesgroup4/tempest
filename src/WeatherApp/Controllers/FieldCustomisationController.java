package WeatherApp.Controllers;

import WeatherApp.model.Field;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.jxmapviewer.viewer.GeoPosition;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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



        Controller controller = loader.<Controller>getController();
        //controller.addFarm(new Field(nameInput.getText(), loc.getLatitude(), loc.getLongitude()));

        JsonObject field = new JsonObject();
        field.addProperty("name",nameInput.getText());
        field.addProperty("latitude",loc.getLatitude());
        field.addProperty("longitude", loc.getLongitude());

        try (Writer writer = new FileWriter("src/stores/fieldStore.json",true)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(field, writer);
            writer.append('\n');
        }

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void setLoc(GeoPosition position) {
        loc = position;
    }

}
