package view;

import java.time.LocalDate;
import java.util.Optional;

import com.sun.istack.internal.Nullable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import repository.SqliteJdbcTemplate;
import domain.Person;
import domain.Service;
import domain.ServiceStatus;

public class ServiceForm {
	private TextField clientId;
	private TextField serviceName;
	private DatePicker dateOfOrder;
	private DatePicker dateOfReceipt;
	private TextArea info;
	private Integer assignedPersonId;
	private TextField assignedPerson;
	private ComboBox<String> status;
	private TextField id;
	private int maxId = 0;

	public Service createService() {
		Dialog<Service> dialog = new Dialog<>();
		dialog.setTitle("Add Service");
		dialog.setHeaderText("Provide new service data");
		dialog.setGraphic(new ImageView(new Image("icons/new_service.png")));
		Integer maxValue = SqliteJdbcTemplate.getJdbcTemplate().queryForObject("select max(id) from service", int.class);
		maxId = maxValue != null ? maxValue : 0;
		return createDialog(dialog, null);
	}
	
	public Service editService(Service service) {
		Dialog<Service> dialog = new Dialog<>();
		dialog.setTitle("Edit Service");
		dialog.setHeaderText("Change service data");
		dialog.setGraphic(new ImageView(new Image("icons/new_service.png")));
		return createDialog(dialog, service);
	}
	
	private void fillComponents(Service service) {
		id.setText(String.valueOf(service.getId()));
		clientId.setText(String.valueOf(service.getClientId()));
		serviceName.setText(service.getName());
		dateOfOrder.setValue(service.getDateOfOrder());
		assignedPersonId = service.getAssignedPersonId();
		assignedPerson.setText(String.valueOf(service.getAssignedPersonValue()));
		status.getSelectionModel().select(service.getServiceStatusValue());
		dateOfReceipt.setValue(service.getDateOfReceipt());
		info.setText(service.getInfo());
	}

	private Service createDialog(Dialog<Service> dialog, @Nullable Service editedService) {
		ButtonType buttonType = new ButtonType(editedService == null ? "Create" : "Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);

		GridPane panel = new GridPane();
		panel.setHgap(10);
		panel.setVgap(10);
		panel.setPadding(new Insets(20, 150, 10, 10));
		
		panel.add(new Label("ID: "), 0, 0);
		id = new TextField(String.valueOf(maxId + 1));
		id.setEditable(false);
		panel.add(id, 1, 0);

		panel.add(new Label("Client ID: "), 0, 1);
		clientId = new TextField();
		clientId.setEditable(false);
		clientId.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
          if (!newValue.matches("\\d*")) {
          	clientId.setText(newValue.replaceAll("[^\\d]", ""));
          }
      }
		});
		panel.add(clientId, 1, 1);
		Button personList = new Button("List");
		personList.setOnAction(e -> {
			Person person = new ClientList().showForm();
			if (person != null)
				clientId.setText(String.valueOf(person.getId()));
		});
		panel.add(personList, 2, 1);

		panel.add(new Label("Service name: "), 0, 2);
		serviceName = new TextField();
		panel.add(serviceName, 1, 2);
		
		panel.add(new Label("Date of order: "), 0, 3);
		dateOfOrder = new DatePicker(LocalDate.now());
		panel.add(dateOfOrder, 1, 3);
		
		panel.add(new Label("Assigned to: "), 0, 4);
		assignedPerson = new TextField();
		panel.add(assignedPerson, 1, 4);
		Button assignedList = new Button("List");
		assignedList.setOnAction(e -> {
			Person person = new ClientList().showForm();
			if (person != null) {
				assignedPersonId = person.getId();
				assignedPerson.setText(person.getFirstName() + " " + person.getLastName());
			}
		});
		panel.add(assignedList, 2, 4);
		
		panel.add(new Label("Status: "), 0, 5);
		status = new ComboBox<String>(FXCollections.observableArrayList(ServiceStatus.listOfStatus.values()));
		panel.add(status, 1, 5);
		status.getSelectionModel().selectFirst();;
		
		panel.add(new Label("Date of receipt: "), 0, 6);
		dateOfReceipt = new DatePicker();
		panel.add(dateOfReceipt, 1, 6);

		panel.add(new Label("Info: "), 0, 7);
		info = new TextArea();
		info.setPrefRowCount(4);
		info.setWrapText(true);
		panel.add(info, 1, 7);

		dialog.getDialogPane().setContent(panel);

		dialog.setResultConverter(btn -> {
			if (btn == buttonType) {
				return Service.builder()
						.id(!id.getText().isEmpty() ? Integer.valueOf(id.getText()) : 0)
						.clientId(Integer.parseInt(clientId.getText()))
						.serviceName(serviceName.getText())
						.dateOfOrder(dateOfOrder.getValue())
						.dateOfReceipt(dateOfReceipt.getValue())
						.info(info.getText())
						.serviceStatusId(status.getSelectionModel().getSelectedIndex() + 2)
						.assignedPersonId(assignedPersonId)
						.assignedPersonValue(assignedPerson.getText())
						.build();
			}
			return null;
		});
		
		if (editedService != null)
			fillComponents(editedService);

		Optional<Service> service = dialog.showAndWait();
		if (service.isPresent()) {
			if (service.get().getDateOfReceipt() != null) {
				SqliteJdbcTemplate.getJdbcTemplate().update("insert into service_history(client_id,service_name,info,date_of_order,date_of_receipt,department_id,assigned_person_id,facture_id) values(" 
								+ service.get().getClientId() + ", '" + service.get().getName() + "', '" + service.get().getInfo() + "', '" + service.get().getDateOfOrder() + "', '" 
								+ service.get().getDateOfReceipt() + "', " + service.get().getDepartment() + ", " + service.get().getAssignedPersonId() + ", " + service.get().getInvoiceId() + ")");
				SqliteJdbcTemplate.getJdbcTemplate().update("delete from service where id = " + service.get().getId());
				return new Service();
			}
			if ("Create".equals(buttonType.getText()))
				SqliteJdbcTemplate.getJdbcTemplate().update(
						"insert into service(id, client_id, service_name, info, date_of_order, date_of_receipt, service_status_id, assigned_person_id) values (" + service.get().getId() + ", " + service.get().getClientId()
								+ ", '" + service.get().getName() + "', '" + service.get().getInfo() + "', '" + service.get().getDateOfOrder() + "', '" 
								+ service.get().getDateOfReceipt() + "', " + service.get().getserviceStatusId() + ", " + service.get().getAssignedPersonId() + ")");
			else
				SqliteJdbcTemplate.getJdbcTemplate().update(
						"update service set client_id = " + service.get().getClientId() + ", service_name = '" + service.get().getName() + "', info = '"
						+ service.get().getInfo() + "', date_of_order = '" + service.get().getDateOfOrder() + "', date_of_receipt = '" + service.get().getDateOfReceipt()
						+ "', service_status_id = " + service.get().getserviceStatusId() + ", assigned_person_id = " + service.get().getAssignedPersonId() + " where id = " + service.get().getId());
			return service.get();
		}
		return null;
	}
}
