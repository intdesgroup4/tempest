package WeatherApp.Controllers;

import WeatherApp.service.SettingsStore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class GeneralSettingsController implements Initializable {

    private SettingsStore settingsStore;

    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox tempUnitCombo = new ComboBox<>();
    @FXML
    private ComboBox windSpeedUnitCombo = new ComboBox<>();
    @FXML
    private Slider intervalFrequencySlider;
    @FXML
    private CheckBox notificationCheckBox;


    public void initialize(URL location, ResourceBundle resources) {
        settingsStore = new SettingsStore(Paths.get("stores/generalSettingsStore.json"));
        tempUnitCombo.getItems().addAll("Celsius (°C)","Fahrenheit (°F)","Kelvin (K)");

        tempUnitCombo.setValue(settingsStore.getTempUnitFull());

        windSpeedUnitCombo.getItems().addAll("mph","kmph","m/s");
        windSpeedUnitCombo.setValue(settingsStore.getWindUnit());

        intervalFrequencySlider.setMajorTickUnit(3.);
        intervalFrequencySlider.setMinorTickCount(0);
        intervalFrequencySlider.setShowTickLabels(true);
        intervalFrequencySlider.setShowTickMarks(true);

        intervalFrequencySlider.setValue(settingsStore.getFrequency());

        notificationCheckBox.setSelected(settingsStore.getNotifications());

    }

    @FXML
    public void cancelClicked() throws IOException {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void saveClicked() throws IOException {

        settingsStore.setTempUnitFull((String)tempUnitCombo.getValue());
        settingsStore.setWindUnit((String)windSpeedUnitCombo.getValue());
        settingsStore.setNotifications(notificationCheckBox.isSelected());
        settingsStore.setFrequency((int)intervalFrequencySlider.getValue());

        settingsStore.save();

        Stage stage = (Stage)saveButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}