package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import main.Main;

import org.springframework.jdbc.core.RowMapper;

import repository.SqliteJdbcTemplate;
import domain.Person;
import domain.Service;
import domain.ServiceStatus;

public class ServiceListForm {
	private TableView<Service> table;
	private ObservableList<Service> observableArrayList;

	public ServiceListForm() {
		BorderPane panel = new BorderPane();

		panel.setTop(createTopPanel());
		panel.setCenter(createList());
		panel.setBottom(createSearchPanel());

		Scene scene = new Scene(panel);
		Main.getPrimaryStage().setScene(scene);
		Main.getPrimaryStage().show();
	}

	private FlowPane createTopPanel() {
		FlowPane panel = new FlowPane();

		Image serviceListIcon = new Image("icons/add_service.png", 50, 50, true, true);
		Button serviceListBtn = new Button("New service", new ImageView(serviceListIcon));
		serviceListBtn.setContentDisplay(ContentDisplay.TOP);
		serviceListBtn.setOnAction(openServiceFormToAdd());

		Image personsListIcon = new Image("icons/persons_list.png", 50, 50, true, true);
		Button personsListBtn = new Button("Person's List", new ImageView(personsListIcon));
		personsListBtn.setContentDisplay(ContentDisplay.TOP);
		personsListBtn.setOnAction(e -> {
			new ClientList().showForm();
		});

		Image enviromentListIcon = new Image("icons/enviroment_list.png", 50, 50, true, true);
		Button enviromentListBtn = new Button("Enviroments", new ImageView(enviromentListIcon));
		enviromentListBtn.setContentDisplay(ContentDisplay.TOP);
		enviromentListBtn.setOnAction(e -> {
			new EnviromentList().showForm();
		});

		panel.setHgap(50);
		panel.setPadding(new Insets(10));
		panel.getChildren().addAll(serviceListBtn, personsListBtn, enviromentListBtn);

		return panel;
	}

	private EventHandler<ActionEvent> openServiceFormToAdd() {
		return e -> {
			Service service = new ServiceForm().createService();
			if (service != null) {
				observableArrayList.add(service);
			}
			table.refresh();
		};
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Pane createList() {
		Label label = new Label("Service List");
		label.setFont(new Font("Arial", 20));

		table = new TableView<Service>();
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setRowFactory(openServiceFormToEdit());

		TableColumn idCol = new TableColumn("ID");
		idCol.setMinWidth(50);
		idCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>("id"));
		TableColumn personIdCol = new TableColumn("Client ID");
		personIdCol.setMinWidth(80);
		personIdCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>("clientId"));
		TableColumn nameCol = new TableColumn("Name");
		nameCol.setMinWidth(300);
		nameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		TableColumn dateOfOrderCol = new TableColumn("Date of order");
		dateOfOrderCol.setMinWidth(120);
		dateOfOrderCol.setCellValueFactory(new PropertyValueFactory<Person, String>("dateOfOrder"));
		TableColumn statusCol = new TableColumn("Status");
		statusCol.setMinWidth(120);
		statusCol.setCellValueFactory(new PropertyValueFactory<Person, String>("serviceStatusValue"));
		TableColumn assignedPersonCol = new TableColumn("Assigned person");
		assignedPersonCol.setMinWidth(200);
		assignedPersonCol.setCellValueFactory(new PropertyValueFactory<Person, String>(
				"assignedPersonValue"));

		List<Service> query = getAllServiceRecords();
		observableArrayList = FXCollections.observableArrayList(query);
		table.setItems(observableArrayList);

		table.getColumns().addAll(idCol, personIdCol, nameCol, dateOfOrderCol, statusCol, assignedPersonCol);

		VBox box = new VBox();
		box.setSpacing(5);
		box.setPadding(new Insets(10, 10, 10, 10));
		box.getChildren().addAll(label, table);

		return box;
	}

	private List<Service> getAllServiceRecords() {
		return SqliteJdbcTemplate
				.getJdbcTemplate()
				.query(
						"select s.id, s.client_id, s.service_name, s.date_of_order, s.service_status_id, s.assigned_person_id, p.first_name || ' ' || p.last_name as ass_name from service s left join person p on s.assigned_person_id = p.id",
						new RowMapper<Service>() {
							public Service mapRow(ResultSet rs, int rowNum) throws SQLException {
								return Service.builder()
										.id(rs.getInt("id"))
										.clientId(rs.getInt("client_id"))
										.serviceName(rs.getString("service_name"))
										.dateOfOrder(LocalDate.parse(rs.getString("date_of_order")))
										.serviceStatusId(rs.getInt("service_status_id"))
										.serviceStatusValue(ServiceStatus.listOfStatus.get(rs.getInt("service_status_id")))
										.assignedPersonId(rs.getInt("assigned_person_id"))
										.assignedPersonValue(rs.getString("ass_name"))
										.build();
								
							}
						});
	}

	private Callback<TableView<Service>, TableRow<Service>> openServiceFormToEdit() {
		return tv -> {
			TableRow<Service> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					Service serwis = new ServiceForm().editService(row.getItem());
					if (serwis != null) {
						observableArrayList.remove(row.getItem());
						if (serwis.getName() != null)
							observableArrayList.add(serwis);
					}
					table.refresh();
				}
			});
			return row;
		};
	}
	
	private Pane createSearchPanel() {
		TextField searchByServiceId = new TextField();
		searchByServiceId.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
          if (!newValue.matches("\\d*")) {
          	searchByServiceId.setText(newValue.replaceAll("[^\\d]", ""));
          }
      }
		});
		Button searchBut = new Button("Search");
		searchBut.setOnAction(e -> {
			Optional<Service> optional = observableArrayList.stream().filter(s -> s.getId() == Integer.valueOf(searchByServiceId.getText()).intValue()).findFirst();
			if (optional.isPresent()) {
				Service serwis = new ServiceForm().editService(optional.get());
				if (serwis != null) {
					observableArrayList.remove(optional.get());
					observableArrayList.add(serwis);
				}
				table.refresh();
			}
			searchByServiceId.setText("");
		});
		searchBut.disableProperty().bind(searchByServiceId.textProperty().isEmpty());
		
		FlowPane panel = new FlowPane();
		panel.setPadding(new Insets(10));
		panel.getChildren().addAll(new Label("Service ID: "),searchByServiceId,searchBut);
		return panel;
	}
}
