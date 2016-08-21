package view;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import repository.SqliteJdbcTemplate;

import com.sun.istack.internal.Nullable;

import domain.Enviroment;

public class EnviromentForm {
	private TextField category;
	private TextField producent;
	private TextField model;
	private TextField price;
	private TextField items;
	private int maxId;

	public Enviroment createEnviroment() {
		Dialog<Enviroment> dialog = new Dialog<>();
		dialog.setTitle("Add Enviroment");
		dialog.setHeaderText("Provide new enviroment data");
		dialog.setGraphic(new ImageView(new Image("icons/add_enviroment.png")));
		maxId = SqliteJdbcTemplate.getJdbcTemplate().queryForObject("select max(id) from enviroment",
				int.class) + 1;
		return createDialog(dialog, null);
	}

	public Enviroment editEnviroment(Enviroment enviroment) {
		Dialog<Enviroment> dialog = new Dialog<>();
		dialog.setTitle("Edit Service");
		dialog.setHeaderText("Change service data");
		dialog.setGraphic(new ImageView(new Image("icons/new_service.png")));
		maxId = enviroment.getId();
		return createDialog(dialog, enviroment);
	}

	private void fillComponents(Enviroment enviroment) {
		category.setText(enviroment.getCategory());
		producent.setText(enviroment.getProducent());
		model.setText(enviroment.getModel());
		price.setText(String.valueOf(enviroment.getPrice()));
		items.setText(String.valueOf(enviroment.getItems()));
	}

	private Enviroment createDialog(Dialog<Enviroment> dialog, @Nullable Enviroment editedEnviroment) {
		ButtonType buttonType = new ButtonType(editedEnviroment == null ? "Create" : "Ok",
				ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);

		GridPane panel = new GridPane();
		panel.setHgap(10);
		panel.setVgap(10);
		panel.setPadding(new Insets(20, 150, 10, 10));

		panel.add(new Label("Category: "), 0, 0);
		category = new TextField();
		panel.add(category, 1, 0);

		panel.add(new Label("Producent: "), 0, 1);
		producent = new TextField();
		panel.add(producent, 1, 1);

		panel.add(new Label("Model: "), 0, 2);
		model = new TextField();
		panel.add(model, 1, 2);

		panel.add(new Label("Price: "), 0, 3);
		price = new TextField();
		panel.add(price, 1, 3);

		panel.add(new Label("Items: "), 0, 4);
		items = new TextField();
		panel.add(items, 1, 4);

		dialog.getDialogPane().setContent(panel);

		dialog
				.setResultConverter(btn -> {
					if (btn == buttonType) {
						return Enviroment.builder().id(maxId).category(category.getText())
								.producent(producent.getText()).model(model.getText())
								.price(Double.valueOf(price.getText())).items(Integer.valueOf(items.getText()))
								.build();
					}
					return null;
				});

		if (editedEnviroment != null)
			fillComponents(editedEnviroment);

		Optional<Enviroment> enviroment = dialog.showAndWait();
		if (enviroment.isPresent()) {
			if (editedEnviroment == null)
				SqliteJdbcTemplate.getJdbcTemplate().update(
						"insert into enviroment(id, category, producent, model, price, items) values (" + enviroment.get().getId() + ",'"
								+ enviroment.get().getCategory() + "', '" + enviroment.get().getProducent()
								+ "', '" + enviroment.get().getModel() + "', " + enviroment.get().getPrice() + ", "
								+ enviroment.get().getItems() + ")");
			else
				SqliteJdbcTemplate.getJdbcTemplate().update(
						"update enviroment set category = '" + enviroment.get().getCategory() + "', producent = '"
								+ enviroment.get().getProducent() + "', model = '" + enviroment.get().getModel()
								+ "', price = " + enviroment.get().getPrice() + ", items = "
								+ enviroment.get().getItems() + " where id = " + enviroment.get().getId());
			return enviroment.get();
		}
		return null;
	}
}
