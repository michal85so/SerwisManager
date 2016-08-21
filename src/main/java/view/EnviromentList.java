package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Dialog;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import org.springframework.jdbc.core.RowMapper;

import repository.SqliteJdbcTemplate;
import domain.Enviroment;
import domain.Person;

public class EnviromentList {
	private TableView<Enviroment> table;
	private ObservableList<Enviroment> observableArrayList;
	private List<Enviroment> query;

	public EnviromentList() {
	}

	public Enviroment showForm() {
		Dialog<Enviroment> dialog = new Dialog<>();
		dialog.setTitle("Enviroment list");

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
		Image enviromentIcon = new Image("icons/add_enviroment.png", 50, 50, true, true);
		Button addEnviromentBtn = new Button("Add Enviroment", new ImageView(enviromentIcon));
		addEnviromentBtn.setContentDisplay(ContentDisplay.TOP);
		addEnviromentBtn.setOnAction(openEnviromentFormToAdd());

		FlowPane panel = new FlowPane();
		panel.setHgap(50);
		panel.setPadding(new Insets(10));

		panel.getChildren().add(addEnviromentBtn);

		return panel;
	}

	private EventHandler<ActionEvent> openEnviromentFormToAdd() {
		return e -> {
			Enviroment newEnviroment = new EnviromentForm().createEnviroment();
			if (newEnviroment != null) {
				observableArrayList.add(newEnviroment);
				query.add(newEnviroment);
				table.setItems(observableArrayList);
			}
			table.refresh();
		};
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Pane createList() {
		table = new TableView<Enviroment>();
		table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setRowFactory(openServiceFormToEdit());

		TableColumn idCol = new TableColumn("Id");
		idCol.setMinWidth(50);
		idCol.setCellValueFactory(new PropertyValueFactory<Person, String>("id"));
		TableColumn categoryCol = new TableColumn("Category");
		categoryCol.setMinWidth(100);
		categoryCol.setCellValueFactory(new PropertyValueFactory<Person, String>("category"));
		TableColumn producentCol = new TableColumn("Producent");
		producentCol.setMinWidth(100);
		producentCol.setCellValueFactory(new PropertyValueFactory<Person, String>("producent"));
		TableColumn modelCol = new TableColumn("Model");
		modelCol.setMinWidth(100);
		modelCol.setCellValueFactory(new PropertyValueFactory<Person, String>("model"));
		TableColumn priceCol = new TableColumn("Price");
		priceCol.setMinWidth(100);
		priceCol.setCellValueFactory(new PropertyValueFactory<Person, Double>("price"));
		TableColumn itemsCol = new TableColumn("Items");
		itemsCol.setMinWidth(50);
		itemsCol.setCellValueFactory(new PropertyValueFactory<Person, Double>("items"));

		query = getAllItems();
		observableArrayList = FXCollections.observableArrayList(query);
		table.setItems(observableArrayList);

		table.getColumns().addAll(idCol, categoryCol, producentCol, modelCol, priceCol, itemsCol);

		VBox box = new VBox();
		box.setSpacing(5);
		box.setPadding(new Insets(10, 10, 10, 10));
		box.getChildren().addAll(table);

		return box;
	}

	private List<Enviroment> getAllItems() {
		return SqliteJdbcTemplate.getJdbcTemplate().query(
				"select id, category, producent, model, price, items from enviroment",
				new RowMapper<Enviroment>() {
					public Enviroment mapRow(ResultSet rs, int rowNum) throws SQLException {
						return Enviroment.builder().id(rs.getInt("id")).category(rs.getString("category"))
								.producent(rs.getString("producent")).model(rs.getString("model"))
								.price(rs.getDouble("price")).items(rs.getInt("items")).build();
					}
				});
	}

	private Callback<TableView<Enviroment>, TableRow<Enviroment>> openServiceFormToEdit() {
		return tv -> {
			TableRow<Enviroment> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					Enviroment enviroment = new EnviromentForm().editEnviroment(row.getItem());
					if (enviroment != null) {
						observableArrayList.remove(row.getItem());
						observableArrayList.add(enviroment);
						table.setItems(observableArrayList);
					}
					table.refresh();
				}
			});
			return row;
		};
	}
}
