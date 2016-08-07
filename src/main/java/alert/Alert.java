package alert;

import javafx.scene.control.Alert.AlertType;

public class Alert {
	public static void error() {
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.ERROR);
		alert.setTitle("Wrong login or password");
		alert.setHeaderText("Your login or password is not correct!");
		alert.setContentText("Please enter your login and password again.");
		alert.showAndWait();
	}
}
