package view;

import java.time.LocalDate;
import java.util.Optional;

import com.sun.istack.internal.Nullable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
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

public class ServiceForm {
	private TextField clientId;
	private TextField serviceName;
	private DatePicker dateOfOrder;
	private DatePicker dateOfReceipt;
	private TextArea info;

	public Service createService() {
		Dialog<Service> dialog = new Dialog<>();
		dialog.setTitle("Add Service");
		dialog.setHeaderText("Provide new service data");
		dialog.setGraphic(new ImageView(new Image("icons/new_service.png")));
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
		clientId.setText(String.valueOf(service.getClientId()));
		serviceName.setText(service.getName());
		dateOfOrder.setValue(service.getDateOfOrder());
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

		panel.add(new Label("Client ID: "), 0, 0);
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
		panel.add(clientId, 1, 0);
		Button personList = new Button("List");
		personList.setOnAction(e -> {
			Person person = new ClientList().showForm();
			if (person != null)
				clientId.setText(String.valueOf(person.getId()));
		});
		panel.add(personList, 2, 0);

		panel.add(new Label("Service name: "), 0, 1);
		serviceName = new TextField();
		panel.add(serviceName, 1, 1);
		
		panel.add(new Label("Date of order: "), 0, 2);
		dateOfOrder = new DatePicker(LocalDate.now());
		panel.add(dateOfOrder, 1, 2);
		
		panel.add(new Label("Date of receipt: "), 0, 3);
		dateOfReceipt = new DatePicker();
		panel.add(dateOfReceipt, 1, 3);

		panel.add(new Label("Info: "), 0, 4);
		info = new TextArea();
		info.setPrefRowCount(4);
		info.setWrapText(true);
		panel.add(info, 1, 4);

		dialog.getDialogPane().setContent(panel);

		dialog.setResultConverter(btn -> {
			if (btn == buttonType) {
				return Service.builder()
						.clientId(Integer.parseInt(clientId.getText()))
						.serviceName(serviceName.getText())
						.dateOfOrder(dateOfOrder.getValue())
						.dateOfReceipt(dateOfReceipt.getValue())
						.info(info.getText())
						.serviceStatusId(1)
						.build();
			}
			return null;
		});
		
		if (editedService != null)
			fillComponents(editedService);

		Optional<Service> service = dialog.showAndWait();
		if (service.isPresent()) {
			if ("Create".equals(buttonType.getText()))
				SqliteJdbcTemplate.getJdbcTemplate().update(
						"insert into service(client_id, service_name, info, date_of_order, date_of_receipt, service_status_id) values (" + service.get().getClientId()
								+ ", '" + service.get().getName() + "', '" + service.get().getInfo() + "', '" + service.get().getDateOfOrder() + "', '" 
								+ service.get().getDateOfReceipt() + "', " + service.get().getserviceStatusId() + ")");
			else
				SqliteJdbcTemplate.getJdbcTemplate().update(
						"update service set client_id = " + service.get().getClientId() + ", service_name = '" + service.get().getName() + "', info = '"
						+ service.get().getInfo() + "', date_of_order = '" + service.get().getDateOfOrder() + "', date_of_receipt = '" + service.get().getDateOfOrder()
						+ "', service_status_id = " + service.get().getserviceStatusId() + " where id = " + service.get().getId());
			return service.get();
		}
		return null;
	}
}
