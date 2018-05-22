package WeatherApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	//store the primarystage so we can have modality of colourchooser popup window (prevent user clicking back to main window
	private static Stage pStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
    	pStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/dashboard.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Tempest");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    
    public static Stage getPrimaryStage() {
    	//returns the primary stage
    	return pStage;
    }
}
