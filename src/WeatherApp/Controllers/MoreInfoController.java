package WeatherApp.Controllers;

import WeatherApp.model.Field;
import WeatherApp.model.Soil;
import WeatherApp.model.Weather;
import WeatherApp.service.AgroAPI;
import WeatherApp.service.AgroStore;
import WeatherApp.service.SettingsStore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MoreInfoController {

    private Field field;
    private AgroStore agroStore = new AgroStore(Paths.get("stores/fieldStore.json"), new AgroAPI(AgroAPI.loadApiKey()));

    @FXML
    private Button backButton;
    @FXML
    private VBox dbContent = new VBox();
    @FXML
    private ScrollPane dashboardList = new ScrollPane(dbContent);
    @FXML
    private Text nameText;
    @FXML
    private Pane fColour;
    @FXML
    private Text soilMoistureText;
    @FXML
    private Text soilUndergroundTempText;
    @FXML
    private Text soilGroundTempText;

    public MoreInfoController() throws IOException {
    }

    public void setField(Field field) {
        this.field = field;
        updateField();
        try {
            getWeatherForecast();
            getCurrentSoil();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateField() {
        nameText.setText(field.getName());
        fColour.setBackground(new Background(new BackgroundFill(field.getColour(), null, null)));
    }

    private void getWeatherForecast() throws IOException {
        SettingsStore settingsStore = new SettingsStore(Paths.get("stores/generalSettingsStore.json"));
        int interval = settingsStore.getFrequency() / 3;

        List<Weather> weatherList = agroStore.getForecastWeather(field);
        weatherList.add(0, agroStore.getCurrentWeather(field));

        Collections.sort(weatherList, Comparator.comparing(Weather::getWhen));

        int i = 0;
        for(Weather weather : weatherList) {
            if (i % interval == 0) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/FieldWeatherCapsuleTemplate.fxml"));
                Node fieldWeatherCapNode = loader.load();
                dbContent.getChildren().add(fieldWeatherCapNode);
                FieldWeatherCapController capController = loader.getController();
                capController.setWeather(weather,settingsStore);
            }
            i++;
        }
        dashboardList.setContent(dbContent);
    }

    private void getCurrentSoil() {
        Soil soil = agroStore.getCurrentSoil(field);
        soilGroundTempText.setText(Double.toString(soil.getSurfaceTemp()));
        soilUndergroundTempText.setText(Double.toString(soil.getUndergroundTemp()));
        soilMoistureText.setText(Double.toString(soil.getMoisture()));
    }

    @FXML
    public void backClicked() throws IOException {
        Stage stage = (Stage)backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../../scenes/dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void settingsClicked() throws IOException {
        Stage stage = (Stage)backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/GeneralSettings.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void refreshClicked() throws IOException {
        getWeatherForecast();
        getCurrentSoil();
    }

}