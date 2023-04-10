package application;
	
import BackEnd.DataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FrontEnd/Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DataBase.load();
		launch(args);
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				DataBase.save();
				System.out.println("Exiting");
			}
		});
		
	}
}
