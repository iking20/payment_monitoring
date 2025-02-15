package com.mygroup.paymentmonitoring.tools.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import com.mygroup.paymentmonitoring.tools.model.AgentCommissionSummary;
import com.mygroup.paymentmonitoring.tools.model.AgentCommissionbyClient;
import com.mygroup.paymentmonitoring.tools.queries.MyDao;
import com.mygroup.paymentmonitoring.tools.utils.Alerts;
import com.mygroup.paymentmonitoring.tools.utils.WriteToExcel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SummaryAgentCommisionSceneController {

    @FXML
    private TableColumn<AgentCommissionSummary, String> AgentColName,AgentIDCol,CommAmountCol;

    @FXML
    private TableView<AgentCommissionSummary> AgentCommTbl;
    
    @FXML
    private TableColumn<AgentCommissionbyClient, String> ClientNameCol,ClientNoCol,ClientPaymentCol,SellerCommCol,AgentCommCol;

    @FXML
    private TableView<AgentCommissionbyClient> ClientTbl;


    @FXML
    private Button cancelBtn,cancelBtn1;

    @FXML
    private Button extractBtn,clearData,viewData,viewData2,clearData2;
    
    @FXML ComboBox<String> monthCombo,agentCombo,monthCombo2;
    
    @FXML TabPane myTabs;
    
    @FXML
    private TextField yearField,yearField2;
    
    @FXML
    private Label totalPayment,totalSeller,totalAgent;
    
    ObservableList<AgentCommissionSummary> agentCommSummary = FXCollections.observableArrayList();
    ObservableList<AgentCommissionbyClient> agentCommClient = FXCollections.observableArrayList();

    @FXML
    void selectIDNumber(MouseEvent event) {

    }
    
    @FXML
    void viewSummaryComm(ActionEvent event) {
    	
    	if(monthCombo.getValue() == null || yearField.getText() == "") {
    		Alert alert = Alerts.error("Need to Select Month & \nEnter the Year!!", "Error!!");
    	}else {
    		
        	agentCommSummary =  MyDao.getAgentListSummary(mainSceneController.postgresConnStr, 
        			mainSceneController.schema_db, "agent");
        	
        	String selectedMonth = monthCombo.getValue().substring(0,2);
        	String startDateStr  = selectedMonth+"/01/"+yearField.getText();
        	LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        	LocalDate endtDate = startDate.withDayOfMonth(
        			startDate.getMonth().length(startDate.isLeapYear()));
        	
        	for(AgentCommissionSummary myAgent : agentCommSummary) {
        		String commAmount = MyDao.doComputeCommissionSummary(mainSceneController.postgresConnStr,mainSceneController.schema_db,
        				"agentcommission",myAgent.getAgentNo(),startDate,endtDate);
        		myAgent.setAgentCommAmount(commAmount);
        		
        	}
        	
    	}
    	loadAgentSummaryTable();
    
    }
    
	private void loadAgentSummaryTable() {
		
		if(!agentCommSummary.isEmpty()) {
			AgentIDCol.setCellValueFactory(new PropertyValueFactory<AgentCommissionSummary,String>("agentNo"));
			AgentColName.setCellValueFactory(new PropertyValueFactory<AgentCommissionSummary,String>("agentName"));
			CommAmountCol.setCellValueFactory(new PropertyValueFactory<AgentCommissionSummary,String>("agentCommAmount"));
			AgentCommTbl.getItems().clear();
			AgentCommTbl.setItems(agentCommSummary);
		}else {
			AgentCommTbl.getItems().clear();
		}
	}
	
    @FXML
    void closeWindow(ActionEvent event) {
    	Stage stage = (Stage) cancelBtn.getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    void ClearData2(ActionEvent event) {
    	ClientTbl.getItems().clear();
    	agentCommClient.clear();
    	monthCombo2.getSelectionModel().clearSelection();
    	monthCombo2.getEditor().clear();
    	monthCombo2.setPromptText("Select Month");
    	yearField2.clear();
    	yearField2.setPromptText("Enter Year");
    	agentCombo.getSelectionModel().clearSelection();
    	agentCombo.getEditor().clear();
    	agentCombo.setPromptText("Select an Agent");
    	totalPayment.setText("");
    	totalSeller.setText("");
    	totalAgent.setText("");
    }
    
    @FXML
    void ClearData(ActionEvent event) {
    	AgentCommTbl.getItems().clear();
    	agentCommSummary.clear();
    	monthCombo.getSelectionModel().clearSelection();
    	monthCombo.getEditor().clear();
    	monthCombo.setPromptText("Select Month");
    	yearField.clear();
    	yearField.setPromptText("Enter Year");
    }
    
    @FXML
    void doStopfromTyping(KeyEvent event) {
    	
    	if(event.getCode().isKeypadKey() || event.getCode().isLetterKey() || event.getCode().isKeypadKey()) {
    		monthCombo.getEditor().clear();
    	}

    }
    
    @FXML
    void DoSelectTab(MouseEvent event) {
    	
    	if(myTabs.getSelectionModel().isSelected(1)) {
    		ObservableList<String> agentNames = MyDao.getAgentNameList(mainSceneController.postgresConnStr, 
        			mainSceneController.schema_db, "agent");
    		agentCombo.setItems(agentNames);
    	}

    }
    
    @FXML
    void viewSummaryClient(ActionEvent event) {
    	
    	int totalPaymentAmount=0, totalSellComm=0, totalAgentComm=0;
    	if(monthCombo2.getValue() == null || yearField2.getText() == "" || agentCombo.getValue() == null) {
    		Alert alert = Alerts.error("Please choose the Agent \nNeed to Select Month & \nEnter the Year!!", "Error!!");
    	}else {
    		
        	String selectedMonth = monthCombo2.getValue().substring(0,2);
        	String startDateStr  = selectedMonth+"/01/"+yearField2.getText();
        	LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        	LocalDate endtDate = startDate.withDayOfMonth(
        			startDate.getMonth().length(startDate.isLeapYear()));
        	
        	String[]myAgent = agentCombo.getValue().split(":");
        	String  agentNo = myAgent[0];
        	
        	
    		agentCommClient =  MyDao.getClientPaymentSummary(mainSceneController.postgresConnStr, 
        			mainSceneController.schema_db, "payments", "sellercommission", 
        			"agentcommission",agentNo,startDate,endtDate);
    		
        	for(AgentCommissionbyClient myClient : agentCommClient) {
        		String clientName = MyDao.getClientName(mainSceneController.postgresConnStr,mainSceneController.schema_db,
        				"customer",myClient.getClientNo());
        		myClient.setClientName(clientName);
        		totalPaymentAmount += Integer.parseInt(myClient.getClientPayment());
        		totalSellComm += Integer.parseInt(myClient.getSellerCommission());
        		totalAgentComm += Integer.parseInt(myClient.getAgentCommission());
        	}
        	
    	}
    	loadClientSummaryTable();
    	DecimalFormat df = new DecimalFormat("#,###");
    	totalPayment.setText(df.format(totalPaymentAmount));
    	totalSeller.setText(df.format(totalSellComm));
    	totalAgent.setText(df.format(totalAgentComm));
    	    
    }

	private void loadClientSummaryTable() {
		if(!agentCommClient.isEmpty()) {
			ClientNoCol.setCellValueFactory(new PropertyValueFactory<AgentCommissionbyClient,String>("clientNo"));
			ClientNameCol.setCellValueFactory(new PropertyValueFactory<AgentCommissionbyClient,String>("clientName"));
			ClientPaymentCol.setCellValueFactory(new PropertyValueFactory<AgentCommissionbyClient,String>("clientPayment"));
			SellerCommCol.setCellValueFactory(new PropertyValueFactory<AgentCommissionbyClient,String>("sellerCommission"));
			AgentCommCol.setCellValueFactory(new PropertyValueFactory<AgentCommissionbyClient,String>("agentCommission"));
			ClientTbl.getItems().clear();
			ClientTbl.setItems(agentCommClient);
		}else {
			ClientTbl.getItems().clear();
		}
		
	}
	
    @FXML
    void DoExtractData(ActionEvent event) {
    	
    	if(AgentCommTbl.getItems().isEmpty()) {
    		Alert alert = Alerts.error("No DATA to Extract!!", "Error!!");
    	}else {
    		
       	 	String currentDir = System.getProperty("user.dir");
       	 	String templateStr = ""; String outputStr = "";
       	 	//System.out.println("Current dir using System:" + currentDir);
       	 	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
       	 	String[] selectedMonth = monthCombo.getValue().split(" ");
       	 	String dateRange = selectedMonth[1] + " " + yearField.getText();
       	 
       	 	if(mainSceneController.schema_db == "balanga" || mainSceneController.schema_db.equals("balanga")) {
       	 		templateStr = currentDir + "\\template\\AgentCommBalanga.xlsx";
       	 		outputStr = currentDir + "\\report\\"+timeStamp+"_AgentCommission_Balanga.xlsx";
       	 	}else {
       	 		templateStr = currentDir + "\\template\\AgentCommAbucay.xlsx";
       	 		outputStr = currentDir + "\\report\\"+timeStamp+"_AgentCommission_Abucay.xlsx";
       	 	}
       	 	
       	 WriteToExcel.writeCommissionSummary(AgentCommTbl.getItems(),templateStr,outputStr,dateRange);
    	}

    }
    
    @FXML
    void DoExtractClientData(ActionEvent event) {
    	
    	if(ClientTbl.getItems().isEmpty()) {
    		Alert alert = Alerts.error("No DATA to Extract!!", "Error!!");
    	}else {
    		
       	 	String currentDir = System.getProperty("user.dir");
       	 	String templateStr = ""; String outputStr = "";
       	 	//System.out.println("Current dir using System:" + currentDir);
       	 	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
       	 	String[] selectedMonth = monthCombo2.getValue().split(" ");
       	 	String[] selectedAgent = agentCombo.getValue().split(": ");
       	 	String dateRange = selectedMonth[1] + " " + yearField2.getText();
       	 	String agentName = selectedAgent[1];
       	 
       	 	if(mainSceneController.schema_db == "balanga" || mainSceneController.schema_db.equals("balanga")) {
       	 		templateStr = currentDir + "\\template\\AgentCommperClient_Balanga.xlsx";
       	 		outputStr = currentDir + "\\report\\"+timeStamp+"_AgentCommissionPerClient_Balanga.xlsx";
       	 	}else {
       	 		templateStr = currentDir + "\\template\\AgentCommperClient_Abucay.xlsx";
       	 		outputStr = currentDir + "\\report\\"+timeStamp+"_AgentCommissionPerClient_Abucay.xlsx";
       	 	}

       	 WriteToExcel.writeCommissionSummaryPerClient(ClientTbl.getItems(),templateStr,outputStr,dateRange,agentName,
       			totalPayment.getText(),totalSeller.getText(),totalAgent.getText());
    	}
    }

}
