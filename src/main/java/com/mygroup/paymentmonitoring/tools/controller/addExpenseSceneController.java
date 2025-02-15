package com.mygroup.paymentmonitoring.tools.controller;

import java.util.ArrayList;
import java.util.List;

import com.mygroup.paymentmonitoring.tools.model.Expenses;
import com.mygroup.paymentmonitoring.tools.model.Seller;
import com.mygroup.paymentmonitoring.tools.queries.MyDao;
import com.mygroup.paymentmonitoring.tools.utils.Alerts;
import com.mygroup.paymentmonitoring.tools.utils.Validate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class addExpenseSceneController {

    @FXML
    private TextField expenseAmount;

    @FXML
    private Button expenseCancel;

    @FXML ComboBox<String> expenseCategory;

    @FXML
    private TextArea expenseDescription;

    @FXML TextField expenseNo;

    @FXML
    private Button expenseSave;

    @FXML
    private TextField expenseTitle;

    @FXML DatePicker expensedate;

    @FXML
    void doCancelExpense(ActionEvent event) {
    	Stage stage = (Stage) expenseCancel.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void doSaveExpense(ActionEvent event) {
    	
    	Expenses expenseModel = new Expenses(expenseNo.getText(), expensedate.getValue().toString(), expenseCategory.getValue(), 
    			expenseTitle.getText(), expenseDescription.getText(), expenseAmount.getText());
    	
    	List<Expenses> expenseList = new ArrayList<>();
    	expenseList.add(expenseModel);
    	boolean isEmptyField = Validate.checkExpense(expenseList);
    	if(isEmptyField) {
    		Alert alert = Alerts.error("Please fill empty Field(s)", "Error");
    	}else {
    		Alert alert = Alerts.confirmation("Do you want to submit this?", "Confirmation");
    		if(alert.getResult() == ButtonType.YES) {
    			String connStr = mainSceneController.postgresConnStr;
    			String schemaDb = mainSceneController.schema_db;
    			int rs = MyDao.addExpense(connStr, schemaDb, "expenses", expenseList);
    			if(rs>0) {
    				Alert alert2 = Alerts.information("Expense added successfully!!", "Information");
    				if(alert2.getResult() == ButtonType.OK) {
    			    	Stage stage = (Stage) expenseSave.getScene().getWindow();
    			    	stage.close();
    				}
    			}
    		}
    	}
    }
    
    @FXML
    void validateInput(KeyEvent event) {
    	
    	filterInput(event, expenseAmount);
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
