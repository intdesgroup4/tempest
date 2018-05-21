package WeatherApp.Controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import WeatherApp.model.Field;
import WeatherApp.model.Weather;
import WeatherApp.service.AgroAPI;
import WeatherApp.service.AgroStore;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class FieldCapController implements Initializable{

	@FXML private Text fName;
	@FXML private Text fTemp;
	@FXML private Text fTempUnit;
	@FXML private Text fRain;
	@FXML private Text fRainUnit;
	@FXML private Text fWindspeed;
	@FXML private Text fWindspeedUnit;
	@FXML private Text latitude;
	@FXML private Text longitude;
	@FXML private Pane fColour;
	private Field field;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fName.setText("test");
	}
	
	public void setcoords(double lat, double lon) {
		latitude.setText(Double.toString(lat));
		longitude.setText(Double.toString(lon));
	}

	public void setName(String txt) {
		fName.setText(txt);
	}
	
	public void setRain(String txt) {
		fRain.setText(txt);
	}
	
	public void setRainUnit(String txt) {
		fRainUnit.setText(txt);
	}
	
	public void setTemp(String txt) {
		fTemp.setText(txt);
	}
	
	public void setTempUnit(String txt) {
		fTempUnit.setText(txt);
	}
	
	public void setWindspeed(String txt) {
		fWindspeed.setText(txt);
	}
	
	public void setWindspeedUnit(String txt) {
		fWindspeedUnit.setText(txt);
	}
	
	public void setField(Field field) {
		this.field = field;
		fColour.setBackground(new Background(new BackgroundFill(field.getColour(), null, null)));
		latitude.setText(Double.toString(field.getLat()));
		longitude.setText(Double.toString(field.getLng()));
		fName.setText(field.getName());
	}

	public void updateToCurrentWeather() {
        try {
			AgroStore agroStore = new AgroStore(Paths.get("stores/fieldStore.json"), new AgroAPI(AgroAPI.loadApiKey()));
            Weather weather = agroStore.getCurrentWeather(field);
            fTemp.setText(Double.toString(weather.getTemperature()));
            fTempUnit.setText("K");
            if(Double.isNaN(weather.getRainfall()))
                fRain.setText("0");
            else
                fRain.setText(Double.toString(weather.getRainfall()));
            fWindspeed.setText(Double.toString(weather.getWindSpeed()));
            fWindspeedUnit.setText("m/s");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
