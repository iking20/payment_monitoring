package com.mygroup.paymentmonitoring.tools.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.jxls.common.Context;
import org.apache.poi.ss.usermodel.Workbook;

import com.mygroup.paymentmonitoring.tools.model.AgentCommissionSummary;
import com.mygroup.paymentmonitoring.tools.model.AgentCommissionbyClient;
import com.mygroup.paymentmonitoring.tools.model.ClientSummaryonDue;
import com.mygroup.paymentmonitoring.tools.model.Contract;
import com.mygroup.paymentmonitoring.tools.model.Customer;
import com.mygroup.paymentmonitoring.tools.model.Expenses;
import com.mygroup.paymentmonitoring.tools.model.PaymentHistory;
import com.mygroup.paymentmonitoring.tools.model.SummaryContract;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class WriteToExcel {

	public static void writeSummary(List<SummaryContract> contractListSummary, String templateStr, String outputStr, String tcp, String payment, String balance) {
		// TODO Auto-generated method stub
		
		Path templatePath = Paths.get(templateStr);
		Path outputPath = Paths.get(outputStr);
		
		try(InputStream inputStream = Files.newInputStream(templatePath)){
			try(OutputStream outputStream = Files.newOutputStream(outputPath)){
				
				Workbook workbook = new XSSFWorkbook(inputStream);
				XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
				workbook.setSheetName(0, "Summary Report");
				
				PoiTransformer transformer = PoiTransformer.createTransformer(workbook);
				transformer.setOutputStream(outputStream);
				
				Context context = new Context();
				context.putVar("records", contractListSummary);
				context.putVar("tcp",tcp);
				context.putVar("payment",payment);
				context.putVar("balance",balance);
				JxlsHelper.getInstance().setUseFastFormulaProcessor(true).processTemplate(context, transformer);
				inputStream.close();
				outputStream.close();
				Alert alert = Alerts.information("Extraction Completed", "Information");
				if(alert.getResult() == ButtonType.OK) {
					Desktop.getDesktop().open(new File(outputStr));
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
				Alert alert = Alerts.error(e.toString(), "Error!!");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
	}

	public static void writeReservation(String fullName, String templateStr, String outputStr) {
		// TODO Auto-generated method stub
		Path templatePath = Paths.get(templateStr);
		Path outputPath = Paths.get(outputStr);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		
		try(InputStream inputStream = Files.newInputStream(templatePath)){
			try(OutputStream outputStream = Files.newOutputStream(outputPath)){
				
				Workbook workbook = new XSSFWorkbook(inputStream);
				XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
				workbook.setSheetName(0, "Reservation");
				
				PoiTransformer transformer = PoiTransformer.createTransformer(workbook);
				transformer.setOutputStream(outputStream);
				
				Context context = new Context();
				context.putVar("fullName", fullName);
				context.putVar("dateNow", LocalDate.now().format(formatter));
				
				JxlsHelper.getInstance().setUseFastFormulaProcessor(true).processTemplate(context, transformer);
				inputStream.close();
				outputStream.close();
				Desktop.getDesktop().open(new File(outputStr));
				
				
			}catch(Exception e) {
				e.printStackTrace();
				Alert alert = Alerts.error(e.toString(), "Error!!");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
	}

	public static void writeAcknowledgement(String fullName, String templateStr, String outputStr, int amountToPay,
			String blockNo, String lotNo, String aRNo) {
		// TODO Auto-generated method stub
		Path templatePath = Paths.get(templateStr);
		Path outputPath = Paths.get(outputStr);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		DecimalFormat formatter2 = new DecimalFormat("#,###");
		
		try(InputStream inputStream = Files.newInputStream(templatePath)){
			try(OutputStream outputStream = Files.newOutputStream(outputPath)){
				
				Workbook workbook = new XSSFWorkbook(inputStream);
				XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
				workbook.setSheetName(0, "Acknowledgement");
				
				PoiTransformer transformer = PoiTransformer.createTransformer(workbook);
				transformer.setOutputStream(outputStream);
				
				Context context = new Context();
				context.putVar("fullName", fullName);
				context.putVar("dateNow", LocalDate.now().format(formatter));
				context.putVar("amountToPay", formatter2.format(amountToPay));
				context.putVar("blockNo", blockNo);
				context.putVar("lotNo", lotNo);
				context.putVar("aRNo", aRNo);
				
				JxlsHelper.getInstance().setUseFastFormulaProcessor(true).processTemplate(context, transformer);
				inputStream.close();
				outputStream.close();
				Desktop.getDesktop().open(new File(outputStr));
				
			}catch(Exception e) {
				e.printStackTrace();
				Alert alert = Alerts.error(e.toString(), "Error!!");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
	}

	public static void writeSOA(String custId, String custName, List<Contract> contractListGlobal,
			List<PaymentHistory> paymentListGlobal, String templateStr, String outputStr) {
		// TODO Auto-generated method stub
		Path templatePath = Paths.get(templateStr);
		Path outputPath = Paths.get(outputStr);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		DecimalFormat formatter2 = new DecimalFormat("#,###");
		
		try(InputStream inputStream = Files.newInputStream(templatePath)){
			try(OutputStream outputStream = Files.newOutputStream(outputPath)){
				
				Workbook workbook = new XSSFWorkbook(inputStream);
				XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
				workbook.setSheetName(0, "SOA");
				
				PoiTransformer transformer = PoiTransformer.createTransformer(workbook);
				transformer.setOutputStream(outputStream);
				
				Context context = new Context();
				context.putVar("dateNow", LocalDate.now().format(formatter));
				context.putVar("contID", contractListGlobal.get(0).getContractNo());
				context.putVar("blkNo", contractListGlobal.get(0).getBlockNo());
				context.putVar("lotNo", contractListGlobal.get(0).getLotNo());
				context.putVar("lotArea", contractListGlobal.get(0).getLotArea());
				context.putVar("TCP", contractListGlobal.get(0).getTotalPrice());
				context.putVar("tpayment", contractListGlobal.get(0).getPayment());
				context.putVar("balance", contractListGlobal.get(0).getBalance());
				context.putVar("payment", paymentListGlobal);
				context.putVar("custID", custId);
				context.putVar("fullName", custName);
				
				JxlsHelper.getInstance().setUseFastFormulaProcessor(true).processTemplate(context, transformer);
				inputStream.close();
				outputStream.close();
				Alert alert = Alerts.information("Extraction Completed", "Information");
				if(alert.getResult() == ButtonType.OK) {
					Desktop.getDesktop().open(new File(outputStr));
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
				Alert alert = Alerts.error(e.toString(), "Error!!");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
	}

	public static void writeCommissionSummary(ObservableList<AgentCommissionSummary> items, String templateStr,
			String outputStr, String dateRange) {
		
		Path templatePath = Paths.get(templateStr);
		Path outputPath = Paths.get(outputStr);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		DecimalFormat formatter2 = new DecimalFormat("#,###");
		
		try(InputStream inputStream = Files.newInputStream(templatePath)){
			try(OutputStream outputStream = Files.newOutputStream(outputPath)){
				
				Workbook workbook = new XSSFWorkbook(inputStream);
				XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
				workbook.setSheetName(0, "AgentCommissionSummary");
				
				PoiTransformer transformer = PoiTransformer.createTransformer(workbook);
				transformer.setOutputStream(outputStream);
				
				Context context = new Context();
				context.putVar("agentComm", items);
				context.putVar("dateRange", dateRange);
				
				JxlsHelper.getInstance().setUseFastFormulaProcessor(true).processTemplate(context, transformer);
				inputStream.close();
				outputStream.close();
				Alert alert = Alerts.information("Extraction Completed", "Information");
				if(alert.getResult() == ButtonType.OK) {
					Desktop.getDesktop().open(new File(outputStr));
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
				Alert alert = Alerts.error(e.toString(), "Error!!");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
	}

	public static void writeClientonDueSummary(ObservableList<ClientSummaryonDue> clientonDue, String templateStr,
			String outputStr, String dateRange) {
		
		Path templatePath = Paths.get(templateStr);
		Path outputPath = Paths.get(outputStr);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		DecimalFormat formatter2 = new DecimalFormat("#,###");
		
		try(InputStream inputStream = Files.newInputStream(templatePath)){
			try(OutputStream outputStream = Files.newOutputStream(outputPath)){
				
				Workbook workbook = new XSSFWorkbook(inputStream);
				XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
				workbook.setSheetName(0, "ClientSummary_onDue");
				
				PoiTransformer transformer = PoiTransformer.createTransformer(workbook);
				transformer.setOutputStream(outputStream);
				
				Context context = new Context();
				context.putVar("clientonDue", clientonDue);
				context.putVar("dateRange", dateRange);
				
				JxlsHelper.getInstance().setUseFastFormulaProcessor(true).processTemplate(context, transformer);
				inputStream.close();
				outputStream.close();
				Alert alert = Alerts.information("Extraction Completed", "Information");
				if(alert.getResult() == ButtonType.OK) {
					Desktop.getDesktop().open(new File(outputStr));
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
				Alert alert = Alerts.error(e.toString(), "Error!!");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
	}

	public static void writeCommissionSummaryPerClient(ObservableList<AgentCommissionbyClient> items,
			String templateStr, String outputStr, String dateRange, String agentName, String totalPayment, String totalSeller, String totalAgent) {
		
		Path templatePath = Paths.get(templateStr);
		Path outputPath = Paths.get(outputStr);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		DecimalFormat formatter2 = new DecimalFormat("#,###");
		
		try(InputStream inputStream = Files.newInputStream(templatePath)){
			try(OutputStream outputStream = Files.newOutputStream(outputPath)){
				
				Workbook workbook = new XSSFWorkbook(inputStream);
				XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
				workbook.setSheetName(0, "ClientSummary_onDue");
				
				PoiTransformer transformer = PoiTransformer.createTransformer(workbook);
				transformer.setOutputStream(outputStream);
				
				Context context = new Context();
				context.putVar("myclients", items);
				context.putVar("dateRange", dateRange);
				context.putVar("agentName", agentName);
				context.putVar("totalPaymentAmount", totalPayment);
				context.putVar("totalSellComm", totalSeller);
				context.putVar("totalAgentComm", totalAgent);
				
				JxlsHelper.getInstance().setUseFastFormulaProcessor(true).processTemplate(context, transformer);
				inputStream.close();
				outputStream.close();
				Alert alert = Alerts.information("Extraction Completed", "Information");
				if(alert.getResult() == ButtonType.OK) {
					Desktop.getDesktop().open(new File(outputStr));
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
				Alert alert = Alerts.error(e.toString(), "Error!!");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
	}

	public static void writeExpenseData(ObservableList<Expenses> items, String templateStr, String outputStr,
			LocalDate now) {
		
		Path templatePath = Paths.get(templateStr);
		Path outputPath = Paths.get(outputStr);
		DecimalFormat formatter2 = new DecimalFormat("#,###");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		
		try(InputStream inputStream = Files.newInputStream(templatePath)){
			try(OutputStream outputStream = Files.newOutputStream(outputPath)){
				
				Workbook workbook = new XSSFWorkbook(inputStream);
				XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
				workbook.setSheetName(0, "Expenses");
				Double mytotal = 0.00;
				
				for(Expenses item : items) {
					
					mytotal+= Double.parseDouble(item.getExp_amount());
					item.setExp_amount(formatter2.format(Double.parseDouble(item.getExp_amount())));
				}
				
				PoiTransformer transformer = PoiTransformer.createTransformer(workbook);
				transformer.setOutputStream(outputStream);
				
				Context context = new Context();
				context.putVar("expenses", items);
				context.putVar("toDate", formatter.format(now));
				context.putVar("myTotal", formatter2.format(mytotal));
				
				JxlsHelper.getInstance().setUseFastFormulaProcessor(true).processTemplate(context, transformer);
				inputStream.close();
				outputStream.close();
				Alert alert = Alerts.information("Extraction Completed", "Information");
				if(alert.getResult() == ButtonType.OK) {
					Desktop.getDesktop().open(new File(outputStr));
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
				Alert alert = Alerts.error(e.toString(), "Error!!");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
	}

}
