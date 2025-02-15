package com.mygroup.paymentmonitoring.tools.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mygroup.paymentmonitoring.tools.model.ActualAgentCommission;
import com.mygroup.paymentmonitoring.tools.model.Agent;
import com.mygroup.paymentmonitoring.tools.model.Expenses;
import com.mygroup.paymentmonitoring.tools.queries.MyDao;
import com.mygroup.paymentmonitoring.tools.utils.Alerts;
import com.mygroup.paymentmonitoring.tools.utils.Validate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class agentCommissionSceneController {

    @FXML
    private TextField AgentName;

    @FXML
    private Button CommCancel;

    @FXML
    private Button CommSave;

    @FXML
    private TextField agentCommAmount;

    @FXML DatePicker agentCommDate;

    @FXML
    private TextArea agentCommDescription;

    @FXML TextField agentCommNo;

    @FXML
    private TextField agentNo;

    @FXML
    private Button searchAgentBtn;
    
    

    @FXML
    void doCancelComm(ActionEvent event) {
    	Stage stage = (Stage) CommCancel.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void doSaveComm(ActionEvent event) {
    	ActualAgentCommission agentCommModel = new ActualAgentCommission(agentCommNo.getText(), agentCommDate.getValue().toString(), agentNo.getText(), 
    			AgentName.getText(), agentCommDescription.getText(), agentCommAmount.getText());
    	
    	List<ActualAgentCommission> commList = new ArrayList<>();
    	commList.add(agentCommModel);
    	boolean isEmptyField = Validate.checkCommission(commList);
    	if(isEmptyField) {
    		Alert alert = Alerts.error("Please fill empty Field(s)", "Error");
    	}else {
    		Alert alert = Alerts.confirmation("Do you want to submit this?", "Confirmation");
    		if(alert.getResult() == ButtonType.YES) {
    			String connStr = mainSceneController.postgresConnStr;
    			String schemaDb = mainSceneController.schema_db;
    			int rs = MyDao.addActualAgentCommission(connStr, schemaDb, "actual_agentcommission", commList);
    			if(rs>0) {
    				Alert alert2 = Alerts.information("Commission added successfully!!", "Information");
    				if(alert2.getResult() == ButtonType.OK) {
    			    	Stage stage = (Stage) CommSave.getScene().getWindow();
    			    	stage.close();
    				}
    			}
    		}
    	}
    }

    @FXML
    void doSearchAgent(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/searchSellerAgentScene.fxml"));
    	DialogPane customerDialogPane = loader.load();
    	searchSellerAgentSceneController<Agent> agentController = loader.getController();
    	
    	ObservableList<Agent> list = FXCollections.observableArrayList();
    	String searchtxt = AgentName.getText();
    	list = MyDao.searchAgentList(searchtxt,mainSceneController.postgresConnStr,mainSceneController.schema_db,"Agent");
    	mainSceneController.loadAgentTbl(list,agentController);
    	
       	Dialog<ButtonType> dialog = new Dialog<>();
    	dialog.setDialogPane(customerDialogPane);
    	dialog.setTitle("Select Agent");
    	
    	Optional<ButtonType> clickButton = dialog.showAndWait();
    	if(clickButton.get() == ButtonType.OK) {
    		agentNo.setText(agentController.selectedItem.get(0).getAgentNumber());
    		String agentName = agentController.selectedItem.get(0).getAgentLastName()+", "+agentController.selectedItem.get(0).getAgentFirstName()+
    				" "+agentController.selectedItem.get(0).getAgentMiddleName();
    		AgentName.setText(agentName);
    	}
    }

    @FXML
    void validateInput(KeyEvent event) {
    	
    	filterInput(event, agentCommAmount);

    }

	private void filterInput(KeyEvent event, TextField txtfield) {
		// TODO Auto-generated method stub
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
