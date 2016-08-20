package view;

import java.util.Optional;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import repository.SqliteJdbcTemplate;
import domain.Person;
import domain.ServiceStatus;


public class ClientForm {
	
	public static Person addClient() {
		Dialog<Person> dialog = new Dialog<>();
		dialog.setTitle("Add Client");
		dialog.setHeaderText("Provide new Client data");
		dialog.setGraphic(new ImageView(new Image("icons/add_person.png")));
		return createDialog(dialog);
	}
	
	private static Person createDialog(Dialog<Person> dialog) {
		ButtonType buttonType = new ButtonType("Add", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);
		
		GridPane panel = new GridPane();
		panel.setHgap(10);
		panel.setVgap(10);
		panel.setPadding(new Insets(20, 150, 10, 10));
		
		
		panel.add(new Label("First Name:"), 0, 0);
		TextField firstNameTf = new TextField();
		panel.add(firstNameTf, 1, 0);
		panel.add(new Label("Last Name:"), 2, 0);
		TextField lastNameTf = new TextField();
		panel.add(lastNameTf, 3, 0);
		panel.add(new Label("Phone number:"), 0, 1);
		TextField phoneNrTf = new TextField();
		panel.add(phoneNrTf, 1, 1);
		panel.add(new Label("Email:"), 2, 1);
		TextField emailTf = new TextField();
		panel.add(emailTf, 3, 1);
		panel.add(new Label("Person type:"), 0, 2);
		ComboBox<String> personType = new ComboBox<String>(FXCollections.observableArrayList(ServiceStatus.listOfStatus.values()));
		panel.add(personType, 1, 2);
		panel.add(new Label("Billing"), 0, 3);
		panel.add(new Label("Company's Name:"), 0, 4);
		TextField companyNameTf = new TextField();
		panel.add(companyNameTf, 1, 4);
		panel.add(new Label("Address:"), 0, 5);
		TextField addressTf = new TextField();
		panel.add(addressTf, 1, 5,3,1);
		panel.add(new Label("Postal code:"), 0, 6);
		TextField postalCodeTf = new TextField();
		panel.add(postalCodeTf, 1, 6);
		panel.add(new Label("City:"), 2, 6);
		TextField cityTf = new TextField();
		panel.add(cityTf, 3, 6);
		panel.add(new Label("NIP:"), 0, 7);
		TextField nipTf = new TextField();
		panel.add(nipTf, 1, 7);
		
		dialog.getDialogPane().setContent(panel);
		
		dialog.setResultConverter(btn -> {
			if (btn == buttonType) {
				return new Person.Builder()
					.firstName(firstNameTf.getText())
					.lastName(lastNameTf.getText())
					.phoneNumber(phoneNrTf.getText())
					.email(emailTf.getText())
					.kindOfPerson(ServiceStatus.getKeyByValue(personType.getSelectionModel().getSelectedItem()))
					.build();
			}
			return null;
		});
		
		Optional<Person> person = dialog.showAndWait();
		if (person.isPresent()) {
			SqliteJdbcTemplate.getJdbcTemplate().update("insert into person(first_name,last_name, phone_number, email, person_status_id) values ('" + person.get().getFirstName() + "', '" 
					+ person.get().getLastName() + "', '" + person.get().getPhoneNumber() + "', '" + person.get().getEmail() + "', " + person.get().getKindOfPerson() + ")");
			return person.get();
		}
		return null;
	}
}
