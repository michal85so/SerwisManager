package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
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
import main.Main;

import org.springframework.jdbc.core.RowMapper;

import repository.SqliteJdbcTemplate;
import domain.Person;
import domain.Service;

public class MainForm {
	private TableView<Service> table;
	private ObservableList<Service> observableArrayList;
	
	public MainForm() {
		BorderPane panel = new BorderPane();
		
		panel.setTop(createTopPanel());
		panel.setCenter(createList());
		
		Scene scene = new Scene(panel);
		Main.getPrimaryStage().setScene(scene);
		Main.getPrimaryStage().show();
	}
	
	private FlowPane createTopPanel() {
		FlowPane panel = new FlowPane();
		
		Image personsListIcon = new Image("icons/persons_list.png",50,50,true,true);
		Button personsListBtn = new Button("Person's List", new ImageView(personsListIcon));
		personsListBtn.setContentDisplay(ContentDisplay.TOP);
		personsListBtn.setOnAction(e -> new ClientList().showForm());
		
		panel.getChildren().addAll(personsListBtn);
		
		return panel;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Pane createList() {
		Label label = new Label("Client List");
		label.setFont(new Font("Arial", 20));
		table = new TableView<Service>();
		
		TableColumn personIdCol = new TableColumn("Person ID");
		personIdCol.setMinWidth(100);
		personIdCol.setCellValueFactory(new PropertyValueFactory<Person, String>("id"));
		TableColumn nameCol = new TableColumn("Name");
		nameCol.setMinWidth(100);
		nameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		TableColumn dateOfOrderCol = new TableColumn("Date of order");
		dateOfOrderCol.setMinWidth(250);
		dateOfOrderCol.setCellValueFactory(new PropertyValueFactory<Person, String>("dateOfOrder"));
		TableColumn statusCol = new TableColumn("Status");
		statusCol.setMinWidth(150);
		statusCol.setCellValueFactory(new PropertyValueFactory<Person, String>("statusId"));
		
		List<Service> query = SqliteJdbcTemplate.getJdbcTemplate().query("select first_name, last_name, email, phone_number from person", new RowMapper<Service>() {
			public Service mapRow(ResultSet rs, int rowNum) throws SQLException {
				Service service = new Service();
				
				return service;
			}
		});
		observableArrayList = FXCollections.observableArrayList(query);
		table.setItems(observableArrayList);
		table.getColumns().addAll(personIdCol, nameCol, dateOfOrderCol, statusCol);
		
		VBox box = new VBox();
		box.setSpacing(5);
		box.setPadding(new Insets(10, 10, 10, 10));
		box.getChildren().addAll(label,table);
		
		return box;
	}
}
