package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable{
	
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
	public void onSaveBtAction() {
		System.out.println("Save Test");
	}
	
	@FXML
	public void onCancelBtAction() {
		System.out.println("Cancel Test");
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
			throw new IllegalStateException("Dep instance is null (Check DepartmentFormController)");
		}
		txtId.setText(String.valueOf(dep.getId()));
		txtName.setText(dep.getName());
	}
	
	
	public void setDep(Department dep) {
		this.dep = dep;
	}

}
