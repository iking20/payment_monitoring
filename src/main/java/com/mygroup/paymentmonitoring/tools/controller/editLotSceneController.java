package com.mygroup.paymentmonitoring.tools.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.mygroup.paymentmonitoring.tools.model.Lot;
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

public class editLotSceneController {

    @FXML
    public TextField editBlockNoTxt,editTPriceTxt,editLotAreaTxt,editLotCode,editLotNoTxt,editPriceTxt,editavailTxt;

    @FXML
    public ComboBox<String> editColorCombo;

    @FXML
    private Button editLotCancelBtn,editLotSubmitBtn,searchLottoEdit;


    @FXML
    void computeTCP(KeyEvent event) {
    	
    	int lotArea = 0, price = 0, tcp = 0;
    	
    	if(editLotAreaTxt.getText() != "") {
    		lotArea = Integer.parseInt(editLotAreaTxt.getText());
    		
    	}
    	
    	if(editPriceTxt.getText() != "") {
    		price = Integer.parseInt(editPriceTxt.getText());
    	}
    	//System.out.print(lotArea + " " + price);
    	tcp = lotArea * price;
    	DecimalFormat formatter = new DecimalFormat("#,###");
    	editTPriceTxt.setText(formatter.format(tcp));

    }

    @FXML
    void doCancel(ActionEvent event) {
    	Stage stage = (Stage) editLotCancelBtn.getScene().getWindow();
    	stage.close();
    }

    @FXML
    void doSubmit(ActionEvent event) {
    	
    	Lot lotmodel = new Lot(editBlockNoTxt.getText(), editLotNoTxt.getText(), editLotAreaTxt.getText(), 
    			editPriceTxt.getText(), editTPriceTxt.getText(),Boolean.valueOf(editavailTxt.getText()), 
    			editColorCombo.getValue(),editLotCode.getText());
    	
    	List<Lot> lotList = new ArrayList<>();
    	lotList.add(lotmodel);
    	
    	boolean isEmptyField = Validate.checkLot(lotList);
    	if(isEmptyField) {
    		Alert alert = Alerts.error("Please fill empty Field(s)", "Error");
    	}else {
    		Alert alert = Alerts.confirmation("Do you want to submit this?", "Confirmation");
    		if(alert.getResult() == ButtonType.YES) {
    			String connStr = mainSceneController.postgresConnStr;
    			String schemaDb = mainSceneController.schema_db;
    			int rs = MyDao.editLot(connStr, schemaDb, "lot", lotList);
    			if(rs>0) {
    				Alert alert2 = Alerts.information("Lot saved successfully!!", "Information");
    				if(alert2.getResult() == ButtonType.OK) {
    			    	Stage stage = (Stage) editLotCancelBtn.getScene().getWindow();
    			    	stage.close();
    				}
    			}else {
    				Alert alert2 = Alerts.error("Something went wrong, check your data.", "Error");
    			}
    		}
    	}

    }

    @FXML
    void validateInput1(KeyEvent event) {
    	filterInput(event, editBlockNoTxt);
    }

    @FXML
    void validateInput2(KeyEvent event) {
    	filterInput(event, editLotNoTxt);
    }

    @FXML
    void validateInput3(KeyEvent event) {
    	filterInput(event, editLotAreaTxt);
    }

    @FXML
    void validateInput4(KeyEvent event) {
    	filterInput(event, editPriceTxt);
    }
    
    private void filterInput(KeyEvent event, TextField txtfield) {
    	
    	if(!event.getCode().equals(KeyCode.BACK_SPACE) && !event.getCode().equals(KeyCode.DELETE)
    			&& !event.getCode().isDigitKey() && !event.getCode().isArrowKey()
    			&& !event.getCode().equals(KeyCode.ENTER) && !event.getCode().equals(KeyCode.TAB)) {
    		
    		Alert alert = Alerts.error("Invalid Input!!", "Error");
    		if(alert.getResult() == ButtonType.OK) {
    			txtfield.clear();
    		}
    	}
    }

}
