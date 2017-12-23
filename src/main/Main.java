package main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import component.MainComponent;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setScene(new Scene(MainComponent.getInstance().getContainer()));
		primaryStage.show();
	}
}
