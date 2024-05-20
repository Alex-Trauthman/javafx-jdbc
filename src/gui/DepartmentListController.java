package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {
	
	private DepartmentService depService;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	@FXML
	private Button ButtonNew;
	
	private ObservableList<Department> obsList;
	
	public void onButtonNewAction(ActionEvent event) {
		Department obj = new Department();
		createDialogForm(obj,"/gui/DepartmentForm.fxml", Utils.currentStage(event));
	}
	
	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}
	public void setDepService(DepartmentService service) {
		this.depService = service;
	}
	public void updateTableView() {
		if (depService == null) {
			throw new IllegalStateException("Department Service is null");
		}
		List<Department> list = depService.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rB) {
		initializeNodes();
		
	}
	private void createDialogForm(Department obj,String absoluteName,Stage parent) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName ));
			Pane pane = loader.load();
			Stage dialog = new Stage();
			
			DepartmentFormController controller = loader.getController();
			controller.setDep(obj);
			controller.updateFormData();
			controller.setDepartmentService(new DepartmentService());
			
			dialog.setTitle("Enter Department Data");
			dialog.setScene(new Scene(pane));
			dialog.setResizable(false);
			dialog.initOwner(parent);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IOException", "Error loading form", e.getMessage(), AlertType.ERROR);
		}
	}

}
