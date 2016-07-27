package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import view.loginEvents.LoginBtnEvent;

public class LoginForm {
	private Label lblUser;
	private TextField tfUser;
	private Label lblPassword;
	private PasswordField pfPassword;
	private Button btnLogin;
	private GridPane panel;
	
	public LoginForm() {
	}
	
	public Scene createForm() {
		lblUser = new Label("User:");
		tfUser = new TextField();
		tfUser.setPromptText("User name");

		lblPassword = new Label("Password:");
		pfPassword = new PasswordField();
		pfPassword.setPromptText("User password");

		btnLogin = new Button("Login");
		
		panel = new GridPane();
		panel.setHgap(6);
		panel.setVgap(6);
		panel.setPadding(new Insets(6));
		
		panel.add(lblUser, 0, 0);
		panel.add(tfUser, 1, 0);
		panel.add(lblPassword, 0, 1);
		panel.add(pfPassword, 1, 1);
		panel.add(btnLogin, 0, 2, 2, 1);
		
		GridPane.setHalignment(btnLogin, HPos.CENTER);
		
		Scene scLogin = new Scene(panel);
		
		setEnabledControls();
		
		btnLogin.setOnAction(new LoginBtnEvent(tfUser.textProperty(), pfPassword.getText()));
		return scLogin;
	}
	
	private void setEnabledControls() {
		pfPassword.disableProperty().bind(tfUser.textProperty().isEmpty());
		btnLogin.disableProperty().bind(pfPassword.textProperty().isEmpty().or(tfUser.textProperty().isEmpty()));
	}
}
