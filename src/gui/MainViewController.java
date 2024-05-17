package gui;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable{
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		System.out.println("you clicked seller");
	}
	@FXML
	public void onMenuItemDepartmenAction() {
		System.out.println("you clicked department");
	}
	@FXML
	public void onMenuItemAboutAction() {
		System.out.println("There's nothing special about it, Phil Anselmo (BEFORE 2013)");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rB) {
		// TODO Auto-generated method stub
		
	}
	
}
