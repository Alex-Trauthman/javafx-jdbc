package gui;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;
import model.services.SellerService;

public class MainViewController implements Initializable{
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		loadView("/gui/SellerList.fxml",(SellerListController controller) -> {
			controller.setSellService(new SellerService());
			controller.updateTableView();
		})
		
		;
	}
	@FXML
	public void onMenuItemDepartmenAction() {
		loadView("/gui/DepartmentList.fxml",(DepartmentListController controller) -> {
			controller.setDepService(new DepartmentService());
			controller.updateTableView();
		})
		
		;
		
	}
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x->{});
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rB) {
		// TODO Auto-generated method stub
		
	}
	private synchronized <T> void loadView(String name, Consumer<T> initializeAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
			VBox vBox = loader.load();
			Scene mainScene = Main.getMainScene();
			VBox mainVBox =(VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(vBox.getChildren());
			
			T controller = loader.getController();
			initializeAction.accept(controller);
		} catch (IOException e) {
			Alerts.showAlert("IOException", "Error loading this view", e.getMessage(), AlertType.ERROR);
		}
	}
	
}
