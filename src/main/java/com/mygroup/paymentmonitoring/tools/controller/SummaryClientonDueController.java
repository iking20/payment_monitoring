package com.mygroup.paymentmonitoring.tools.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import com.mygroup.paymentmonitoring.tools.model.ClientSummaryonDue;
import com.mygroup.paymentmonitoring.tools.queries.MyDao;
import com.mygroup.paymentmonitoring.tools.utils.Alerts;
import com.mygroup.paymentmonitoring.tools.utils.WriteToExcel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SummaryClientonDueController {

    @FXML
    private TableView<ClientSummaryonDue> ClientDueTbl;

    @FXML
    private TableColumn<ClientSummaryonDue, String> CustIDCol,CustomerNameCol,DueAmountCol,
    lastPaymentCol,CustDueDateCol,CustMonlyCol,CustNoDueCol,CustLeftOverCol;

    @FXML
    private Button cancelBtn,clearData,extractBtn,viewData;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TabPane myTabs;
    
    List<ClientSummaryonDue> clientonDueSummary = new ArrayList<>();


    @FXML
    void ClearData(ActionEvent event) {
    	ClientDueTbl.getItems().clear();
    	clientonDueSummary.clear();
    	datePicker.setValue(null);
    	
    }

    @FXML
    void DoExtractData(ActionEvent event) {
    	
    	if(ClientDueTbl.getItems().isEmpty()) {
    		Alert alert = Alerts.error("No DATA to Extract!!", "Error!!");
    	}else {
    		
       	 	String currentDir = System.getProperty("user.dir");
       	 	String templateStr = ""; String outputStr = "";
       	 	//System.out.println("Current dir using System:" + currentDir);
       	 	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
       	 	String dateRange = datePicker.getValue().toString();
       	 
       	 	if(mainSceneController.schema_db == "balanga" || mainSceneController.schema_db.equals("balanga")) {
       	 		templateStr = currentDir + "\\template\\ClientDueSummary_Balanga.xlsx";
       	 		outputStr = currentDir + "\\report\\"+timeStamp+"_ClientSummaryonDue_Balanga.xlsx";
       	 	}else {
       	 		templateStr = currentDir + "\\template\\ClientDueSummary_Abucay.xlsx";
       	 		outputStr = currentDir + "\\report\\"+timeStamp+"_ClientSummaryonDue_Abucay.xlsx";
       	 	}
       	 	
       	 	WriteToExcel.writeClientonDueSummary(ClientDueTbl.getItems(),templateStr,outputStr,dateRange);
    	}

    }

    @FXML
    void DoSelectTab(MouseEvent event) {

    }

    @FXML
    void closeWindow(ActionEvent event) {
    	Stage stage = (Stage) cancelBtn.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void selectIDNumber(MouseEvent event) {

    }

    @FXML
    void viewSummaryClient(ActionEvent event) {
    	
    	if(datePicker.getValue() == null) {
    		Alert alert = Alerts.error("No Date Selected", "Error!!");
    	}else {
    		
    		clientonDueSummary =  MyDao.getCustomerListSummary(mainSceneController.postgresConnStr, 
        			mainSceneController.schema_db, "customer","contract");
    		
    		for(ClientSummaryonDue client: clientonDueSummary) {
    			String lastPayment = MyDao.getLastPayment(mainSceneController.postgresConnStr, 
    					mainSceneController.schema_db, "payments", client.getCustID());
    			client.setCustlastPaymentDate(lastPayment);
    			
    			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    			LocalDate startDate =  LocalDate.parse(client.getCustStartDate(),format);
    			LocalDate lastPaymentDate = LocalDate.parse(lastPayment,format);
    			LocalDate actualDueDate = startDate.plusMonths((int) ChronoUnit.MONTHS.between(startDate,lastPaymentDate));
    			
    			double customerMultiplier =  0.016667;
    			double monthlyInstallment = Double.parseDouble(client.getCustTotalPrice().replace(",", ""))* customerMultiplier;
    			int roundedOffmonthlyInstallment = (int) Math.rint(monthlyInstallment);
    			int lengthOfMonth = (int) ChronoUnit.MONTHS.between(actualDueDate,datePicker.getValue());
    			int leftoverdue = MyDao.getLeftOverAmountDue(mainSceneController.postgresConnStr,mainSceneController.schema_db,
    					"leftover",client.getCustID());
    			int monthlyDue = (roundedOffmonthlyInstallment * lengthOfMonth)+leftoverdue;
    			DecimalFormat df = new DecimalFormat("#,###");
    			client.setCustAmountDue(df.format(monthlyDue));
    			client.setCustDueDate(actualDueDate.toString());
    			client.setCustMonthly(df.format(roundedOffmonthlyInstallment));
    			client.setCustNoDue(String.valueOf(lengthOfMonth));
    			client.setCustLeftOver(df.format(leftoverdue));
    		}
    		
    	}
    	
    	clientonDueSummary = clientonDueSummary.stream()
    										   .filter(client -> Integer.parseInt(client.getCustAmountDue().replace(",", "")) > 0)
    										   .collect(Collectors.toList());
    	loadClientonDueSummaryTbl();

    }

	private void loadClientonDueSummaryTbl() {
		
		if(!clientonDueSummary.isEmpty()) {
			ObservableList<ClientSummaryonDue> clientSummary = FXCollections.observableArrayList();
			clientSummary.addAll(clientonDueSummary);
			CustIDCol.setCellValueFactory(new PropertyValueFactory<ClientSummaryonDue,String>("custID"));
			CustomerNameCol.setCellValueFactory(new PropertyValueFactory<ClientSummaryonDue,String>("custName"));
			CustDueDateCol.setCellValueFactory(new PropertyValueFactory<ClientSummaryonDue,String>("custDueDate"));
			DueAmountCol.setCellValueFactory(new PropertyValueFactory<ClientSummaryonDue,String>("custAmountDue"));
			lastPaymentCol.setCellValueFactory(new PropertyValueFactory<ClientSummaryonDue,String>("custlastPaymentDate"));
			CustMonlyCol.setCellValueFactory(new PropertyValueFactory<ClientSummaryonDue,String>("custMonthly"));
			CustNoDueCol.setCellValueFactory(new PropertyValueFactory<ClientSummaryonDue,String>("custNoDue"));
			CustLeftOverCol.setCellValueFactory(new PropertyValueFactory<ClientSummaryonDue,String>("custLeftOver"));
			ClientDueTbl.getItems().clear();
			ClientDueTbl.setItems(clientSummary);
		}else {
			ClientDueTbl.getItems().clear();
		}
		
	}

}
