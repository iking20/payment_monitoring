package com.mygroup.paymentmonitoring.tools.queries;

import java.sql.Connection;
import java.sql.DriverManager;
import com.mygroup.paymentmonitoring.tools.utils.Alerts;

import javafx.scene.control.Alert;

public class ConnectPosgreSQL {
	
	public static Connection connect(String postgresConnStr) {
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(postgresConnStr);
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!");
		}
		return conn;
	}

}
