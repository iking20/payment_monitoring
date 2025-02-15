package com.mygroup.paymentmonitoring.tools.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public final class Alerts {
	
	static Alert alert;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int centerX = screenSize.width/2;
	static int centerY = screenSize.height/2;
	
	
	
	public static Alert confirmation(String message, String title) {
		alert  = new Alert(AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
		alert.setTitle(title);
		alert.setX(centerX - 110);
		alert.setY(centerY - 180);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
		return alert;
	}
	
	public static Alert information(String message, String title) {
		alert  = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
		alert.setTitle(title);
		alert.setX(centerX - 110);
		alert.setY(centerY - 180);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
		return alert;
	}
	
	public static Alert error(String message, String title) {
		alert  = new Alert(AlertType.ERROR, message, ButtonType.OK);
		alert.setTitle(title);
		alert.setX(centerX - 110);
		alert.setY(centerY - 180);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
		return alert;
	}
	

}
