package WeatherApp.Controllers;

import WeatherApp.model.Weather;
import WeatherApp.service.SettingsStore;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
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
    /*
    @FXML
    private ImageView fTemperatureIcon;
    @FXML
    private ImageView fRainfallIcon;
    @FXML
    private ImageView fWindSpeedIcon;
    @FXML
    private ImageView fWindDirectionIcon;
    @FXML
    private ImageView fHumidityIcon;
	*/

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

        fWindDirection.setText(nf.format(weather.getWindDirection()) + "");

        fHumidity.setText(nf.format(weather.getHumidity()));
        fHumidityUnit.setText("%");
        
        /*Setting WeatherIcons:
        File tempIconFile = new File("resources/weather-symbols/TemperatureIcon.png");
        Image tempIcon = new Image(tempIconFile.toURI().toString());
        fTemperatureIcon.setImage(tempIcon);
        
        File RainfallIconFile = new File("resources/weather-symbols/RainfallIcon.png");
        Image RainfallIcon = new Image(RainfallIconFile.toURI().toString());
        fRainfallIcon.setImage(RainfallIcon);
        
        File WindSpeedIconFile = new File("resources/weather-symbols/WindSpeedIcon.png");
        Image WindSpeedIcon = new Image(WindSpeedIconFile.toURI().toString());
        fWindSpeedIcon.setImage(WindSpeedIcon);
        
        File WindDirectionIconFile = new File("resources/weather-symbols/WindDirectionIcon.png");
        Image WindDirectionIcon = new Image(WindDirectionIconFile.toURI().toString());
        fWindDirectionIcon.setImage(WindDirectionIcon);
        
        File HumidityIconFile = new File("resources/weather-symbols/HumidityIcon.png");
        Image HumidityIcon = new Image(HumidityIconFile.toURI().toString());
        fHumidityIcon.setImage(HumidityIcon);
        */
        
    }

}