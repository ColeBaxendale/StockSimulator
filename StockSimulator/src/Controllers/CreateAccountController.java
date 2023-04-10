package Controllers;

import java.io.IOException;

import BackEnd.Account;
import BackEnd.DataBase;
import BackEnd.Portfolio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateAccountController {

	private Stage stage;
	private Scene scene;
	DataBase databaseInstance = DataBase.getInstance();

	@FXML
	private TextField emailField;
	@FXML
	private TextField nameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField passwordFieldCheck;
	
	Alert error = new Alert(AlertType.ERROR);
	Alert success = new Alert(AlertType.INFORMATION);
	

	@FXML
	void createAccountPressed(ActionEvent event) throws IOException {
		if (fieldsCorrect()) {
			if(databaseInstance.addAccount(new Account(emailField.getText(), passwordField.getText(), nameField.getText(), new Portfolio(0)))) {
				success.setHeaderText("Success!");
				success.setContentText("Your Account Was Created!");
				success.show();
				Parent root = FXMLLoader.load(getClass().getResource("/FrontEnd/Login.fxml"));
				stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
				return;
			}
			error.setHeaderText("Account Not Created!");
			error.setContentText("Username already exists in our system!");
			error.showAndWait();
		}
		
	}

	public boolean fieldsCorrect() {
		if(emailField.getText().isBlank() || nameField.getText().isBlank()) {
			error.setContentText("Looks like some of your input fields are empty :( Try Again!");
			error.setHeaderText("Inputs Incorrect!");
			error.showAndWait();
			return false;
		}
		if(!passwordField.getText().equals(passwordFieldCheck.getText())) {
			error.setContentText("Your passwords do not match :(  Try Again!");
			error.setHeaderText("Inputs Incorrect!");
			error.showAndWait();
			return false;
		}	
		return true;	
	}

}
