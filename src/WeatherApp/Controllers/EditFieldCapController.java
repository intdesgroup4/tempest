package WeatherApp.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import WeatherApp.model.Field;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EditFieldCapController implements Initializable{
	
	@FXML private Button dragButton;
	@FXML private Button deleteButton;
	@FXML private Button editImageButton;
	@FXML private TextField defName;
	//the field that this edit panel corresponds to
	private Field field;
	//the current list of fields in the dashboard
	private List<Field> fList;
	//the main Controller so we can call methods from it
	private Controller parent;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		defName.setText("Oops....");
	}
	
	public void passlist(List<Field> fList) {
		this.fList = fList;
	}
	
	public void deleteClicked() throws IOException {
		fList.remove(field);
		parent.editupdate();
	}
	
	public void imageClicked() {
		
	}
	
	public void nameEdit() {
		field.setName(defName.getText());
	}
	
	public void setField(Field field) {
		this.field = field;
		this.defName.setText(field.getName());
	}

	public void passparent(Controller controller) {
		parent = controller;
	}

}
