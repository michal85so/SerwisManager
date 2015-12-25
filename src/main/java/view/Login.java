package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Login {
	public Scene createForm() {
		Label lblUser = new Label("Użytkownik:");
		TextField tfUser = new TextField();
		tfUser.setPromptText("Nazwa użytkownika");

		Label lblPassword = new Label("Hasło:");
		PasswordField pfPassword = new PasswordField();
		pfPassword.setPromptText("Hasło");

		Button btnLogin = new Button("Zaloguj");
		
		GridPane panel = new GridPane();
		panel.setHgap(6);
		panel.setVgap(6);
		panel.setPadding(new Insets(6));
		
		panel.add(lblUser, 0, 0);
		panel.add(tfUser, 1, 0);
		panel.add(lblPassword, 0, 1);
		panel.add(pfPassword, 1, 1);
		panel.add(btnLogin, 0, 2, 2, 1);
		
		panel.setHalignment(btnLogin, HPos.CENTER);
		
		Scene scLogin = new Scene(panel);
		return scLogin;
	}
}
