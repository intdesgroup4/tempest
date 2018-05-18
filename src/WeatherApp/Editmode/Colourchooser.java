package WeatherApp.Editmode;

import WeatherApp.Controllers.Controller;
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
	private Field field;
	private Controller controller;

	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox(10);
		stage.setTitle("Colour Picker");
		ColorPicker cPicker = new ColorPicker(field.getColour());
		cPicker.setMinSize(200, 50);
		cPicker.setOnAction(e->{
			root.setBackground(new Background(new BackgroundFill(cPicker.getValue(), null, null)));
		});
		
		Button selectButton = new Button("Select");
		selectButton.setMinSize(100, 30);
		selectButton.setOnAction(e -> {
			try {
				Color c = cPicker.getValue();
				field.setColour(c);
				controller.editupdate();
				stage.close();
			} catch (Exception e1) {
				System.out.println("Colourchooser problems closing application (selectButton)");
				e1.printStackTrace();
			}
		});
		
		Button cancelButton = new Button("Cancel");
		cancelButton.setMinSize(100, 30);
		cancelButton.setOnAction(e -> {
			try {
				controller.editupdate();
				stage.close();
			} catch (Exception e1) {
				System.out.println("Colourchooser problems closing application (cancelButton)");
				e1.printStackTrace();
			}
		});
		HBox buttonboard = new HBox(selectButton, cancelButton);
		buttonboard.setAlignment(Pos.CENTER);
		
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(cPicker, buttonboard);
		stage.setScene(new Scene(root, 300, 150));
		stage.setTitle("ColourPicker");
		stage.setResizable(false);
		stage.show();
		
	}
	
	public void setField(Field field) {
		this.field = field;
	}
	
	public void passcontroller(Controller controller) {
		//allows call of editupdate on closure of the colourchooser
		this.controller = controller;
	}

}
