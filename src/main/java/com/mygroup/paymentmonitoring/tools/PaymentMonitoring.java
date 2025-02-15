package com.mygroup.paymentmonitoring.tools;


import com.mygroup.paymentmonitoring.tools.utils.Alerts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PaymentMonitoring extends Application{
	
	public static boolean isSplashLoaded  = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		// TODO Auto-generated method stub
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/mainScene.fxml"));
			Parent root= loader.load();
			Scene scene = new Scene(root);
			primaryStage.setTitle("Real Estate Payment Monitoring System (REPMS)");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			//primaryStage.initStyle(StageStyle.TRANSPARENT);
			//scene.setFill(Color.TRANSPARENT);
			primaryStage.show();
	
			primaryStage.setOnCloseRequest(event -> {
				//System.out.println(event);
				Alert alert = Alerts.confirmation("Do you want to close this?", "Confirmation");
				Stage stage = (Stage) root.getScene().getWindow();
				if(alert.getResult() == ButtonType.YES) {
					stage.close();
				}else {
					handle(event);
				}
			});
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		

	}
	
	public void handle(javafx.stage.WindowEvent event) {
		event.consume();
	}

}

