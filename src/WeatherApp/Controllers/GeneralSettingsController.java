package WeatherApp.Controllers;

import WeatherApp.service.SettingsStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GeneralSettingsController implements Initializable {

    private SettingsStore settingsStore;

    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox tempUnitCombo = new ComboBox<>();


    public void initialize(URL location, ResourceBundle resources) {
        settingsStore = new SettingsStore(Paths.get("stores/generalSettingsStore.json"));
        tempUnitCombo.getItems().addAll("Celsius (°C)","Fahrenheit (°F)","Kelvin (K)");

        if (settingsStore.getTempUnit().equals("C"))
            tempUnitCombo.setValue("Celsius (°C)");
        else if (settingsStore.getTempUnit().equals("F"))
            tempUnitCombo.setValue("Fahrenheit (°F)");
        else
            tempUnitCombo.setValue("Kelvin (K)");



    }

    @FXML
    public void cancelClicked() throws IOException {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../../scenes/dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void saveClicked() throws IOException {
        Stage stage = (Stage)saveButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../../scenes/dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
