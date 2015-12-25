package main;

import view.Login;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Logowanie");
		primaryStage.setScene(new Login().createForm());
		primaryStage.show();
	}

}
