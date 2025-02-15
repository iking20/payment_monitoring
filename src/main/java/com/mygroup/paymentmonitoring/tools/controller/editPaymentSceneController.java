package com.mygroup.paymentmonitoring.tools.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.mygroup.paymentmonitoring.tools.model.CustomerPayment;
import com.mygroup.paymentmonitoring.tools.queries.MyDao;
import com.mygroup.paymentmonitoring.tools.utils.Alerts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class editPaymentSceneController {

    @FXML
    private Button editPaymentCancelBtn;

    @FXML
    private Button editPaymentSaveBtn;

    @FXML TextField editPaymentCustID;

    @FXML TextField editPaymentAmount;

    @FXML TextField editPaymentDate;
    
    @FXML Label TCPamount,oldAmount;

    @FXML
    void doCancelpayment(ActionEvent event) {
    	Stage stage = (Stage) editPaymentCancelBtn.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void doEditPayment(ActionEvent event) {
    	int reseultSet=0;
    	if(editPaymentAmount.getText()!= "") {
    		int leftoverdue = MyDao.getLeftOverAmountDue(mainSceneController.postgresConnStr,mainSceneController.schema_db,"leftover",
    				editPaymentCustID.getText());
    		int OldAmount = Integer.parseInt(oldAmount.getText());
    		int amount = Integer.parseInt(editPaymentAmount.getText());
    		int leftoverRemaining = 0;
    		
    		if(OldAmount>amount) {
    			leftoverRemaining = OldAmount-amount;
    		}else {
    			leftoverRemaining = amount-OldAmount;
    		}
    		
    		reseultSet = MyDao.editPayment(mainSceneController.postgresConnStr,mainSceneController.schema_db,"payments",
    				editPaymentCustID.getText(),editPaymentDate.getText(),editPaymentAmount.getText());
    		
    		if(leftoverdue!=0) {
    			leftoverdue -= leftoverRemaining;
    			String contID = editPaymentCustID.getText().replace("cust","cont");
            	CustomerPayment leftOverModel = new CustomerPayment(editPaymentCustID.getText(), contID, 
            			Integer.toString(leftoverdue), editPaymentDate.getText());
            	
            	List<CustomerPayment>leftoverList = new ArrayList<>();
            	leftoverList.add(leftOverModel);
    			MyDao.addPayment(leftoverList,mainSceneController.postgresConnStr,mainSceneController.schema_db,"leftover");
    		}
    		
    		if(reseultSet>0) {
    			//this is for computation of total payment
    			int totalPayment = MyDao.computeCommision(mainSceneController.postgresConnStr,mainSceneController.schema_db,"payments",
    					"amount",editPaymentCustID.getText());
    			//System.out.println("totalpayment: "+totalPayment);
    			int balance = Integer.parseInt(TCPamount.getText().replace(",", "")) - totalPayment;
    			DecimalFormat formatter = new DecimalFormat("#,###");
    			MyDao.editContract(mainSceneController.postgresConnStr,mainSceneController.schema_db,"contract",
    					editPaymentCustID.getText(),formatter.format(balance),totalPayment);
    			Alert alert = Alerts.information("Payment edited successfully!!", "Information");
    			if(alert.getResult() == ButtonType.OK) {
    		    	Stage stage = (Stage) editPaymentSaveBtn.getScene().getWindow();
    		    	stage.close();
    			}
    		}
    		
    	}else {
    		Alert alert = Alerts.error("Fill empty field", "Error");
    	}
    }

    @FXML
    void validateInput(KeyEvent event) {
    	
    	filterInput(event, editPaymentAmount);
    }
    
    private void filterInput(KeyEvent event, TextField txtfield) {
    	
    	if(!event.getCode().equals(KeyCode.BACK_SPACE) && !event.getCode().equals(KeyCode.DELETE)
    			&& !event.getCode().isDigitKey() && !event.getCode().isArrowKey()
    			&& !event.getCode().equals(KeyCode.ENTER) && !event.getCode().equals(KeyCode.TAB)
    			&& !event.getCode().equals(KeyCode.PERIOD) && !event.getCode().equals(KeyCode.SHIFT)) {
    		
    		Alert alert = Alerts.error("Invalid Input!!", "Error");
    		if(alert.getResult() == ButtonType.OK) {
    			txtfield.clear();
    		}
    	}
    }

}
