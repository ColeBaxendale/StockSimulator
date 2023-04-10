package Controllers;

import java.io.IOException;

import BackEnd.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    
    DataBase databaseInstance = DataBase.getInstance(); 
    private Stage stage;
    private Scene scene; 
    Alert error = new Alert(AlertType.ERROR);

    @FXML
    void createAccountPressed(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/FrontEnd/CreateAccount.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void loginPressed(ActionEvent event) throws IOException {
    
    	if(databaseInstance.getAccount(usernameField.getText(),passwordField.getText())){
        	Parent root = FXMLLoader.load(getClass().getResource("/FrontEnd/MainScreen.fxml"));
        	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        	scene = new Scene(root);
        	stage.setScene(scene);
        	stage.show();
        	return;
    	}
    	error.setHeaderText("Incorrect Login!");
		error.setContentText("Looks like you have not entered the correct login :( Try Again!");
		error.showAndWait();
    	

    }
}
