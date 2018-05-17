package WeatherApp.Controllers;

import WeatherApp.model.Field;
import WeatherApp.service.FieldStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jxmapviewer.viewer.GeoPosition;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable{
    @FXML private Button editButton;
    @FXML private Button settingsButton;
    @FXML private Button addButton;
    @FXML private VBox dbContent = new VBox();
    @FXML private ScrollPane dashboardList = new ScrollPane(dbContent);
    private List<Field> fList;
    private boolean editmode = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        FieldStore store = new FieldStore(Paths.get("stores/fieldStore.json"));
        try {
            updatelist(store.getFields());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    @FXML
    private void editClicked() throws IOException{
        editmode = !editmode;
        if(editmode) {
        	//switch the scrollpane to contain the editfieldcapsules
        	editButton.setText("Done");
        	editupdate();
        }
        else {
        	editButton.setText("Edit");
        	//switch the scrollpane to contain the fieldcapsules again
        	//send the flist off to the fieldstore
        	
        	//clear dbContent
        	dbContent = new VBox();
        	//update the scrollpane with the new edited fieldlist
        	updatelist(fList);
        }
    }

    @FXML
    private void addClicked() throws IOException{

        Stage stage = (Stage)addButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/fieldPositionChoice.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addFarm(Field field) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/FieldCapsuleTemplate.fxml"));
        Node fieldCapNode = loader.load();
        dbContent.getChildren().add(fieldCapNode);
        dashboardList.setContent(dbContent);
        FieldCapController capController = loader.<FieldCapController>getController();
        capController.setcoords(field.getLat(), field.getLng());
        capController.setName(field.getName());
    }
    
    private void updatelist(List<Field> fieldlist) throws IOException {
    	//updates the dashboard's field list by reading through the inputed fieldlist
    	fList = fieldlist;
    	for(Field field: fieldlist) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/FieldCapsuleTemplate.fxml"));
    		Node fieldCapNode = loader.load();
    		dbContent.getChildren().add(fieldCapNode);
    		FieldCapController capController = loader.<FieldCapController>getController();
            capController.setcoords(field.getLat(), field.getLng());
            capController.setName(field.getName());	
    	}
    	dashboardList.setContent(dbContent);
    }

	public void setfList(List<Field> fList) {
		this.fList = fList;
	}
	
	public void editupdate() throws IOException {
		//updates the edit panels
		dbContent = new VBox();
		for(Field field: fList) {
    		FXMLLoader ploader = new FXMLLoader(getClass().getResource("/scenes/editFieldCap.fxml"));
    		Node editCapNode = ploader.load();
    		dbContent.getChildren().add(editCapNode);
    		EditFieldCapController capController = ploader.<EditFieldCapController>getController();    
            capController.setField(field);
            capController.passlist(fList);
            capController.passparent(this);
    	}
    	dashboardList.setContent(dbContent);
	}
	
	public void removeFarm(Field field) throws IOException {
		fList.remove(field);
		editupdate();
	}

}
