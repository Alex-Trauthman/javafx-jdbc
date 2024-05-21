package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable {

	private List<DataChangeListener> listeners = new ArrayList<DataChangeListener>();

	private SellerService service;

	private Seller sell;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtEmail;

	@FXML
	private DatePicker dPBirthDate;

	@FXML
	private TextField txtSalary;

	@FXML
	private Label errorName;
	@FXML
	private Label errorEmail;
	@FXML
	private Label errorBirthDate;
	@FXML
	private Label errorSalary;

	@FXML
	private Button saveBt;

	@FXML
	private Button cancelBt;

	@FXML
	public void onSaveBtAction(ActionEvent event) {
		try {
			sell = getFormData();
			if (sell == null) {
				throw new IllegalStateException("Sell instance is null (Check SellerFormController or ListController)");
			}
			if (service == null) {
				throw new IllegalStateException(
						"Service instance is null (Check SellerFormController or ListController)");
			}
			service.saveOrUpdate(sell);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error Saving Seller", "Database exception", e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener dataChangeListener : listeners) {
			dataChangeListener.onDataChanged();
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
		Constraints.setTextFieldMaxLength(txtName, 50);
		Constraints.setTextFieldMaxLength(txtEmail, 40);
		Constraints.setTextFieldDouble(txtSalary);
		Utils.formatDatePicker(dPBirthDate, "dd/MM/yyyy");
	}

	public void updateFormData() {
		if (sell == null) {
			throw new IllegalStateException("Sell instance is null (Check SellerFormController or ListController)");
		}
		txtId.setText(String.valueOf(sell.getId()));
		txtName.setText(sell.getName());
		txtEmail.setText(sell.getEmail());
		Locale.setDefault(Locale.US);
		txtSalary.setText(String.format("%.2f", sell.getBaseSalary()));
		if(sell.getBirthDate() != null) {
			dPBirthDate.setValue(LocalDate.ofInstant(sell.getBirthDate().toInstant(), ZoneId.systemDefault()));
		}
	}

	public void setSell(Seller sell) {
		this.sell = sell;
	}

	public void setSellerService(SellerService aux) {
		service = aux;
	}

	public Seller getFormData() {
		Seller obj = new Seller();
		ValidationException error = new ValidationException("Error validating data");
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		if (txtName.getText() == null || txtName.getText().isBlank()) {
			error.addError("name", "Data must be informed");
		}
		obj.setName(txtName.getText());
		if (error.getErrors().size() > 0) {
			throw error;
		}
		return obj;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		listeners.add(listener);
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("name")) {
			errorName.setText(errors.get("name"));
		}
	}
}
