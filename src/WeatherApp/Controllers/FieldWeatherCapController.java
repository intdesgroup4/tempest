package WeatherApp.Controllers;

import WeatherApp.model.Weather;
import WeatherApp.service.SettingsStore;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.text.NumberFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class FieldWeatherCapController {

    @FXML
    private Text tHour;
    @FXML
    private Text tDate;
    @FXML
    private ImageView weatherIcon;
    @FXML
    private Text fTemp;
    @FXML
    private Text fTempUnit;
    @FXML
    private Text fRain;
    @FXML
    private Text fRainUnit;
    @FXML
    private Text fWindspeed;
    @FXML
    private Text fWindspeedUnit;
    @FXML
    private Text fWindDirection;
    @FXML
    private Text fHumidity;
    @FXML
    private Text fHumidityUnit;


    private double convertTemp(Weather weather, SettingsStore settingsStore) {
        if (settingsStore.getTempUnit().equals("C"))
            return weather.getTemperature() - 273.15;
        else if (settingsStore.getTempUnit().equals("F"))
            return (weather.getTemperature() * 9) / 5 - 459.67;
        else
            return weather.getTemperature();
    }

    private double convertWind(Weather weather, SettingsStore settingsStore) {
        if (settingsStore.getWindUnit().equals("mph"))
            return weather.getWindSpeed() * 2.23694;
        else if (settingsStore.getWindUnit().equals("kmph"))
            return weather.getWindSpeed() * 3.6;
        else
            return weather.getWindSpeed();
    }

    public void setWeather(Weather weather, SettingsStore settingsStore) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);


        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(Locale.UK).withZone(ZoneId.systemDefault());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                .withLocale(Locale.UK).withZone(ZoneId.systemDefault());

        tHour.setText(timeFormatter.format(weather.getWhen()));
        tDate.setText(dateFormatter.format(weather.getWhen()));

        Image image = weather.getConditions().get(0).getDayIcon();
        weatherIcon.setImage(image);

        fTemp.setText(nf.format(convertTemp(weather,settingsStore)));
        fTempUnit.setText(settingsStore.getTempUnitIcon());

        if(Double.isNaN(weather.getRainfall()))
            fRain.setText("0");
        else
            fRain.setText(nf.format(weather.getRainfall()));

        fWindspeed.setText(nf.format(convertWind(weather,settingsStore)));
        fWindspeedUnit.setText(settingsStore.getWindUnit());

        fWindDirection.setText(nf.format(weather.getWindDirection()) + "°");

        fHumidity.setText(nf.format(weather.getHumidity()));
        fHumidityUnit.setText("%");
    }

}