package WeatherApp.Controllers;

import WeatherApp.model.Field;
import WeatherApp.service.FieldStore;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
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
        	addButton.setVisible(false);
        	editupdate();
        }
        else {
        	editButton.setText("Edit");
        	addButton.setVisible(true);
        	//switch the scrollpane to contain the fieldcapsules again
        	//send the flist off to the fieldstore
        	
        	//clear dbContent

        	//update the scrollpane with the new edited fieldlist
        	updatelist(fList);
        }
    }

    @FXML
    private void settingsClicked() throws IOException {
	    Stage stage = (Stage)addButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/GeneralSettings.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
        capController.setField(field);
    }
    
    private void updatelist(List<Field> fieldlist) throws IOException {
    	//updates the dashboard's field list by reading through the inputed fieldlist
    	fList = fieldlist;

    	// for each field load the template and create the node for the field capsule
    	for(Field field: fieldlist) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/FieldCapsuleTemplate.fxml"));
    		Node fieldCapNode = loader.load();

    		// add click event handler
            fieldCapNode.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        Stage stage = (Stage) editButton.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/moreInfo.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);

                        MoreInfoController moreInfoController = loader.<MoreInfoController>getController();
                        moreInfoController.setField(field);

                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            // add the capsule to dashboard content
    		dbContent.getChildren().add(fieldCapNode);

    		FieldCapController capController = loader.<FieldCapController>getController();
            capController.setField(field);
            capController.updateToCurrentWeather();
    	}

    	dashboardList.setContent(dbContent);
    }

	public void setfList(List<Field> fList) {
		this.fList = fList;
	}
	
	public void editupdate() throws IOException {
		//updates the edit panels
		VBox dbContentNew = new VBox();

		for(Field field: fList) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/editmodeCapsule.fxml"));
    		Node editCapNode = loader.load();
    		dbContentNew.getChildren().add(editCapNode);
    		EditFieldCapController capController = loader.<EditFieldCapController>getController();
            capController.setField(field);
            capController.passlist(fList);
            capController.passparent(this);
    	}
    	dashboardList.setContent(dbContentNew);
	}
	
	public void removeFarm(Field field) throws IOException {
		fList.remove(field);
		editupdate();
	}
}
