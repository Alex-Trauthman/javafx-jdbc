package gui;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable, DataChangeListener {

	private SellerService sellService;

	@FXML
	private TableView<Seller> tableViewSeller;
	@FXML
	private TableColumn<Seller, Integer> tableColumnId;

	@FXML
	private TableColumn<Seller, String> tableColumnName;
	@FXML
	private Button ButtonNew;
	@FXML
	private TableColumn<Seller, String> tableColumnEmail;
	@FXML
	private TableColumn<Seller, Date> tableColumnBirthDate;
	@FXML
	private TableColumn<Seller, Double> tableColumnSalary;

	@FXML
	private TableColumn<Seller, Seller> tableColumnEDIT;

	private ObservableList<Seller> obsList;
	@FXML
	private TableColumn<Seller, Seller> tableColumnREMOVE;

//	private void initEditButtons() {
//		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
//		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
//			private final Button button = new Button("edit");
//
//			@Override
//			protected void updateItem(Seller obj, boolean empty) {
//				super.updateItem(obj, empty);
//				if (obj == null) {
//					setGraphic(null);
//					return;
//				}
//				setGraphic(button);
//				button.setOnAction(
//						event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
//			}
//		});
//	}

	public void onButtonNewAction(ActionEvent event) {
		Seller obj = new Seller();
//		createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event));
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		tableColumnSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
		Utils.formatTableColumnDouble(tableColumnSalary, 2);

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
	}

	public void setSellService(SellerService service) {
		this.sellService = service;
	}

	public void updateTableView() {
		if (sellService == null) {
			throw new IllegalStateException("Seller Service is null");
		}
		List<Seller> list = sellService.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewSeller.setItems(obsList);
//		initEditButtons();
		initRemoveButtons();
	}

	private void initRemoveButtons() {

		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}

			
		});
	}
	private void removeEntity(Seller obj) {
		
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		
		if(result.get() == ButtonType.OK) {
			if(sellService == null) {
				throw new IllegalStateException("Service was´nt intialized");
			}try {
				sellService.remove(obj);
				updateTableView();
			}catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing seller",null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
	@Override
	public void initialize(URL url, ResourceBundle rB) {
		initializeNodes();

	}

//	private void createDialogForm(Seller obj, String absoluteName, Stage parent) {
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//			Pane pane = loader.load();
//			Stage dialog = new Stage();
//
//			SellerFormController controller = loader.getController();
//			controller.setSell(obj);
//			controller.updateFormData();
//			controller.setSellerService(new SellerService());
//			controller.subscribeDataChangeListener(this);
//
//			dialog.setTitle("Enter Seller Data");
//			dialog.setScene(new Scene(pane));
//			dialog.setResizable(false);
//			dialog.initOwner(parent);
//			dialog.initModality(Modality.WINDOW_MODAL);
//			dialog.showAndWait();
//		} catch (IOException e) {
//			Alerts.showAlert("IOException", "Error loading form", e.getMessage(), AlertType.ERROR);
//		}
//	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

}
