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
import domain.Enviroment;

public class EnviromentForm {
	private TextField category;
	private TextField producent;
	private TextField model;
	private TextField price;
	private TextField items;

	public Enviroment showForm() {
		Dialog<Enviroment> dialog = new Dialog<>();
		dialog.setTitle("Add Enviroment");
		dialog.setHeaderText("Provide new enviroment data");
		dialog.setGraphic(new ImageView(new Image("icons/add_enviroment.png")));
		return createDialog(dialog);
	}

	private Enviroment createDialog(Dialog<domain.Enviroment> dialog) {
		ButtonType buttonType = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);

		GridPane panel = new GridPane();
		panel.setHgap(10);
		panel.setVgap(10);
		panel.setPadding(new Insets(20, 150, 10, 10));

		panel.add(new Label("Category: "), 0, 0);
		category = new TextField();
		category.setEditable(false);
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

		panel.add(new Label("Items: "), 0, 3);
		items = new TextField();
		panel.add(items, 1, 4);

		dialog.getDialogPane().setContent(panel);

		dialog.setResultConverter(btn -> {
			if (btn == buttonType) {
				return Enviroment.builder().category(category.getText()).producent(producent.getText())
						.model(model.getText()).price(Double.valueOf(price.getText()))
						.items(Integer.valueOf(items.getText())).build();
			}
			return null;
		});

		Optional<Enviroment> enviroment = dialog.showAndWait();
		if (enviroment.isPresent()) {
			SqliteJdbcTemplate.getJdbcTemplate().update(
					"insert into enviroment(category, producent, model, price, items) values ('"
							+ enviroment.get().getCategory() + "', '" + enviroment.get().getProducent() + "', '"
							+ enviroment.get().getModel() + "', " + enviroment.get().getPrice() + ", "
							+ enviroment.get().getItems() + ")");
		}
		return null;
	}
}
