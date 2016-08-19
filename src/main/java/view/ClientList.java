package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import org.springframework.jdbc.core.RowMapper;

import repository.SqliteJdbcTemplate;
import domain.Person;

public class ClientList {
	
	private TableView<Person> table;
	private ObservableList<Person> observableArrayList;
	
	public ClientList() {}
	public Person showForm() {
		Dialog<Person> dialog = new Dialog<>();
		dialog.setTitle("Client list");
		
		ButtonType buttonType = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);
		BorderPane panel = new BorderPane();
		
		panel.setTop(createButtonPanel());
		panel.setCenter(createList());
		
		dialog.getDialogPane().setContent(panel);
		
		dialog.showAndWait();
		
		return table.getSelectionModel().getSelectedItem();
	}
	
	private Pane createButtonPanel() {
		Image personsIcon = new Image("icons/add_person.png",50,50,true,true);
		Button addPersonBtn = new Button("Add Person", new ImageView(personsIcon));
		addPersonBtn.setContentDisplay(ContentDisplay.TOP);
		addPersonBtn.setOnAction(e -> {
			Person addClient = ClientForm.addClient();
			if (addClient != null){
				observableArrayList.add(addClient);
			}
			table.refresh();
		});
		
		FlowPane panel = new FlowPane();
		panel.setHgap(50);
		panel.setPadding(new Insets(10));
		
		panel.getChildren().add(addPersonBtn);
		
		return panel;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Pane createList() {
		Label label = new Label("Client List");
		label.setFont(new Font("Arial", 20));
		table = new TableView<Person>();
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		TableColumn firstNameCol = new TableColumn("First Name");
		firstNameCol.setMinWidth(100);
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
		TableColumn lastNameCol = new TableColumn("Last Name");
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
		TableColumn emailCol = new TableColumn("Email");
		emailCol.setMinWidth(250);
		emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
		TableColumn phoneNumberCol = new TableColumn("Phone Number");
		phoneNumberCol.setMinWidth(150);
		phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Person, String>("phoneNumber"));
		
		List<Person> query = SqliteJdbcTemplate.getJdbcTemplate().query("select id, first_name, last_name, email, phone_number from person", new RowMapper<Person>() {
			public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Person.Builder()
					.id(rs.getInt("id"))
					.firstName(rs.getString("first_name"))
					.lastName(rs.getString("last_name"))
					.email(rs.getString("email"))
					.phoneNumber(rs.getString("phone_number"))
					.build();
			}
		});
		observableArrayList = FXCollections.observableArrayList(query);
		table.setItems(observableArrayList);
		table.getColumns().addAll(firstNameCol, lastNameCol, emailCol, phoneNumberCol);
		
		VBox box = new VBox();
		box.setSpacing(5);
		box.setPadding(new Insets(10, 10, 10, 10));
		box.getChildren().addAll(label,table);
		
		return box;
	}
}
