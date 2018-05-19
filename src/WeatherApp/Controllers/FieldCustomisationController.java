package WeatherApp.Controllers;

import WeatherApp.Main;
import WeatherApp.Editmode.Colourchooser;
import WeatherApp.model.Field;
import WeatherApp.service.FieldStore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jxmapviewer.viewer.GeoPosition;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FieldCustomisationController {

    @FXML
    private Button cancelButton;
    @FXML
    private TextField nameInput;
    @FXML
    private Button doneButton;
    @FXML
    private Button colourButton;
    @FXML
    private HBox colourBox;

    private GeoPosition loc;
    //default colour for a field is white
    private Color colour = Color.WHITE;

    @FXML
    public void cancelClicked() throws IOException {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void doneClicked() throws IOException {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/dashboard.fxml"));

        Controller controller = loader.<Controller>getController();
        //controller.addFarm(new Field(nameInput.getText(), loc.getLatitude(), loc.getLongitude()));

        Field field = new Field(nameInput.getText(), loc.getLatitude(), loc.getLongitude());
        field.setColour(colour);
        FieldStore fs = new FieldStore(Paths.get("stores/fieldStore.json"));
        fs.getFields().add(field);
        fs.save();

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    
    public void colourClicked() throws Exception {
    	//bring up a new colourchooser window in which the user can select the colour of the field
    			Colourchooser cc = new Colourchooser();
    			//field yet to be created so we use a dummy one
    			Field dummy = new Field("DummyField",0,0);
    			cc.setField(dummy);
    			cc.passcontroller(this);
    			Stage colourStage = new Stage();
    			//prevents the user from clicking away from the colourchooser:
    			colourStage.initOwner(Main.getPrimaryStage());
    			colourStage.initModality(Modality.APPLICATION_MODAL);
    			cc.start(colourStage);
    }
    
    public void panelupdate(Color c) {
    	//takes in the colour from the colourchooser and updates the colour of the pane
    	colour = c;
    	colourBox.setBackground(new Background(new BackgroundFill(c, null, null)));
    	
    }

    public void setLoc(GeoPosition position) {
        loc = position;
    }

}
