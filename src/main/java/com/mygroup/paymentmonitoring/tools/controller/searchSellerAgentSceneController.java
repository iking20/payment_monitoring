package com.mygroup.paymentmonitoring.tools.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class searchSellerAgentSceneController<T> {

    @FXML TableView<T> SellerAgentTable;

    @FXML TableColumn<T, String> FirstNameCol;

    @FXML TableColumn<T, String> IDNoCol;

    @FXML TableColumn<T, String> LastNameCol;

    @FXML TableColumn<T, String> MiddleNameCol;
    
    ObservableList<T> selectedItem = FXCollections.observableArrayList();

    @FXML
    void selectIDNumber(MouseEvent event) {
    	selectedItem = SellerAgentTable.getSelectionModel().getSelectedItems();
    }

}
