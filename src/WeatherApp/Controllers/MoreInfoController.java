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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MoreInfoController {

    private Field field;
    private AgroStore agroStore = new AgroStore(Paths.get("stores/agroStore.json"), new AgroAPI(AgroAPI.loadApiKey()));

    @FXML
    private Button backButton;
    @FXML
    private VBox dbContent = new VBox();
    @FXML
    private ScrollPane dashboardList = new ScrollPane(dbContent);
    @FXML
    private Text nameText;
    @FXML
    private Rectangle fColourRec;
    @FXML
    private Text soilMoistureText;
    @FXML
    private Text soilUndergroundTempText;
    @FXML
    private Text soilGroundTempText;

    public MoreInfoController() throws IOException {
    }

    /*
    sets the field stored in this page
     */
    public void setField(Field field) {
        this.field = field;
        updateField();
        try {
            SettingsStore settingsStore = new SettingsStore(Paths.get("stores/generalSettingsStore.json"));
            getWeatherForecast(settingsStore);
            getCurrentSoil(settingsStore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    updates the display for the fields data
     */
    private void updateField() {
        nameText.setText(field.getName());
        Color c = field.getColour();
        fColourRec.setFill(c);
    }

    /*
    gets the weather forecast data and adds it to the display
     */
    private void getWeatherForecast(SettingsStore settingsStore) throws IOException {
        int interval = settingsStore.getFrequency() / 3;

        agroStore.load();
        List<Weather> weatherList = agroStore.getForecastWeather(field);
        weatherList.add(0, agroStore.getCurrentWeather(field));
        agroStore.save(); // save any fetched data for caching

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

    /*
    gets the values of the current soil data and updates it
     */
    private void getCurrentSoil(SettingsStore settingsStore) {
        agroStore.load();
        Soil soil = agroStore.getCurrentSoil(field);
        agroStore.save(); // save any fetched data for caching
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        soilGroundTempText.setText(nf.format(convertTemp(soil.getSurfaceTemp(),settingsStore)) + settingsStore.getTempUnitIcon());
        soilUndergroundTempText.setText(nf.format(convertTemp(soil.getUndergroundTemp(),settingsStore)) + settingsStore.getTempUnitIcon());
        soilMoistureText.setText(nf.format(soil.getMoisture()));
    }

    /*
    converts the temp into the users choice
     */
    private double convertTemp(double kelvin, SettingsStore settingsStore) {
        if (settingsStore.getTempUnit().equals("C"))
            return kelvin - 273.15;
        else if (settingsStore.getTempUnit().equals("F"))
            return (kelvin * 9) / 5 - 459.67;
        else
            return kelvin;
    }

    /*
    returns to the dashboard
     */
    @FXML
    public void backClicked() throws IOException {
        Stage stage = (Stage)backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../../scenes/dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /*
    goes to the settings page
     */
    @FXML
    public void settingsClicked() throws IOException {
        Stage stage = (Stage)backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/GeneralSettings.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /*
    loads the settings page
     */
    @FXML
    public void refreshClicked() throws IOException {
        SettingsStore settingsStore = new SettingsStore(Paths.get("stores/generalSettingsStore.json"));
        getWeatherForecast(settingsStore);
        getCurrentSoil(settingsStore);
    }

}