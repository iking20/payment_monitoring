package com.mygroup.paymentmonitoring.tools.controller;

import com.mygroup.paymentmonitoring.tools.model.Customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class searchCustomerSceneController {


    @FXML TableColumn<Customer, String> custNoCol;

    @FXML TableColumn<Customer, String> fnameCol;

    @FXML TableColumn<Customer, String> lnameCol;

    @FXML TableColumn<Customer, String> mnameCol;
    
    @FXML TableView<Customer> CustomerTbl;
    
    ObservableList<Customer> selectedCustomer = FXCollections.observableArrayList();
    
    @FXML
    void onSelectCustomer(MouseEvent event) {
    	
    	selectedCustomer = CustomerTbl.getSelectionModel().getSelectedItems();

    }


}
