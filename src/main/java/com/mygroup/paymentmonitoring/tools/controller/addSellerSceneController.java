package com.mygroup.paymentmonitoring.tools.controller;

import java.util.ArrayList;
import java.util.List;

import com.mygroup.paymentmonitoring.tools.model.Lot;
import com.mygroup.paymentmonitoring.tools.model.Seller;
import com.mygroup.paymentmonitoring.tools.queries.MyDao;
import com.mygroup.paymentmonitoring.tools.utils.Alerts;
import com.mygroup.paymentmonitoring.tools.utils.Validate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addSellerSceneController {

    @FXML
    private Button cancelSellerBtn;

    @FXML
    private TextField selerMnameTxt;

    @FXML
    private TextField sellerFnameTxt;

    @FXML
    private TextField sellerLnameTxt;

    @FXML TextField sellerNoTxt;

    @FXML
    private Button submitSellerBtn;

    @FXML
    void doCancelSeller(ActionEvent event) {
    	
    	Stage stage = (Stage) cancelSellerBtn.getScene().getWindow();
    	stage.close();

    }

    @FXML
    void doSubmitSeller(ActionEvent event) {
    	
    	Seller sellerModel = new Seller(sellerNoTxt.getText(), sellerLnameTxt.getText(), 
    			sellerFnameTxt.getText(), selerMnameTxt.getText());
    	
    	List<Seller> sellerList = new ArrayList<>();
    	sellerList.add(sellerModel);
    	
    	boolean isEmptyField = Validate.checkSeller(sellerList);
    	if(isEmptyField) {
    		Alert alert = Alerts.error("Please fill empty Field(s)", "Error");
    	}else {
    		Alert alert = Alerts.confirmation("Do you want to submit this?", "Confirmation");
    		if(alert.getResult() == ButtonType.YES) {
    			String connStr = mainSceneController.postgresConnStr;
    			String schemaDb = mainSceneController.schema_db;
    			int rs = MyDao.addSeller(connStr, schemaDb, "seller", sellerList);
    			if(rs>0) {
    				Alert alert2 = Alerts.information("Seller added successfully!!", "Information");
    				if(alert2.getResult() == ButtonType.OK) {
    			    	Stage stage = (Stage) submitSellerBtn.getScene().getWindow();
    			    	stage.close();
    				}
    			}else {
    				Alert alert2 = Alerts.error("Something went wrong, check your data.", "Error");
    			}
    		}
    	}
    }

}
