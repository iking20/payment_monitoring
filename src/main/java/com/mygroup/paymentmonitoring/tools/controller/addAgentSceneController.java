package com.mygroup.paymentmonitoring.tools.controller;

import java.util.ArrayList;
import java.util.List;

import com.mygroup.paymentmonitoring.tools.model.Agent;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class addAgentSceneController {

    @FXML
    private TextField agentFnameTxt;

    @FXML
    private TextField agentLnameTxt;

    @FXML
    private TextField agentMnameTxt;

    @FXML TextField agentNoTxt;

    @FXML
    private TextField agentPhoneNoTxt;

    @FXML
    private Button cancelAgentBtn;

    @FXML
    private Button submitAgentBtn;
    
    @FXML ComboBox<String> agentcategory;

    @FXML
    void doCancelAgent(ActionEvent event) {
    	
    	Stage stage = (Stage) cancelAgentBtn.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void doSubmitAgent(ActionEvent event) {
    	
    	Agent agentModel = new Agent(agentNoTxt.getText(), agentLnameTxt.getText(), 
    			agentFnameTxt.getText(), agentMnameTxt.getText(), agentPhoneNoTxt.getText(), agentcategory.getValue());
    	
    	List<Agent> agentList = new ArrayList<>();
    	agentList.add(agentModel);
    	
    	boolean isEmptyField = Validate.checkAgent(agentList);
    	if(isEmptyField) {
    		Alert alert = Alerts.error("Please fill empty Field(s)", "Error");
    	}else {
    		Alert alert = Alerts.confirmation("Do you want to submit this?", "Confirmation");
    		if(alert.getResult() == ButtonType.YES) {
    			String connStr = mainSceneController.postgresConnStr;
    			String schemaDb = mainSceneController.schema_db;
    			int rs = MyDao.addAgent(connStr, schemaDb, "agent", agentList);
    			if(rs>0) {
    				Alert alert2 = Alerts.information("Agent added successfully!!", "Information");
    				if(alert2.getResult() == ButtonType.OK) {
    			    	Stage stage = (Stage) submitAgentBtn.getScene().getWindow();
    			    	stage.close();
    				}
    			}else {
    				Alert alert2 = Alerts.error("Something went wrong, check your data.", "Error");
    			}
    		}
    	}
    }

    @FXML
    void filterInput(KeyEvent event) {
    	
    	if(!event.getCode().equals(KeyCode.BACK_SPACE) && !event.getCode().equals(KeyCode.DELETE)
    			&& !event.getCode().isDigitKey() && !event.getCode().isArrowKey()
    			&& !event.getCode().equals(KeyCode.ENTER) && !event.getCode().equals(KeyCode.TAB)) {
    		
    		Alert alert = Alerts.error("Invalid Input!!", "Error");
    		if(alert.getResult() == ButtonType.OK) {
    			agentPhoneNoTxt.clear();
    		}
    	}

    }

}
