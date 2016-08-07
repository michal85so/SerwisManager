package view;

import java.time.LocalDate;
import java.util.Optional;

import repository.SqliteJdbcTemplate;
import controls.DateTimeFactory;
import controls.TextfieldFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import domain.Person;
import domain.Service;

public class ServiceForm {
	public Service createService() {
		Dialog<Service> dialog = new Dialog<>();
		dialog.setTitle("Add Service");
		dialog.setHeaderText("Provide new service data");
		dialog.setGraphic(new ImageView(new Image("icons/new_service.png")));
		return createDialog(dialog);
	}

	private Service createDialog(Dialog<Service> dialog) {
		ButtonType buttonType = new ButtonType("Create", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);

		GridPane panel = new GridPane();
		panel.setHgap(10);
		panel.setVgap(10);
		panel.setPadding(new Insets(20, 150, 10, 10));

		panel.add(new Label("Client ID: "), 0, 0);
		TextField clientId = new TextField();
		clientId.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
          if (!newValue.matches("\\d*")) {
          	clientId.setText(newValue.replaceAll("[^\\d]", ""));
          }
      }
		});
		panel.add(clientId, 1, 0);

		panel.add(new Label("Service name: "), 0, 1);
		TextField serviceName = new TextField();
		panel.add(serviceName, 1, 1);
		
		panel.add(new Label("Date of order: "), 0, 2);
		DatePicker dateOfOrder = new DatePicker(LocalDate.now());
		panel.add(dateOfOrder, 1, 2);
		
		panel.add(new Label("Date of receipt: "), 0, 3);
		DatePicker dateOfReceipt = new DatePicker(LocalDate.now());
		panel.add(dateOfReceipt, 1, 3);

		panel.add(new Label("Info: "), 0, 4);
		TextArea info = new TextArea();
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
						.build();
			}
			return null;
		});

		Optional<Service> service = dialog.showAndWait();
		if (service.isPresent()) {
			SqliteJdbcTemplate.getJdbcTemplate().update(
					"insert into service(client_id, service_name, info, date_of_order, date_of_receipt) values (" + service.get().getId()
							+ ", '" + service.get().getName() + "', '" + service.get().getInfo() + "', '" + service.get().getDateOfOrder() + "', '" 
							+ service.get().getDateOfReceipt() + "')");
			return service.get();
		}
		return null;
	}
}
