package com.mygroup.paymentmonitoring.tools.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mygroup.paymentmonitoring.tools.model.Contract;
import com.mygroup.paymentmonitoring.tools.model.Seller;
import com.mygroup.paymentmonitoring.tools.queries.MyDao;
import com.mygroup.paymentmonitoring.tools.utils.Alerts;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;


public class cancelReserveSceneController implements Initializable{

    @FXML
    TextField AgentNoText,BalanceText,BlkNoText,ContIDText,CustIDText,DateEndText,
    DateStartText,DurationText,PriceText,PaymentText,LotNoText,LotAreaText,SellerNoText,TpriceText;

    @FXML
    Button CancelBtn,SubmitBtn;

    @FXML
    RadioButton CancelRadio,RefundRadio;


    @FXML
    void doCancel(ActionEvent event) {
    	Stage stage = (Stage) CancelBtn.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void doSubmit(ActionEvent event) {
    	Contract contractModel;
    	List<Contract> refundList = new ArrayList<>();

    	if(CancelRadio.isSelected() == false && RefundRadio.isSelected() == false) {
        	Alert alert = Alerts.error("Please select either to refund or just to cancel reservation.", "Error");
        }
        	
        if(CancelRadio.isSelected()) {
        	Alert alert = Alerts.confirmation("Do you want to cancel this reservation", "Confirmation");
        	if(alert.getResult() == ButtonType.YES) {
        		contractModel = new Contract(ContIDText.getText(), CustIDText.getText(), BlkNoText.getText(), LotNoText.getText(), 
        					LotAreaText.getText(), DurationText.getText(), PriceText.getText(), TpriceText.getText(), "0", 
        					BalanceText.getText(), DateStartText.getText(), DateEndText.getText(), SellerNoText.getText(), AgentNoText.getText(), 
        					null);
        		refundList.add(contractModel);
        	}
        }
        	
        if(RefundRadio.isSelected()) {
        	Alert alert = Alerts.confirmation("Do you want to cancel and refund this reservation", "Confirmation");
        	if(alert.getResult() == ButtonType.YES) {
        		contractModel = new Contract(ContIDText.getText(), CustIDText.getText(), BlkNoText.getText(), LotNoText.getText(), 
        					LotAreaText.getText(), DurationText.getText(), PriceText.getText(), TpriceText.getText(), PaymentText.getText(), 
        					BalanceText.getText(), DateStartText.getText(), DateEndText.getText(), SellerNoText.getText(), AgentNoText.getText(), 
        					null);
        		refundList.add(contractModel);
        	}
        }
        	
        	//System.out.println(refundList.size());
        if(refundList.size()>0) {
           	MyDao.updateLotAvailability(mainSceneController.postgresConnStr,mainSceneController.schema_db,"lot",
           			BlkNoText.getText(),LotNoText.getText(),true);
           	MyDao.updateCustomerContractStatus(mainSceneController.postgresConnStr,mainSceneController.schema_db,"customer",
           			CustIDText.getText());
           	MyDao.updateCustomerContractStatus(mainSceneController.postgresConnStr,mainSceneController.schema_db,"contract",
           			CustIDText.getText());
           	MyDao.insertRefund(mainSceneController.postgresConnStr,mainSceneController.schema_db,refundList,"refund");
           	Alert alert = Alerts.information("Reservation is cancelled", "Information!!");
           	if(alert.getResult() == ButtonType.OK) {
               	Stage stage = (Stage) CancelBtn.getScene().getWindow();
               	stage.close();
            }
        }
    		
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ToggleGroup tg = new ToggleGroup(); 
		CancelRadio.setToggleGroup(tg);
		RefundRadio.setToggleGroup(tg);	
		
		CancelRadio.setOnAction(event ->{
			PaymentText.setEditable(false);
		});
		
		RefundRadio.setOnAction(event ->{
			PaymentText.setEditable(true);
		});
	}

}
