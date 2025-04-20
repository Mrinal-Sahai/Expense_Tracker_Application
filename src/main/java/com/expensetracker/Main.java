package com.expensetracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml")); 
	    Scene scene = new Scene(fxmlLoader.load());
	    primaryStage.setTitle("Expense Tracker Application");
		
		Image image = new Image(getClass().getResource("/images/icon.jpg").toString());
		primaryStage.getIcons().add(image);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}
    public static void main(String[] args) {
        launch();
    }
}
