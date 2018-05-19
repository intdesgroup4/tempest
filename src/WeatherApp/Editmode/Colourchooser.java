package WeatherApp.Editmode;

import WeatherApp.Controllers.Controller;
import WeatherApp.Controllers.FieldCustomisationController;
import WeatherApp.model.Field;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Colourchooser extends Application {
	/*
	 * A separate popup window that allows you to set the colour of a field
	 * brought up by "edit colour" and "add a field"
	 */
	//the field for which you are setting the colour
	private Field field;
	//the host window for editmode so we can call editupdate to have the panels respond to the colour choice
	private Controller controller;
	//the host window when in addfield so we can call panelupdate "..."
	private FieldCustomisationController fcontroller;
	//if we're in edit mode or not (if we need to do editupdate)
	private boolean editmode = false;

	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox(10);
		stage.setTitle("Colour Picker");
		//set the starting selected colour as the field's saved colour
		ColorPicker cPicker = new ColorPicker(field.getColour());
		cPicker.setMinSize(200, 50);
		//on clicking of a colour the background of the window is set to that colour
		cPicker.setOnAction(e->{
			root.setBackground(new Background(new BackgroundFill(cPicker.getValue(), null, null)));
		});
		
		//select button on press saves the colour to the field and closes the colourchooser
		Button selectButton = new Button("Select");
		selectButton.setMinSize(100, 30);
		selectButton.setOnAction(e -> {
			try {
				Color c = cPicker.getValue();
				field.setColour(c);
				if(editmode) controller.editupdate();
				else fcontroller.panelupdate(c);
				stage.close();
			} catch (Exception e1) {
				System.out.println("Colourchooser problems closing application (selectButton)");
				e1.printStackTrace();
			}
		});
		
		//cancel button just closes the colourchooser without saving the colour
		Button cancelButton = new Button("Cancel");
		cancelButton.setMinSize(100, 30);
		cancelButton.setOnAction(e -> {
			try {
				stage.close();
			} catch (Exception e1) {
				System.out.println("Colourchooser problems closing application (cancelButton)");
				e1.printStackTrace();
			}
		});
		
		//hbox contains the select and cancel buttons
		HBox buttonboard = new HBox(selectButton, cancelButton);
		buttonboard.setAlignment(Pos.CENTER);
		
		//add the colourpicker and buttonboard to the root VBox
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(cPicker, buttonboard);
		//set the scene and show to user
		stage.setScene(new Scene(root, 300, 150));
		stage.setTitle("ColourPicker");
		stage.setResizable(false);
		stage.show();
		
	}
	
	public void setField(Field field) {
		//sets the field this colourchooser will edit the colour of
		this.field = field;
	}
	
	public void passcontroller(Controller controller) {
		//allows call of editupdate on closure of the colourchooser
		//this means the colours for each edit panel will update to those chosen
		this.controller = controller;
		editmode = true;
	}
	
	public void passcontroller(FieldCustomisationController controller) {
		//allows call of panelupdate
		this.fcontroller = controller;
	}

}
