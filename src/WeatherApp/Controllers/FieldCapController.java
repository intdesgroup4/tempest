package WeatherApp.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import org.jxmapviewer.viewer.GeoPosition;
import com.sun.javafx.geom.Rectangle;

import WeatherApp.model.Field;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
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
}
