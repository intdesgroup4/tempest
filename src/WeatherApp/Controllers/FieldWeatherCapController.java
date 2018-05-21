package WeatherApp.Controllers;

import WeatherApp.model.Weather;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class FieldWeatherCapController {

    @FXML
    private Text fTime;
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

    public void setWeather(Weather weather) {
        fTime.setText(weather.getWhen().toString());
        Image image = weather.getConditions().get(0).getDayIcon();
        weatherIcon.setImage(image);
        fTemp.setText(Double.toString(weather.getTemperature()));
        fTempUnit.setText("K");
        if(Double.isNaN(weather.getRainfall()))
            fRain.setText("0");
        else
            fRain.setText(Double.toString(weather.getRainfall()));
        fWindspeed.setText(Double.toString(weather.getWindSpeed()));
        fWindspeedUnit.setText("m/s");
    }

}
