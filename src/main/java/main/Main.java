package main;

import javafx.application.Application;
import javafx.stage.Stage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import view.LoginForm;
import configuration.Configuration;

public class Main extends Application{
	private static Stage primaryStage = null;
	
	static {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Configuration.class);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("Service Manager");
		createLoginForm();
	}
	
	private void createLoginForm() {
		primaryStage.setScene(new LoginForm().createForm());
		primaryStage.show();
	}
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
}
