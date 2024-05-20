package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private DepartmentService service;

	private Department dep;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private Label error;

	@FXML
	private Button saveBt;

	@FXML
	private Button cancelBt;

	@FXML
	public void onSaveBtAction(ActionEvent event) {
		try {
			dep = getFormData(event);
			if (dep == null) {
				throw new IllegalStateException(
						"Dep instance is null (Check DepartmentFormController or ListController)");
			}
			if (service == null) {
				throw new IllegalStateException(
						"Service instance is null (Check DepartmentFormController or ListController)");
			}
			service.saveOrUpdate(dep);
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error Saving Department", "Database exception", e.getMessage(), AlertType.ERROR);
		} catch (IllegalStateException e) {
            Alerts.showAlert("Error Saving Department", "Illegal State Exception", e.getMessage(), AlertType.ERROR);
        }
	}

	@FXML
	public void onCancelBtAction(ActionEvent event) {
		System.out.println("Cancel Test");
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rB) {
		initializeNodes();

	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 18);
	}

	public void updateFormData() {
		if (dep == null) {
			throw new IllegalStateException("Dep instance is null (Check DepartmentFormController or ListController)");
		}
		txtId.setText(String.valueOf(dep.getId()));
		txtName.setText(dep.getName());
	}

	public void setDep(Department dep) {
		this.dep = dep;
	}

	public void setDepartmentService(DepartmentService aux) {
		service = aux;
	}

	public Department getFormData(ActionEvent event) {
		Department obj = new Department();
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtName.getText());
		if(obj.getName() == null|| obj.getName() == " ") {
			
			Utils.currentStage(event).close();
			return null;
		}
		return obj;
	}

}
