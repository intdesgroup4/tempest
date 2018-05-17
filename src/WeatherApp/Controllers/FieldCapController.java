package WeatherApp.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.jxmapviewer.viewer.GeoPosition;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fName.setText("test");
	}
	
	public void setcoords(GeoPosition gp) {
		latitude.setText(Double.toString(gp.getLatitude()));
		longitude.setText(Double.toString(gp.getLongitude()));
	}

	public void setName(String txt) {
		fName.setText(txt);
	}
	

}
