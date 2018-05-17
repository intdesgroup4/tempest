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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        FieldStore store = new FieldStore(Paths.get("src/stores/fieldStore.json"));
        try {
            updatelist(store.getFields());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    @FXML
    private void editClicked(){
        System.out.println("edit clicked");
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

}
