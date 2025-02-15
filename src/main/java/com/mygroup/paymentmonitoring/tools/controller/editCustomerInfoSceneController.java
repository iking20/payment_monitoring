package com.mygroup.paymentmonitoring.tools.controller;

import java.util.ArrayList;
import java.util.List;

import com.mygroup.paymentmonitoring.tools.model.Customer;
import com.mygroup.paymentmonitoring.tools.queries.MyDao;
import com.mygroup.paymentmonitoring.tools.utils.Alerts;
import com.mygroup.paymentmonitoring.tools.utils.Validate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class editCustomerInfoSceneController {

    @FXML DatePicker bday;

    @FXML TextField cust_no_txt,fname_txt,lname_txt,mail_txt,mname_txt,phone_txt;

    @FXML
    private Button editInfoCancel;

    @FXML
    private Button editInfoSave;
    
    
    @FXML
    void doCancelEditInfo(ActionEvent event) {
    	Stage stage = (Stage) editInfoCancel.getScene().getWindow();
    	stage.close();

    }

    @FXML
    void doSaveInfo(ActionEvent event) {
    	Alert alert = Alerts.confirmation("Do you want to save this?", "Confirmation");
    	if(alert.getResult() == ButtonType.YES) {
    		Customer customerModel = new Customer(cust_no_txt.getText(), lname_txt.getText(), fname_txt.getText(), 
    				mname_txt.getText(), bday.getValue().toString(), phone_txt.getText(), mail_txt.getText(), "");
    		List<Customer>customerList = new ArrayList<>();
    		customerList.add(customerModel);
    		
    		boolean isEmpty = Validate.checkCustomerInfo(customerList);
    		
    		if(isEmpty) {
    			alert = Alerts.error("Check empty field(s)", "Error!!");
    		}else {
    			int resultSet = MyDao.updateCustomerInfo(mainSceneController.postgresConnStr,mainSceneController.schema_db,"customer",
    					customerList);
    			if(resultSet>0) {
    				alert = Alerts.information("Saved Successfully", "Information!!");
    				if(alert.getResult() == ButtonType.OK) {
    				   	Stage stage = (Stage) editInfoSave.getScene().getWindow();
    			    	stage.close();
    				}
    				
    			}else {
    				alert = Alerts.error("Something went wrong!!", "Error!!");
    			}
    		}
    	}
    }


}
