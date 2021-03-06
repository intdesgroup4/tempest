package WeatherApp.Controllers;

import WeatherApp.Exceptions.InvalidCoordinateException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jxmapviewer.viewer.GeoPosition;

import java.io.IOException;

public class CoordinateController {

    @FXML
    private Button cancelButton;
    @FXML
    private Button doneButton;
    @FXML
    private TextField latInput;
    @FXML
    private TextField longInput;
    @FXML
    private Text errorM;

    /*
    cancels the process and re-loads the dashboard
     */
    @FXML
    public void cancelClicked() throws IOException {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /*
    continues after the coordinates have been set
     */
    @FXML
    public void doneClicked() throws IOException {
        try {

            GeoPosition pos = getPostion();

            Stage stage = (Stage) cancelButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/fieldCustomisationScreen.fxml"));



            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            FieldCustomisationController customisationController = loader.<FieldCustomisationController>getController();
            customisationController.setLoc(pos);
        } catch (InvalidCoordinateException e) {
            errorM.setVisible(true);
        }
    }

    /*
    generates a GeoPosition object from the values in the boxes
     */
    private GeoPosition getPostion() throws InvalidCoordinateException {
        double lat,lng;
        try {
            lat = Double.parseDouble(latInput.getText());
            lng = Double.parseDouble(longInput.getText());
        } catch (NumberFormatException e) {
            errorM.setText(" \n Please enter valid numbers as coordinates \n ");
            throw new InvalidCoordinateException();
        }

        if(lat >90.0 || lat < -90.0 || lng > 180.0 || lng < -180.0) {
            errorM.setText("Please ensure that coordinates are in a valid range \nLatitude: between -90.0 and 90.0 \nLongitude: between -180.0 and 180.0");
            throw new InvalidCoordinateException();
        }


        return new GeoPosition(lat,lng);
    }

}
