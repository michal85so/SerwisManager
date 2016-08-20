package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
	private List<Person> query;
	
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
		addPersonBtn.setOnAction(openClientFormToAdd());
		
		FlowPane panel = new FlowPane();
		panel.setHgap(50);
		panel.setPadding(new Insets(10));
		
		panel.getChildren().add(addPersonBtn);
		
		return panel;
	}
	
	private EventHandler<ActionEvent> openClientFormToAdd() {
		return e -> {
			Person addClient = ClientForm.addClient();
			if (addClient != null){
				observableArrayList.add(addClient);
				query.add(addClient);
			}
			table.refresh();
		};
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Pane createList() {
		Label label = new Label("List of: ");
		label.setFont(new Font("Arial", 20));
		
		ComboBox<String> typeOfList = new ComboBox<String>();
		typeOfList.getItems().addAll("All", "Clients", "Personel");
		typeOfList.setOnAction(filterListAfterComboboxChange());
		typeOfList.getSelectionModel().selectFirst();
		
		table = new TableView<Person>();
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		TableColumn idCol = new TableColumn("Id");
		idCol.setMinWidth(50);
		idCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>("id"));
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
		
		query = getAllPerson();
		observableArrayList = FXCollections.observableArrayList(query);
		table.setItems(observableArrayList);
		
		table.getColumns().addAll(idCol, firstNameCol, lastNameCol, emailCol, phoneNumberCol);
		
		VBox box = new VBox();
		box.setSpacing(5);
		box.setPadding(new Insets(10, 10, 10, 10));
		FlowPane pane = new FlowPane();
		pane.getChildren().addAll(label, typeOfList);
		box.getChildren().addAll(pane, table);
		
		return box;
	}
	
	private List<Person> getAllPerson() {
		return SqliteJdbcTemplate.getJdbcTemplate().query("select id, first_name, last_name, email, phone_number, person_status_id from person", new RowMapper<Person>() {
			public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Person.Builder()
					.id(rs.getInt("id"))
					.firstName(rs.getString("first_name"))
					.lastName(rs.getString("last_name"))
					.email(rs.getString("email"))
					.phoneNumber(rs.getString("phone_number"))
					.kindOfPerson(rs.getInt("person_status_id"))
					.build();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	private EventHandler<ActionEvent> filterListAfterComboboxChange() {
		return event -> {
			observableArrayList = FXCollections.observableArrayList(query.stream()
					.filter(p -> {
						if ("All".equalsIgnoreCase(((ComboBox<String>)event.getSource()).getSelectionModel().getSelectedItem()))
							return true;
						if ("Clients".equalsIgnoreCase(((ComboBox<String>)event.getSource()).getSelectionModel().getSelectedItem()))
							return p.getKindOfPerson() == 5;
						return p.getKindOfPerson() != 5;
					})
					.collect(Collectors.toList()));
			table.setItems(observableArrayList);
			table.refresh();
		};
	}
}
