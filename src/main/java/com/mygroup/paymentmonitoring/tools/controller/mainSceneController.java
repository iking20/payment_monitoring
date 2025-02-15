package com.mygroup.paymentmonitoring.tools.controller;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import com.mygroup.paymentmonitoring.tools.utils.Alerts;
import com.mygroup.paymentmonitoring.tools.utils.Validate;
import com.mygroup.paymentmonitoring.tools.utils.WriteToExcel;
import com.mygroup.paymentmonitoring.tools.PaymentMonitoring;
import com.mygroup.paymentmonitoring.tools.model.Agent;
import com.mygroup.paymentmonitoring.tools.model.AngentInfo;
import com.mygroup.paymentmonitoring.tools.model.AgentCommission;
import com.mygroup.paymentmonitoring.tools.model.AgentCommissionSummary;
import com.mygroup.paymentmonitoring.tools.model.Contract;
import com.mygroup.paymentmonitoring.tools.model.Customer;
import com.mygroup.paymentmonitoring.tools.model.CustomerPayment;
import com.mygroup.paymentmonitoring.tools.model.Expenses;
import com.mygroup.paymentmonitoring.tools.model.Lot;
import com.mygroup.paymentmonitoring.tools.model.PaymentHistory;
import com.mygroup.paymentmonitoring.tools.model.PostgresCredentials;
import com.mygroup.paymentmonitoring.tools.model.Seller;
import com.mygroup.paymentmonitoring.tools.model.SellerAgentInfo;
import com.mygroup.paymentmonitoring.tools.model.SellerCommission;
import com.mygroup.paymentmonitoring.tools.model.SummaryContract;
import com.mygroup.paymentmonitoring.tools.queries.ConnectPosgreSQL;
import com.mygroup.paymentmonitoring.tools.queries.MyDao;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class mainSceneController implements Initializable{
	
    @FXML
    public RadioButton bal_Rb,Abu_Rb;

    @FXML
    public DatePicker bday,date_end,date_start,dueDatePicker;

    @FXML
    public ComboBox<String> blk_no,lot_no,MonthCombo,filterComboMonth;

    @FXML
    public AnchorPane mainPane,loginPane,dashPane,AnchorContractPane;

    @FXML
    public PasswordField pass_passfield;

    @FXML
    public Pane reservePane,boardPane,paymentPane,dataPane,addPane,historyPane,
    userSidePane,userPame,viewEditLotPane,ContractPane;
    
    @FXML
    private TitledPane TitleContractPanr,TitlePaymentPane;

    @FXML
    public TextField seller_no_txt, user_textfield,price_txt,port_textfield,phone_txt,payment_txt,
    mname_txt,mail_txt,db_textfield,duration_txt,fname_txt,host_textfield,lname_txt,cont_no_txt,
    cust_no_txt,balance_txt,agent_no_txt,lot_area_txt,total_price_txt,filterYearTxt,expenseTotal;
    
    @FXML
    public TextField paymentCustIdTxt,amountToPay_txt,searchContract_Txt,viewSearchContract,
    paymentSearchTxt,lotSearchTxt,searchSellerTxt,searchAgentTxt,viewsearchCustomerTxt,historySearchContract,
    historyCustID,historyContID,historyName,historyBlkNo,historyLotNo,historyLotArea,historyTCP,historyPayment,historyBalance,
    viewSearchExpense,viewSearchRefund,tcp_textfield,payment_textfield,bal_textfield;
    
    @FXML
    private Label paymentNameLbl,paymentContNoLbl,paymentblockNoLbl,paymentPriceLbl,
    paymentlotAreaLbl,paymentlotLbl,paymentTPriceLbl,paymentbalanceLbl,paymentDueLbl,
    monthlyinstallmentLbl,paymentCustNoLbl,paymentlotNoLbl,paymentpaymentLbl,ovedueLbl,
    overdueAmountLbl,agentCodeLbl,sellerCodeLbl,dashpaymentLbl,dashSellerLbl,dashAgentLbl,
    totalpaymentLbl,dashExpense,dashRefund;
    
    @FXML
    private Label totalLotLbl,agentLbl,sellerLbl,lotAvailLbl,localtionLbl,leftoveramountLbl,actuaDueLbl;


    @FXML
    public Button submit_btn,search_seller_btn,search_agent_btn,reserveBtn,logout_btn,dashBtn,viewDataBtn,
    clear_btn,close_btn,connect_btn,paymentSearchBtn,makePayBtn,addLotBtn,addAgentBtn,addSellerBtn,
    addBtn,generateSummaryBtn,ReportBtn,historySearchContractBtn,historyClearBtn,ReportSOABtn,addExpenseBtn,
    cancelReserve,editLotBtn,viewContractBtn,editpayBtn,editInfoBtn,filterBtn2,ClearFilterBtn2,expExtractBtn;
    
    @FXML
    private TableColumn<CustomerPayment, String> paymentCol,dateCol;
    
    @FXML
    private TableView<CustomerPayment> priceTbl;
    
    @FXML
    private TableColumn<Contract, String> dashCustNoCol,dashBlkCol,dashLotCol,dashPriceCol,dashTpriceCol,
    dashPaymentCol,dashBalanceCol,dashSellerCol;
    
    @FXML
    private TableColumn<SummaryContract,String> viewCustIdCol,viewNameCol,viewBlkNoCol,viewLotNoCol,viewLotAreaCol,viewDurationCol,viewPriceCol,
    viewTPriceCol,viewPaymentCol,viewBalanceCol,viewDateStartCol,viewDateEndCol,
    viewSellerName,viewSellerComm,viewAgentName,viewAgentComm,viewLastPayment;
    
    @FXML
    private TableView<SummaryContract> ViewContractTbl;
    
    @FXML
    private TableView<Contract> dashTbl;
    
    @FXML
    private TableView<PaymentHistory> historypaymentTbl;
    
    @FXML
    private TableColumn<PaymentHistory, String> historypaymentCustIdCol,historypaymentNameCol,historypaymentAmountCol,
    historypaymentDateCol,historypaymentSellCol,historypaymentAgentCol;
    
    @FXML
    private TableView<Expenses> viewExpenseTbl;
    
    @FXML
    private TableColumn<Expenses, String> expenseNoCol,expenseDateCol,expenseCatCol,
    expenseTitleCol,expenseDescpCol,expenseAmountCol;
    
    @FXML
    private TabPane TabDataPane;
    
    @FXML
    private TableColumn<Lot, String> lotblockNoCol,lotlotNoCol,lotlotAreCol,lotPriceCol,
    lotTPriceCol,lotAvailCol;
    
    @FXML
    private TableView<Lot> lotTbl;
    
    @FXML
    private TableColumn<SellerAgentInfo, String> sellerNoCol,sellerNameCol,sellerTotalComm;
    
    @FXML
    private TableColumn<AngentInfo, String> 
    agentNoCol,agentNameCol,agentTotalComm,agentCategoryCol,agentDateCol;
    
    @FXML
    private TableView<Customer> viewCustomerTbl;
    
    @FXML
    private TableColumn<Customer, String> viewCustomerNoCol,viewFullNameCol,viewBirthdayCol,
    viewPhoneNoCol,viewEmailAddCol,viewStatCol;
    
    @FXML
    private TableView<SellerAgentInfo> sellerTbl;
    
    @FXML
    private TableView<AngentInfo> agentTbl;
    
    @FXML
    private TableColumn<Contract, String> refundContNoCol,refundCustNoCol,refundBlockNoCol,
    refundLotNoCol,refundAmountCol;
    
    @FXML
    private TableView<Contract> refundTbl;

     
    
    static String postgresConnStr;
    static String schema_db;
    List<Contract>contractListGlobal = new ArrayList<>();
    List<SummaryContract>contractListSummary = new ArrayList<>();
    List<SummaryContract>filteredListSummary = new ArrayList<>();
    List<PaymentHistory>paymentListGlobal = new ArrayList<>();
    List<Lot>lotListGlobal = new ArrayList<>();
    List<SellerAgentInfo>sellerListGlobal = new ArrayList<>();
    List<AngentInfo>agentListGlobal = new ArrayList<>();
    List<Customer>customerListGlobal = new ArrayList<>();
    List<Expenses>expenseListGlobal = new ArrayList<>();
    List<Contract>refundListGlobal = new ArrayList<>();
    ObservableList<SummaryContract> selectedContract = FXCollections.observableArrayList();
    ObservableList<Lot> selectedLot = FXCollections.observableArrayList();
    int lengthOfMonth = 0;
    int lengthOfDay = 0;
    LocalDate lastPayDate;
    double customerMultiplier = 0;
    boolean isSelected = false;
    
    private static final String FILE_NAME = "Credentials.ser";
    private static final double CUSTOMER_MULTIPLIER_GREEN  = 0.016667;
    private static final double SELLER_MULTIPLIER_GREEN  = 0.8462162710489;
    private static final double AGENT_MULTIPLIER_GREEN  = 0.1537837289511;
    
    private static final double CUSTOMER_MULTIPLIER_BLUE  = 0.016667;
    private static final double SELLER_MULTIPLIER_BLUE  = 0.800024;
    private static final double AGENT_MULTIPLIER_BLUE  = 0.200035999;
    
    private static final double CUSTOMER_MULTIPLIER_YELLOW  = 0.016667;
    private static final double SELLER_MULTIPLIER_YELLOW  = 0.818165455;
    private static final double AGENT_MULTIPLIER_YELLOW  = 0.181814546;
    

    @FXML
    void doClose(ActionEvent event) {
    	
		Alert alert = Alerts.confirmation("Do you want to close this?", "Confirmation");
		Stage stage = (Stage) close_btn.getScene().getWindow();
		if(alert.getResult() == ButtonType.YES) {
			stage.close();
		}else {
			
		}
    }

    @FXML
    void doConnect(ActionEvent event) {
    	
		if(bal_Rb.isSelected() == true) {
			schema_db = "balanga";
			localtionLbl.setText("(Balanga)");
		}else {
			schema_db = "abucay";
			localtionLbl.setText("(Abucay)");
		}
		
    	postgresConnStr = "jdbc:postgresql://" +host_textfield.getText()+ ":" 
    	+port_textfield.getText()+ "/" +db_textfield.getText()+ "?user=" +user_textfield.getText()+
    	"&password=" +pass_passfield.getText();
    	
    	Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
    	if(conn != null) {
    		Alert alert = Alerts.information("Connected!!", "Information");
    		if(alert.getResult() == ButtonType.OK) {
    			toSaveCredentials();
    			showDashboard(null);
    			paymenthistoryInitialize();
    			dashPane.toFront();
    			if(user_textfield.getText().equals("postgres") || user_textfield.getText().equals("admin")) {
        			boardPane.toFront();
        			userSidePane.toBack();
    			}else {
    				userSidePane.toFront();
    				userPame.toFront();

    			}
    		}
    	}
    	
    }
    
    @FXML
    void showUserMenu(ActionEvent event) {
    	userPame.toFront();
    	userSidePane.toFront();
    }
    
    @FXML
    void doLogout(ActionEvent event) {
    	Alert alert = Alerts.confirmation("Are you sure do you want to quit?", "Confirmation");
		if(alert.getResult() == ButtonType.YES) {
			loginPane.toFront();
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		if(!PaymentMonitoring.isSplashLoaded) {
			loadSplashScreen();
		}
		
		postgresConnStr="";
		duration_txt.setEditable(false);
		duration_txt.setText("60");
		loginPane.toFront();
		boardPane.toFront();
		ToggleGroup tg = new ToggleGroup(); 
		Abu_Rb.setToggleGroup(tg);
		bal_Rb.setToggleGroup(tg);	 
		
		PostgresCredentials credentials = loadCredentials();
		if(credentials != null) {
			setCredentials(credentials);
		}
		
	}
	
	/**
	 * 
	 */
	private void loadSplashScreen(){
		
		try {
			
			PaymentMonitoring.isSplashLoaded = true;
			
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/splashScene.fxml"));
			pane.setStyle("-fx-background-color: transparent;"); 
			mainPane.getChildren().setAll(pane);
			
			FadeTransition fadeIn = new FadeTransition(Duration.seconds(1),pane);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);
			
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(3),pane);
			fadeOut.setFromValue(1);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);
			
			fadeIn.play();
			fadeIn.setOnFinished((e)->{
				fadeOut.play();
			});
			
			fadeOut.setOnFinished((e)->{
				AnchorPane parentPane;
				try {
					parentPane = FXMLLoader.load(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/mainScene.fxml"));
					mainPane.getChildren().setAll(parentPane);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			});
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
    void showAdd(ActionEvent event) {
		
	}
	
	
	@FXML
    void showDashboard(ActionEvent event) {
		contractListGlobal.clear();
    	dashPane.toFront();
    	boardPane.toFront();
    	DecimalFormat formatter = new DecimalFormat("#,###");
    	
    	int customerPayment =  MyDao.computeAmount(postgresConnStr,schema_db,"contract", "payment");
    	dashpaymentLbl.setText(formatter.format(customerPayment));
    	
    	int totalPayment =  MyDao.computeAmount(postgresConnStr,schema_db,"lot","tcp");
    	totalpaymentLbl.setText(formatter.format(totalPayment));
    	   	
    	int agentComm =  MyDao.computeAmount(postgresConnStr,schema_db,"actual_agentcommission", "comm_amount");
    	dashAgentLbl.setText(formatter.format(agentComm));
    	
    	int expenses =  MyDao.computeAmount(postgresConnStr,schema_db,"expenses", "exp_amount");
    	dashExpense.setText(formatter.format(expenses));
    	
    	int refund =  MyDao.computeAmount(postgresConnStr,schema_db,"refund", "payment");
    	dashRefund.setText(formatter.format(refund));
    	
    	int sellerComm =  customerPayment - (agentComm + expenses + refund);
    	dashSellerLbl.setText(formatter.format(sellerComm));
    	    		
    	contractListGlobal = MyDao.getContractListAll(postgresConnStr,schema_db,"contract");
    	for(Contract list:contractListGlobal) {
    		sellerComm = MyDao.computeCommision(postgresConnStr,schema_db,"sellercommission", "amount",list.getCustomerNo());
    		list.setSellerNo(formatter.format(sellerComm));
    		
    		agentComm = MyDao.computeCommision(postgresConnStr,schema_db,"agentcommission", "amount",list.getCustomerNo());
    		list.setAgentNo(formatter.format(agentComm));
    	}
    	
    	loadDashContactTbl(contractListGlobal);
    	
    	int noOfItem = MyDao.countItem(postgresConnStr,schema_db,"lot");
    	totalLotLbl.setText(Integer.toString(noOfItem));
    	
    	noOfItem = MyDao.countItem(postgresConnStr,schema_db,"seller");
    	sellerLbl.setText(Integer.toString(noOfItem));
    	
    	noOfItem = MyDao.countItem(postgresConnStr,schema_db,"agent");
    	agentLbl.setText(Integer.toString(noOfItem));
    	
    	noOfItem = MyDao.countItemAvailable(postgresConnStr,schema_db,"lot");
    	lotAvailLbl.setText(Integer.toString(noOfItem));
    	
    }
	
    @FXML
    void SearchContractList(KeyEvent event) {
    	
    	List<Contract>filteredList = new ArrayList<>();
    	filteredList.addAll(contractListGlobal);
    	String val = searchContract_Txt.getText();
    	//System.out.println(val);
    	if(val != "" || !val.isEmpty()) {
    		filteredList = filteredList.stream()
    				.filter(data -> data.getCustomerNo().contains(val))
    				.collect(Collectors.toList());
    		loadDashContactTbl(filteredList);
    	}else {
    		loadDashContactTbl(contractListGlobal);
    	}
    	
    }
    
    @FXML
    void  viewFilterRefund(KeyEvent event) {
    	List<Contract>filteredList = new ArrayList<>();
    	filteredList.addAll(refundListGlobal);
    	String val = viewSearchRefund.getText().toLowerCase();
    	if(val != "" || !val.isEmpty()) {
    		//System.out.println(val);
    		filteredList = filteredList.stream()
    				.filter(data -> data.getContractNo().contains(val)
    						|| data.getCustomerNo().contains(val))
    				.collect(Collectors.toList());
    		//System.out.println(filteredList.get(0).getExp_no());
    		loadRefundTbl(filteredList);
    	}else {
    		loadRefundTbl(refundListGlobal);
    	}
    }
    
    @FXML
    void viewFilterExpense(KeyEvent event) {
    	List<Expenses>filteredList = new ArrayList<>();
    	filteredList.addAll(expenseListGlobal);
    	String val = viewSearchExpense.getText().toLowerCase();
    	
    	if(val != "" || !val.isEmpty()) {
    		//System.out.println(val);
    		filteredList = filteredList.stream()
    				.filter(data -> data.getExp_no().contains(val) || data.getExp_title().toLowerCase().contains(val)
    						|| data.getExp_cat().toLowerCase().contains(val))
    				.collect(Collectors.toList());
    		//System.out.println(filteredList.get(0).getExp_no());
    		loadExpenseTbl(filteredList);
    	}else {
    		loadExpenseTbl(expenseListGlobal);
    	}
    	expenseTotal();
    }
    
    @FXML
    void FilterExpensesTbl(ActionEvent event) {
    	
    	if(filterComboMonth.getValue() == null || filterYearTxt.getText() == "") {
    		Alert alert = Alerts.error("Need to Select Month & \nEnter the Year!!", "Error!!");
    	}else {
        	String selectedMonth = filterComboMonth.getValue().substring(0,2);
        	String startDateStr  = filterYearTxt.getText()+"-"+selectedMonth+"-01";
        	LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        	LocalDate endtDate = startDate.withDayOfMonth(
        			startDate.getMonth().length(startDate.isLeapYear()));
        	
        	//System.out.println(startDate + " " + endtDate);
        	
        	List<Expenses>filteredList = new ArrayList<>();
        	List<Expenses>myList = new ArrayList<>();
        	filteredList.addAll(expenseListGlobal);
        	//System.out.println(LocalDate.parse(filteredList.get(0).getExp_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).isAfter(startDate));
        	for(Expenses list : filteredList) {
        		if(LocalDate.parse(list.getExp_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).isAfter(startDate)
        				&& LocalDate.parse(list.getExp_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).isBefore(endtDate)) {
        			myList.add(list);
        		}
        	}
    		loadExpenseTbl(myList);
    		expenseTotal();
    	}
    	
    }
    
    @FXML
    void doExtractExpenses(ActionEvent event) {
    	
   	 	String currentDir = System.getProperty("user.dir");
   	 	String templateStr = ""; String outputStr = "";
   	 	//System.out.println("Current dir using System:" + currentDir);
   	 	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
   	 
   	 	if(schema_db == "balanga" || schema_db.equals("balanga")) {
   	 		templateStr = currentDir + "\\template\\ExpensesBalanga.xlsx";
   	 		outputStr = currentDir + "\\report\\"+timeStamp+"_Expenses_Balanga.xlsx";
   	 	}else {
   	 		templateStr = currentDir + "\\template\\ExpensesAbucay.xlsx";
   	 		outputStr = currentDir + "\\report\\"+timeStamp+"_Expenses_Abucay.xlsx";
   	 	}
   	 	
   	 WriteToExcel.writeExpenseData(viewExpenseTbl.getItems(),templateStr,outputStr,LocalDate.now());
	
    }
    
    @FXML
    void ClearFilter2(ActionEvent event) {
    	loadExpenseTbl(expenseListGlobal);
    	filterYearTxt.clear();
    	filterComboMonth.getSelectionModel().clearSelection();
    	filterComboMonth.setValue(null);
    	filterComboMonth.setPromptText("Select Month here..");
    	expenseTotal();
    }
    
    
    @FXML
    void viewFilterData(KeyEvent event) {
    	
    	filteredListSummary.clear();
    	filteredListSummary.addAll(contractListSummary);
    	String val = viewSearchContract.getText().toLowerCase();
    	//System.out.println(val);
    	if(val != "" || !val.isEmpty()) {
    		filteredListSummary = filteredListSummary.stream()
    				.filter(data -> data.getSummarycustomerNo().contains(val) || data.getSummarycustomerName().toLowerCase().contains(val)
    						|| data.getSummarydateStart().contains(val) || data.getSummarydateEnd().contains(val))
    				.collect(Collectors.toList());
    		loadViewContactTbl(filteredListSummary);
    	}else {
    		loadViewContactTbl(contractListSummary);
    	}
    	
    }
    
    
    @FXML
    void viewFilterCustomer(KeyEvent event) {
    	List<Customer>filteredList = new ArrayList<>();
    	filteredList.addAll(customerListGlobal);
    	String val = viewsearchCustomerTxt.getText().toLowerCase();
    	//System.out.println(val);
    	if(val != "" || !val.isEmpty()) {
    		filteredList = filteredList.stream()
    				.filter(data -> data.getCustNo().contains(val) || data.getLastName().toLowerCase().contains(val))
    						
    				.collect(Collectors.toList());
    		loadCustomerAllTbl(filteredList);
    	}else {
    		loadCustomerAllTbl(customerListGlobal);
    	}
    	
    }
    
    
    @FXML
    void viewFilterSeller(KeyEvent event) {
    	
    	List<SellerAgentInfo>filteredList = new ArrayList<>();
    	filteredList.addAll(sellerListGlobal);
    	String val = searchSellerTxt.getText().toLowerCase();
    	//System.out.println(val);
    	if(val != "" || !val.isEmpty()) {
    		filteredList = filteredList.stream()
    				.filter(data -> data.getSellerAgentNo().contains(val) || data.getSellerAgentName().toLowerCase().contains(val))
    				
    				.collect(Collectors.toList());
    		loadSellerTbl(filteredList);
    	}else {
    		loadSellerTbl(sellerListGlobal);
    	}
    	
    }
    
    @FXML
    void viewFilterAgent(KeyEvent event) {
    	
    	List<AngentInfo>filteredList = new ArrayList<>();
    	filteredList.addAll(agentListGlobal);
    	String val = searchAgentTxt.getText().toLowerCase();
    	//System.out.println(val);
    	if(val != "" || !val.isEmpty()) {
    		filteredList = filteredList.stream()
    				.filter(data -> data.getAgentNo().contains(val) || data.getAgentName().toLowerCase().contains(val))
    				
    				.collect(Collectors.toList());
    		loadAgentTbl(filteredList);
    	}else {
    		loadAgentTbl(agentListGlobal);
    	}
    	
    }
    
    
    @FXML
    void viewFilterLot(KeyEvent event) {
    	
    	List<Lot>filteredList = new ArrayList<>();
    	filteredList.addAll(lotListGlobal);
    	String val = lotSearchTxt.getText().toLowerCase();
    	//System.out.println(val);
    	if(val != "" || !val.isEmpty()) {
    		filteredList = filteredList.stream()
    				.filter(data -> data.getBlockNum().toLowerCase().equals(val) || data.getLotNum().toLowerCase().equals(val)
    						|| data.getLotAvail() == Boolean.valueOf(val))
    				
    						
    				.collect(Collectors.toList());
    		loadLotTbl(filteredList);
    	}else {
    		loadLotTbl(lotListGlobal);
    	}
    }
	
	
	private void loadDashContactTbl(List<Contract> contractList) {
		
		ObservableList<Contract>list = FXCollections.observableArrayList();
		list.addAll(contractList);
		dashCustNoCol.setCellValueFactory(new PropertyValueFactory<Contract,String>("customerNo"));
		dashBlkCol.setCellValueFactory(new PropertyValueFactory<Contract,String>("blockNo"));
		dashLotCol.setCellValueFactory(new PropertyValueFactory<Contract,String>("lotNo"));
		dashPriceCol.setCellValueFactory(new PropertyValueFactory<Contract,String>("price"));
		dashTpriceCol.setCellValueFactory(new PropertyValueFactory<Contract,String>("totalPrice"));
		dashPaymentCol.setCellValueFactory(new PropertyValueFactory<Contract,String>("payment"));
		dashBalanceCol.setCellValueFactory(new PropertyValueFactory<Contract,String>("balance"));
		dashSellerCol.setCellValueFactory(new PropertyValueFactory<Contract,String>("sellerNo"));
		//dashAgentCol.setCellValueFactory(new PropertyValueFactory<Contract,String>("agentNo"));
		dashTbl.setItems(list);
		
	}

    @FXML
    void showReserve(ActionEvent event) throws IOException {
    	reservePane.toFront(); 
    	reserveInitialize();
    }
    

	@FXML
    void doClear(ActionEvent event) {
		reserveInitialize();
    }
	
    @FXML
    void doPayment(ActionEvent event) {
    	paymentPane.toFront();
    	paymentInitialize();
    	
    }
    
    @FXML
    void doPay(ActionEvent event) {
    	
    	if(amountToPay_txt.getText() != "" &&
    			amountToPay_txt.getText().chars().allMatch(Character::isDigit)) {
    		   		
    		int amountToPay = Integer.parseInt(amountToPay_txt.getText());
    		int ondue = Integer.parseInt(paymentDueLbl.getText().replace(",", ""));
 
    			Alert alert = Alerts.confirmation("Do you want to submit this payment?", "Confirmation");
    			if(alert.getResult()  == ButtonType.YES) {
    				isSelected = true;
    				int lastpayment = Integer.parseInt(paymentpaymentLbl.getText().replace(",", ""));
        			int currentpayment = lastpayment+amountToPay;
        			int balance = Integer.parseInt(paymentbalanceLbl.getText().replace(",", ""))-amountToPay;
        			MyDao.updateContract(paymentContNoLbl.getText(),paymentCustNoLbl.getText(),currentpayment,balance,postgresConnStr,schema_db,"contract");
        			
        			String category = MyDao.selectCategory(agentCodeLbl.getText(),postgresConnStr,schema_db,"Agent");
                	double sellerMultiplier = 0.00, agentMultiplier = 0.00;
                	if(category.equals("A") || category == "A"){
                		agentMultiplier = (Double.parseDouble(paymentlotAreaLbl.getText())*1000)/60;
                	}else {
                		agentMultiplier = (Double.parseDouble(paymentTPriceLbl.getText().replace(",", ""))*0.05)/60;
                	}
        			
                	int roundedOffsellerCommission = (int) Math.rint(sellerMultiplier);
                	int roundedOffagentCommission = (int) Math.rint(agentMultiplier);
                	
                	if(priceTbl.getItems().size() == 1 && lengthOfMonth == 1) {
                		lengthOfMonth = 0;
                	}
                	
                	if(priceTbl.getItems().size() == 1 && lengthOfMonth > 1) {
                		lengthOfMonth -=1;
                	}
                	                	
                	int agentCommission = roundedOffagentCommission*(lengthOfMonth);
                	System.out.println(agentMultiplier + " " + agentCommission);
                	int sellerCommission = amountToPay - agentCommission;
                	int leftOverAmnt  = ondue - amountToPay;
                	
        			CustomerPayment paymentModel = new CustomerPayment(paymentCustNoLbl.getText(), 
        					paymentContNoLbl.getText(), Integer.toString(amountToPay), dueDatePicker.getValue().toString());
                	List<CustomerPayment>paymentList = new ArrayList<>();
                	paymentList.add(paymentModel);
                	
                	CustomerPayment leftOverModel = new CustomerPayment(paymentCustNoLbl.getText(), paymentContNoLbl.getText(), 
                			Integer.toString(leftOverAmnt), dueDatePicker.getValue().toString());
                	
                	List<CustomerPayment>leftoverList = new ArrayList<>();
                	leftoverList.add(leftOverModel);
                	
                	SellerCommission sellerCommissionModel = new SellerCommission(paymentCustNoLbl.getText(), 
                			sellerCodeLbl.getText(), Integer.toString(sellerCommission), dueDatePicker.getValue().toString());
                	
                	AgentCommission agentCommissionModel = new AgentCommission(paymentCustNoLbl.getText(), 
                			agentCodeLbl.getText(), Integer.toString(agentCommission), dueDatePicker.getValue().toString());
                	
                	List<SellerCommission>sellerCommisionList = new ArrayList<>();
                	sellerCommisionList.add(sellerCommissionModel);
                	
                	List<AgentCommission>agentCommissionList = new ArrayList<>();
                	agentCommissionList.add(agentCommissionModel);
                	
            		MyDao.addPayment(paymentList,postgresConnStr,schema_db,"payments");
            		MyDao.addPayment(leftoverList,postgresConnStr,schema_db,"leftover");
            		MyDao.addSellerCommision(sellerCommisionList,postgresConnStr,schema_db,"sellercommission");
            		MyDao.addAgentCommision(agentCommissionList,postgresConnStr,schema_db,"agentcommission");
            		Alert alert2 = Alerts.information("Payment Success", "Information");
            		
            		if(alert2.getResult()  == ButtonType.OK) {
            			String fullName  = paymentNameLbl.getText();
            	    	String currentDir = System.getProperty("user.dir");
            	    	String templateStr = ""; String outputStr = "";
            	    	 //System.out.println("Current dir using System:" + currentDir);
            	    	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            			String  no = MyDao.generate_no("payments",schema_db,postgresConnStr);
            			String ARNo = "AR-"+no;
            	    	if(schema_db == "balanga" || schema_db.equals("balanga")) {
            	    		 templateStr = currentDir + "\\template\\acknowledgeMonthlyTemplateBalanga.xlsx";
            	    		 outputStr = currentDir + "\\report\\"+timeStamp+"_Acknowledgement_Receipt_.xlsx";
            	    	 }else {
            	    		 templateStr = currentDir + "\\template\\acknowledgeMonthlyTemplateAbucay.xlsx";
            	    		 outputStr = currentDir + "\\report\\"+timeStamp+"_Acknowledgement_Receipt_.xlsx";
            	    	 }
            	    	WriteToExcel.writeAcknowledgement(fullName,templateStr,outputStr,amountToPay,paymentblockNoLbl.getText(),
            	    			paymentlotNoLbl.getText(),ARNo);
            			paymentInitialize();
            		}
            		
    			}	
    		
 	
    	}else{
    		Alert alert = Alerts.error("Please check your field", "Error");
    	}

    }
    
	@FXML
    void doSearchCustomer(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/searchCustomerScene.fxml"));
    	DialogPane customerDialogPane = loader.load();
    	searchCustomerSceneController customerController = loader.getController();
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int centerX = screenSize.width/2;
    	int centerY = screenSize.height/2;
    	
    	ObservableList<Customer> list = FXCollections.observableArrayList();
    	String searchtxt = paymentCustIdTxt.getText();
    	
    	list = MyDao.searchCustomerList(searchtxt,postgresConnStr,schema_db,"customer");
    	loadCustomerTbl(list,customerController);
    	
    	Dialog<ButtonType> dialog = new Dialog<>();
    	dialog.setDialogPane(customerDialogPane);
    	dialog.setX(centerX-130);
    	dialog.setY(centerY-270);
    	dialog.setTitle("Select Customer");
    	
    	Optional<ButtonType> clickButton = dialog.showAndWait();
    	if(clickButton.get() == ButtonType.OK) {
    		//System.out.println(customerController.selectedCustomer.get(0).getCustomerNo());
    		
    		paymentCustNoLbl.setText(customerController.selectedCustomer.get(0).getCustNo());
    		paymentNameLbl.setText(customerController.selectedCustomer.get(0).getLastName()
    				+ ", " + customerController.selectedCustomer.get(0).getFirstName()
    				+ " " + customerController.selectedCustomer.get(0).getMiddleName());
    		
    		List<Contract> contractList = new ArrayList<>();
    		contractList = MyDao.getContractList(paymentCustNoLbl.getText(),postgresConnStr,schema_db,"contract");
    		paymentContNoLbl.setText(contractList.get(0).getContractNo());
    		paymentblockNoLbl.setText(contractList.get(0).getBlockNo());
    		paymentlotNoLbl.setText(contractList.get(0).getLotNo());
    		paymentlotAreaLbl.setText(contractList.get(0).getLotArea());
    		paymentPriceLbl.setText(contractList.get(0).getPrice());
    		paymentTPriceLbl.setText(contractList.get(0).getTotalPrice());
    		paymentpaymentLbl.setText(contractList.get(0).getPayment());
    		paymentbalanceLbl.setText(contractList.get(0).getBalance());
    		sellerCodeLbl.setText(contractList.get(0).getSellerNo());
    		agentCodeLbl.setText(contractList.get(0).getAgentNo());
    	
    		ObservableList<CustomerPayment> paymentList = FXCollections.observableArrayList();
    		paymentList = MyDao.getPaymentList(paymentCustNoLbl.getText(),postgresConnStr,schema_db,"payments");
    		paymentCol.setCellValueFactory(new PropertyValueFactory<CustomerPayment,String>("amount"));
    		dateCol.setCellValueFactory(new PropertyValueFactory<CustomerPayment,String>("datePay"));
    		priceTbl.setItems(paymentList);
    	
    		String color_code = MyDao.selectColor(paymentblockNoLbl.getText(),paymentlotNoLbl.getText(),postgresConnStr,schema_db,"lot");
    		
    		if(color_code.equals("green")) {
    			customerMultiplier = CUSTOMER_MULTIPLIER_GREEN;
    		
    		}else if (color_code.equals("blue")) {
    			customerMultiplier = CUSTOMER_MULTIPLIER_BLUE;
    		
    		}else {
    			customerMultiplier = CUSTOMER_MULTIPLIER_YELLOW;	
    		}
    		
    		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    		lastPayDate = LocalDate.parse(paymentList.get(paymentList.size()-1).getDatePay(),format);
//    		ObservableList<String> dueDateList = FXCollections.observableArrayList();
//    		for(int i=1;i<=24;i++) {
//    			dueDateList.add(lastPayDate.plusMonths(i).toString());
//    		}
//    		comboDueDate.setItems(dueDateList);
    		isSelected = false;
    		selectDueInitialize();
    	}
    }
	
	@FXML
	void onSelecDuepayment(ActionEvent event) {
		
		if(!isSelected) {
			DecimalFormat formatter = new DecimalFormat("#,###");
			LocalDate today = LocalDate.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate selectedDueDate = null;
			//System.out.println(priceTbl.getItems().get(0).getDatePay());
			//LocalDate startDate =  LocalDate.parse(priceTbl.getItems().get(0).getDatePay(),format);
			if(dueDatePicker.getValue() != null) {
				selectedDueDate = dueDatePicker.getValue();
		   		double monthlyInstallment = Double.parseDouble(paymentTPriceLbl.getText().replace(",", ""))* customerMultiplier;
				int roundedOffmonthlyInstallment = (int) Math.rint(monthlyInstallment);
				//int lengthOfMonth = today.compareTo(lastPayDate);
				//System.out.println(today + " " + lastPayDate + " " + lengthOfMonth);
				int leftoverdue = MyDao.getLeftOverAmountDue(postgresConnStr,schema_db,"leftover",paymentCustNoLbl.getText());
				leftoveramountLbl.setText(formatter.format(leftoverdue));
				LocalDate convertedDate = selectedDueDate;
		    	convertedDate = convertedDate.withDayOfMonth(
		    	                                convertedDate.getMonth().length(convertedDate.isLeapYear()));
		    	int lengthOfDays = 0;
		    	System.out.println(convertedDate);
				lengthOfMonth = (int) ChronoUnit.MONTHS.between(lastPayDate,convertedDate);
				if(lengthOfMonth ==0) {
					lengthOfDays = (int) ChronoUnit.DAYS.between(lastPayDate,selectedDueDate);
					System.out.println("days = "+lengthOfDays);
					if(lengthOfDays >=1) {
						lengthOfMonth = 1;
					}
				}
				LocalDate startDate =  LocalDate.parse(priceTbl.getItems().get(0).getDatePay(),format);
				LocalDate actualDueDate = startDate.plusMonths((int) ChronoUnit.MONTHS.between(startDate,convertedDate));
				actuaDueLbl.setText(actualDueDate.toString());
		    	if(priceTbl.getItems().size() == 1 && lengthOfMonth > 1) {
		    		//lengthOfMonth -=1;
		    		System.out.println("lengthOfMonth: "+lengthOfMonth);
		    	}
				monthlyinstallmentLbl.setText(formatter.format(roundedOffmonthlyInstallment));
				int rowCount = priceTbl.getItems().size();
				int monthlyDue = (roundedOffmonthlyInstallment * lengthOfMonth)+leftoverdue;
				if(lengthOfDays < 0) {
					monthlyDue = 0;
				}
				//System.out.println(rowCount);
			
				if(lengthOfMonth>1) {
					ovedueLbl.setText("Overdue ("+lengthOfMonth+") ");
					overdueAmountLbl.setText(formatter.format(monthlyDue));
				}else {
					ovedueLbl.setText("Overdue (0) ");
					overdueAmountLbl.setText(formatter.format(0));
				}
				
				if(rowCount>1) {
					paymentDueLbl.setText(formatter.format(monthlyDue));
				}else {
					monthlyDue -= Double.parseDouble(paymentpaymentLbl.getText());
					paymentDueLbl.setText(formatter.format(monthlyDue));
				}
				
				if(monthlyDue <= 0) {
					//makePayBtn.setDisable(true);
				}else {
					makePayBtn.setDisable(false);
				}
			}
		
		}
	}
    
    @FXML
    void doSubmit(ActionEvent event) {
    	
    	String bDay = "", blNo ="", ltNo = "", dtStart = "", dtEnd = "";
    	
    	if(bday.getValue() != null) {
    		bDay = bday.getValue().toString();    		
    	}
    	
    	if(blk_no.getValue() != null) {
    		blNo = blk_no.getValue().toString();    		
    	}
    	
    	if(lot_no.getValue() != null) {
    		ltNo = lot_no.getValue().toString();    		
    	}
    	
    	if(date_start.getValue() != null) {
    		dtStart = date_start.getValue().toString();    		
    	}
    	
    	if(date_end.getValue() != null) {
    		dtEnd = date_end.getValue().toString();    		
    	}
    	
    	
    	try {
        	Customer customerModel = new Customer(cust_no_txt.getText(), lname_txt.getText(), fname_txt.getText(), 
        			mname_txt.getText(), bDay, phone_txt.getText(), mail_txt.getText(),"");
        	
        	Contract contractModel = new Contract(cont_no_txt.getText(), cust_no_txt.getText(), blNo, 
        			ltNo, lot_area_txt.getText(), duration_txt.getText(), price_txt.getText(), 
        			total_price_txt.getText(), payment_txt.getText(),balance_txt.getText(), dtStart, 
        			dtEnd, seller_no_txt.getText(), agent_no_txt.getText(),"reserved");
        	
        	List<Customer>customerList = new ArrayList<>();
        	customerList.add(customerModel);
        	
        	List<Contract>contractList = new ArrayList<>();
        	contractList.add(contractModel);
        	
        	boolean customerBad = Validate.checkCustomer(customerList);
        	boolean contractBad = Validate.checkContract(contractList);;
        	
        	if(customerBad || contractBad) {
        		Alert alert = Alerts.error("Please fill empty field(s)", "Error");
        	}else {
        		Alert alert = Alerts.confirmation("Do you want to submit this?", "Confirmation");
        		if(alert.getResult() == ButtonType.YES) {
        			
        			CustomerPayment paymentModel = new CustomerPayment(cust_no_txt.getText(), 
        					cont_no_txt.getText(), payment_txt.getText(), dtStart);
                	List<CustomerPayment>paymentList = new ArrayList<>();
                	paymentList.add(paymentModel);
                	
                	String category = MyDao.selectCategory(agent_no_txt.getText(),postgresConnStr,schema_db,"Agent");
                	double sellerMultiplier = 0.00, agentMultiplier = 0.00;
                	if(category.equals("A") || category == "A"){
                		agentMultiplier = (Double.parseDouble(lot_area_txt.getText())*1000)/60;
                	}else {
                		agentMultiplier = (Double.parseDouble(total_price_txt.getText())*0.05)/60;
                	}

                	int roundedOffsellerCommission = (int) Math.rint(sellerMultiplier);
                	int roundedOffagentCommission = (int) Math.rint(agentMultiplier);
                	
                	int agentCommission = roundedOffagentCommission;
                	int sellerCommission = Integer.parseInt(payment_txt.getText()) - agentCommission;
      
                	
                	if(Integer.parseInt(payment_txt.getText()) <= agentCommission) {
                		Alert alert2 = Alerts.error("Reservation fee should be more than "+agentCommission, "Error");
                	}else {
                    	SellerCommission sellerCommissionModel = new SellerCommission(cust_no_txt.getText(), 
                    			seller_no_txt.getText(), Integer.toString(sellerCommission), dtStart);
                    	
                    	AgentCommission agentCommissionModel = new AgentCommission(cust_no_txt.getText(), 
                    			agent_no_txt.getText(), Integer.toString(agentCommission), dtStart);
                    	
                    	List<SellerCommission>sellerCommisionList = new ArrayList<>();
                    	sellerCommisionList.add(sellerCommissionModel);
                    	
                    	List<AgentCommission>agentCommissionList = new ArrayList<>();
                    	agentCommissionList.add(agentCommissionModel);
           
                		MyDao.addCustomer(customerList,postgresConnStr,schema_db,"customer");
                		MyDao.addContract(contractList,postgresConnStr,schema_db,"contract");
                		MyDao.addPayment(paymentList,postgresConnStr,schema_db,"payments");
                		MyDao.addSellerCommision(sellerCommisionList,postgresConnStr,schema_db,"sellercommission");
                		MyDao.addAgentCommision(agentCommissionList,postgresConnStr,schema_db,"agentcommission");
                		MyDao.updateLotAvailability(postgresConnStr,schema_db,"lot",blNo,ltNo,false);
                		Alert alert3 = Alerts.information("Contract Added Successfully!", "Information");
            			if(alert3.getResult() == ButtonType.OK) {
                			String fullName  = fname_txt.getText() + " " + mname_txt.getText() + " " + lname_txt.getText();
                	    	String currentDir = System.getProperty("user.dir");
                	    	String templateStr = ""; String outputStr = "";
                	    	 //System.out.println("Current dir using System:" + currentDir);
                	    	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                			String  no = MyDao.generate_no("payments",schema_db,postgresConnStr);
                			String ARNo = "AR-"+no;
                			
                	    	if(schema_db == "balanga" || schema_db.equals("balanga")) {
                	    		 templateStr = currentDir + "\\template\\reservationTemplateBalanga.xlsx";
                	    		 outputStr = currentDir + "\\report\\"+timeStamp+"_Rervation_Agreement_.xlsx";
                	    	 }else {
                	    		 templateStr = currentDir + "\\template\\reservationTemplateAbucay.xlsx";
                	    		 outputStr = currentDir + "\\report\\"+timeStamp+"_Rervation_Agreement_.xlsx";
                	    	 }
                			WriteToExcel.writeReservation(fullName,templateStr,outputStr);
                			
                	    	if(schema_db == "balanga" || schema_db.equals("balanga")) {
                	    		templateStr = currentDir + "\\template\\acknowledgeReserveTemplateBalanga.xlsx";
               	    		 	outputStr = currentDir + "\\report\\"+timeStamp+"_Acknowledgement_Receipt_.xlsx";
               	    	 	}else {
                  	    		templateStr = currentDir + "\\template\\acknowledgeReserveTemplateAbucay.xlsx";
                   	    		outputStr = currentDir + "\\report\\"+timeStamp+"_Acknowledgement_Receipt_.xlsx";
               	    	 	}
                	    	WriteToExcel.writeAcknowledgement(fullName,templateStr,outputStr,
                	    			Integer.parseInt(payment_txt.getText()),blNo,ltNo,ARNo);
                			reserveInitialize();
            			}

                	}
                	

        		}

        	}
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		Alert alert = Alerts.error("Please fill empty field(s)", "Error");
    	}
    	

    }
    
    @FXML
    void doSelectLot(ActionEvent event) {
  
    	lot_no.setValue(null);
    	String blkNo = blk_no.getValue();
		ObservableList<String> list = FXCollections.observableArrayList();
		list = MyDao.generate_item2("lot",schema_db,blkNo,postgresConnStr);
		//System.out.println(list);
		//lot_no.setPromptText("Please select Lot No. here..");
		lot_no.setItems(list);

    }
    
    @FXML
    void doSelectDate(ActionEvent event) {
    	
    	if(date_start.getValue() != null) {
        	try {
        		if(date_start.getValue().isAfter(LocalDate.now())) {
        			Alert alert = Alerts.error("Not a Valid Date!!", "Information");
        		}else {
        			date_end.setValue(date_start.getValue()
        					.plusMonths(Integer.parseInt(duration_txt.getText())));
        		}
        		
        	}catch(Exception e) {
        		e.printStackTrace();
        		Alert alert = Alerts.error(e.toString(), "Error!! ");
        	}
    	}
    }
    
    @FXML
    void doCheckAvailibity(ActionEvent event) {
    	String blkNo = blk_no.getValue();
    	String lotNo = lot_no.getValue();
    	boolean availability = MyDao.check_availability("lot",schema_db,blkNo,lotNo,postgresConnStr);
    	List<String> list = new ArrayList<>();
    	list = MyDao.setPrice("lot",schema_db,blkNo,lotNo,postgresConnStr);
    	DecimalFormat formatter = new DecimalFormat("#,###");
    	//System.out.println("availability: " + availability + " list: " + list);
    	if(availability && !list.isEmpty()) {
    		lot_area_txt.setText(list.get(0));
    		price_txt.setText(formatter.format(Integer.parseInt(list.get(1))));
    		total_price_txt.setText(formatter.format(Integer.parseInt(list.get(2).replace(",", ""))));
    		
    	}else if(!availability && !list.isEmpty()) {
    		Alert alert = Alerts.error("This Block No. "+blkNo + " and Lot No. " +lotNo  + " is not available!!", "Information");
    		if(alert.getResult() == ButtonType.OK) {
        		lot_area_txt.clear();
        		price_txt.clear();
        		total_price_txt.clear();
    		}
    		
    	}
    }
    
    @FXML
    void computeBalance(KeyEvent event) {
    	
    	if(!event.getCode().equals(KeyCode.BACK_SPACE) && !event.getCode().equals(KeyCode.DELETE)
    			&& !event.getCode().isDigitKey() && !event.getCode().isArrowKey()
    			&& !event.getCode().equals(KeyCode.ENTER) && !event.getCode().equals(KeyCode.TAB)) {
    		
    		Alert alert = Alerts.error("Invalid Input!!", "Error");
    		
    		if(alert.getResult() == ButtonType.OK) {
    			payment_txt.clear();
    		}  
    	}
    	
    	try {
        	if(payment_txt.getText() != "" && event.getCode().isDigitKey() ){
        		int total_price = 0, payment, balance;
            	DecimalFormat formatter = new DecimalFormat("#,###");
            	total_price = Integer.parseInt(total_price_txt.getText().replace(",", ""));
            	payment = Integer.parseInt(payment_txt.getText());
            	balance  = total_price - payment;
            	balance_txt.setText(formatter.format(balance));
        	}else {
        		balance_txt.clear();
        	}
    		
    	}catch(Exception e) {
    		e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error");
    	}
    }
    	
	
    private void reserveInitialize() {
		// TODO Auto-generated method stub
		cust_no_txt.setEditable(false);
		cont_no_txt.setEditable(false);
		lot_area_txt.setEditable(false);
		price_txt.setEditable(false);
		balance_txt.setEditable(false);
		
		lname_txt.clear(); fname_txt.clear(); mname_txt.clear(); bday.getEditor().clear(); 
		phone_txt.clear(); mail_txt.clear();
		
		lot_area_txt.clear(); price_txt.clear(); payment_txt.clear();
		total_price_txt.clear(); balance_txt.clear(); date_start.setValue(null);
		agent_no_txt.clear(); seller_no_txt.clear();

		String  no = MyDao.generate_no("customer",schema_db,postgresConnStr);
		cust_no_txt.setText("cust-"+no);
		
		no = MyDao.generate_no("contract",schema_db,postgresConnStr);
		cont_no_txt.setText("cont-"+no);
		
		ObservableList<String> list = FXCollections.observableArrayList();
		list = MyDao.generate_item("lot",schema_db,postgresConnStr);
		//System.out.println(list);
		blk_no.setPromptText("Please select Block No. here..");
		blk_no.setItems(list);
		
	}
    

	private void paymentInitialize() {
		
		paymentCustNoLbl.setText("Cust-00000");
		paymentContNoLbl.setText("Cont-00000");
		paymentNameLbl.setText("Name");
		paymentblockNoLbl.setText("0");
		paymentlotNoLbl.setText("0");
		paymentlotAreaLbl.setText("0");
		paymentPriceLbl.setText("0");
		paymentTPriceLbl.setText("0");
		paymentpaymentLbl.setText("0");
		paymentbalanceLbl.setText("0");
		priceTbl.setItems(null);
		lengthOfMonth = 0;
		selectDueInitialize();
    }
	
	private void selectDueInitialize() {
		dueDatePicker.getEditor().clear();
		dueDatePicker.setValue(null);
		monthlyinstallmentLbl.setText("0");
		leftoveramountLbl.setText("0");
		ovedueLbl.setText("Overdue");
		overdueAmountLbl.setText("0");
		paymentDueLbl.setText("0");
		actuaDueLbl.setText("yyyy-mm-dd");
		makePayBtn.setDisable(true);
		amountToPay_txt.clear();
	}
    
    public void toSaveCredentials() {
    	PostgresCredentials credentials = new PostgresCredentials()
    			.setUsername(user_textfield.getText())
    			.setPassword(pass_passfield.getText())
    			.setHost(host_textfield.getText())
    			.setPort(port_textfield.getText())
    			.setDatabase(db_textfield.getText())
    			.setSchemas(schema_db);
    	saveCredentials(credentials);
    }

	private void saveCredentials(PostgresCredentials credentials) {
		// TODO Auto-generated method stub
		try (
			FileOutputStream fos = new FileOutputStream(FILE_NAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
		) {
			oos.writeObject(credentials);
			oos.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error");
		}
		
	}
	
	private PostgresCredentials loadCredentials() {
		// TODO Auto-generated method stub
    	if(Files.exists(Paths.get(FILE_NAME))) {
    		try( ObjectInputStream dis = new ObjectInputStream(new FileInputStream(FILE_NAME))){
    			PostgresCredentials credentials = (PostgresCredentials) dis.readObject();
    			dis.close();
    			return credentials;
    		}catch(Exception e) {
    			e.printStackTrace();
    			Alert alert = Alerts.error(e.toString(), "Error!!");
    		}
    	}
		return null;
	}
	
    private void setCredentials(PostgresCredentials credentials) {
		// TODO Auto-generated method stub
		user_textfield.setText(credentials.getUsername());
		pass_passfield.setText(credentials.getPassword());
		host_textfield.setText(credentials.getHost());
		port_textfield.setText(credentials.getPort());
		db_textfield.setText(credentials.getDatabase());
		if(credentials.getSchemas().equals("balanga")) {
			bal_Rb.setSelected(true);
		}else {
			Abu_Rb.setSelected(true);
		}
		
	}
    
    @FXML
    void doSearchEnter(KeyEvent event) throws IOException {
    	
    	if(event.getCode().equals(KeyCode.ENTER)) {
    		doSearchCustomer(null);
    	}

    }
    
    @FXML
    void doSearchAgentEnter(KeyEvent event) throws IOException {
    	
    	if(event.getCode().equals(KeyCode.ENTER)) {
    		doSearchAgent(null);
    	}

    }
    
    @FXML
    void doSearchSellerEnter(KeyEvent event) throws IOException {
    	
    	if(event.getCode().equals(KeyCode.ENTER)) {
    		doSearchSeller(null);
    	}

    }
    
    @FXML
    void doSearchAgent(ActionEvent event) throws IOException{
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/searchSellerAgentScene.fxml"));
    	DialogPane customerDialogPane = loader.load();
    	searchSellerAgentSceneController<Agent> agentController = loader.getController();
    	
    	ObservableList<Agent> list = FXCollections.observableArrayList();
    	String searchtxt = agent_no_txt.getText();
    	list = MyDao.searchAgentList(searchtxt,postgresConnStr,schema_db,"Agent");
    	loadAgentTbl(list,agentController);
    	
       	Dialog<ButtonType> dialog = new Dialog<>();
    	dialog.setDialogPane(customerDialogPane);
    	dialog.setTitle("Select Agent");
    	
    	Optional<ButtonType> clickButton = dialog.showAndWait();
    	if(clickButton.get() == ButtonType.OK) {
    		agent_no_txt.setText(agentController.selectedItem.get(0).getAgentNumber());
    	}
    }
    

	@FXML
    void doSearchSeller(ActionEvent event) throws IOException{
		
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/searchSellerAgentScene.fxml"));
    	DialogPane customerDialogPane = loader.load();
    	searchSellerAgentSceneController<Seller> sellerController = loader.getController();
    	
    	ObservableList<Seller> list = FXCollections.observableArrayList();
    	String searchtxt = seller_no_txt.getText();
    	list = MyDao.searchSellerList(searchtxt,postgresConnStr,schema_db,"seller");
    	loadSellerTbl(list,sellerController);
    	
       	Dialog<ButtonType> dialog = new Dialog<>();
    	dialog.setDialogPane(customerDialogPane);
    	dialog.setTitle("Select Customer");
    	
    	Optional<ButtonType> clickButton = dialog.showAndWait();
    	if(clickButton.get() == ButtonType.OK) {
    		seller_no_txt.setText(sellerController.selectedItem.get(0).getSellerNumber());
    	}
    	
    	
    }
   

	
    private void loadSellerTbl(ObservableList<Seller> list, searchSellerAgentSceneController<Seller> sellerController) {
		// TODO Auto-generated method stub
    	sellerController.IDNoCol.setCellValueFactory(new PropertyValueFactory<Seller,String>("sellerNumber"));
    	sellerController.LastNameCol.setCellValueFactory(new PropertyValueFactory<Seller,String>("sellerLastName"));
    	sellerController.FirstNameCol.setCellValueFactory(new PropertyValueFactory<Seller,String>("sellerFirstName"));
    	sellerController.MiddleNameCol.setCellValueFactory(new PropertyValueFactory<Seller,String>("sellerMiddleName"));
    	sellerController.SellerAgentTable.setItems(list);
		
	}
    
    static void loadAgentTbl(ObservableList<Agent> list, searchSellerAgentSceneController<Agent> agentController) {
		// TODO Auto-generated method stub
    	agentController.IDNoCol.setCellValueFactory(new PropertyValueFactory<Agent,String>("agentNumber"));
    	agentController.LastNameCol.setCellValueFactory(new PropertyValueFactory<Agent,String>("agentLastName"));
    	agentController.FirstNameCol.setCellValueFactory(new PropertyValueFactory<Agent,String>("agentFirstName"));
    	agentController.MiddleNameCol.setCellValueFactory(new PropertyValueFactory<Agent,String>("agentMiddleName"));
    	agentController.SellerAgentTable.setItems(list);
		
	}

	private void loadCustomerTbl(ObservableList<Customer> list, searchCustomerSceneController customerController) {
		// TODO Auto-generated method stub
		customerController.custNoCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("CustNo"));
		customerController.lnameCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("lastName"));
		customerController.fnameCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("firstName"));
		customerController.mnameCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("middleName"));
		customerController.CustomerTbl.setItems(list);
		
	}
	
	
	private void loadViewContactTbl(List<SummaryContract> contractListSummary) {
		
		ObservableList<SummaryContract>list = FXCollections.observableArrayList();
		list.addAll(contractListSummary);
		viewCustIdCol.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summarycustomerNo"));
		viewNameCol.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summarycustomerName"));
		viewBlkNoCol.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summaryblockNo"));
		viewLotNoCol.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summarylotNo"));
		viewLotAreaCol.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summarylotArea"));
		viewDurationCol.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summaryduration"));
		viewPriceCol.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summaryprice"));
		viewTPriceCol.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summarytotalPrice"));
		viewPaymentCol.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summarypayment"));
		viewBalanceCol.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summarybalance"));
		viewDateStartCol.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summarydateStart"));
		viewDateEndCol.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summarydateEnd"));
		viewSellerName.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summarysellerName"));
		viewSellerComm.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summarysellerComm"));
		viewAgentName.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summaryagentName"));
		//viewAgentComm.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summaryagentComm"));
		viewLastPayment.setCellValueFactory(new PropertyValueFactory<SummaryContract,String>("summaryLastPayment"));
		ViewContractTbl.setItems(list);
		
	}
	
    @FXML
    void showClientSummaryonDue(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/SummaryClientonDueScene.fxml"));
    	Parent root = (Parent) loader.load();
    	Stage stage = new Stage();
    	SummaryClientonDueController clientSummary = loader.getController();
    	
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int centerX = screenSize.width/2;
    	int centerY = screenSize.height/2;
    	stage.setScene(new Scene(root));
    	stage.setResizable(false);
    	stage.setX(centerX-300);
    	stage.setY(centerY-350);
    	stage.setTitle("CLIENTS SUMMARY ON DUE");
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();
    }
	
    @FXML
    void showAgentCommSummary(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/SummaryAgentCommisionScene.fxml"));
    	Parent root = (Parent) loader.load();
    	Stage stage = new Stage();
    	SummaryAgentCommisionSceneController summary = loader.getController();
    	ObservableList<String>monthList = FXCollections.observableArrayList();
    	monthList.add("01: January");   monthList.add("02: February"); monthList.add("03: March"); 	  monthList.add("04: April");
    	monthList.add("05: May");	    monthList.add("06: June"); 	   monthList.add("07: July"); 	  monthList.add("08: August");
    	monthList.add("09: September"); monthList.add("10: October");  monthList.add("11: November"); monthList.add("12: December");
    	summary.monthCombo.setItems(monthList);
    	summary.monthCombo2.setItems(monthList);
    	summary.myTabs.getSelectionModel().select(0);
    	
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int centerX = screenSize.width/2;
    	int centerY = screenSize.height/2;
    	stage.setScene(new Scene(root));
    	stage.setResizable(false);
    	stage.setX(centerX-300);
    	stage.setY(centerY-350);
    	stage.setTitle("AGENT's COMMISSION SUMMARY");
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();
    }
	
    @FXML
    void showAddPane(ActionEvent event) {
    	addPane.toFront();
    }
    
    @FXML
    void showEditLotPane(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/editLotScene.fxml"));
    	Parent root = (Parent) loader.load();
    	Stage stage = new Stage();
    	editLotSceneController editLot = loader.getController();
    	
    	editLot.editLotCode.setText(selectedLot.get(0).getLotCode());
    	editLot.editBlockNoTxt.setText(selectedLot.get(0).getBlockNum());
    	editLot.editLotNoTxt.setText(selectedLot.get(0).getLotNum());
    	editLot.editLotAreaTxt.setText(selectedLot.get(0).getLotAreasqm());
    	editLot.editPriceTxt.setText(selectedLot.get(0).getPricesqm());
    	editLot.editTPriceTxt.setText(selectedLot.get(0).getTotalPricesqm());
    	editLot.editavailTxt.setText(String.valueOf(selectedLot.get(0).getLotAvail()));
    	
    	ObservableList<String> comboItem = FXCollections.observableArrayList();
    	List<String> item = new ArrayList<>();
    	item.add("green");
    	item.add("blue");
    	item.add("yellow");
    	comboItem.addAll(item);
    	editLot.editColorCombo.setItems(comboItem);
    	editLot.editColorCombo.getSelectionModel().select(selectedLot.get(0).getColorCode());
    	
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int centerX = screenSize.width/2;
    	int centerY = screenSize.height/2;
    	stage.setScene(new Scene(root));
    	stage.setResizable(false);
    	stage.setX(centerX-115);
    	stage.setY(centerY-300);
    	stage.setTitle("EDIT LOT");
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();
    	showLot(null);
    }
    
	@FXML
    void showLotPane(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/addLotScene.fxml"));
    	Parent root = (Parent) loader.load();
    	Stage stage = new Stage();
    	addLotSceneController addlot = loader.getController();
    	
    	ObservableList<Lot> list = FXCollections.observableArrayList();
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int centerX = screenSize.width/2;
    	int centerY = screenSize.height/2;
    	String no = MyDao.generate_no("lot", schema_db, postgresConnStr);
    	addlot.addLotCode.setText("lot-"+no);
		ObservableList<String> comboItem = FXCollections.observableArrayList();
    	List<String> item = new ArrayList<>();
    	item.add("green");
    	item.add("blue");
    	item.add("yellow");
    	comboItem.addAll(item);
    	addlot.addColorCombo.setItems(comboItem);
    	
    	stage.setScene(new Scene(root));
    	stage.setResizable(false);
    	stage.setX(centerX-115);
    	stage.setY(centerY-300);
    	stage.setTitle("ADD LOT");
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();
    	
    }
    
    @FXML
    void showAgentPane(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/addAgentScene.fxml"));
    	Parent root = (Parent) loader.load();
    	Stage stage = new Stage();
    	addAgentSceneController addagent = loader.getController();
    	
    	//ObservableList<Lot> list = FXCollections.observableArrayList();
		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("A");
		list.add("B");
		//System.out.println(list);
		addagent.agentcategory.setPromptText("Please select Category here..");
		addagent.agentcategory.setItems(list);
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int centerX = screenSize.width/2;
    	int centerY = screenSize.height/2;
    	String no = MyDao.generate_no("agent", schema_db, postgresConnStr);
    	System.out.println(no.substring(2));
    	StringBuilder agentNo = new StringBuilder();
    	agentNo.append("agt-"+no.substring(2));
    	addagent.agentNoTxt.setText(agentNo.toString());
    	stage.setScene(new Scene(root));
    	stage.setResizable(false);
    	stage.setX(centerX-210);
    	stage.setY(centerY-210);
    	stage.setTitle("ADD AGENT");
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();
    	
    }
    
    @FXML
    void showSellerPane(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/addSellerScene.fxml"));
    	Parent root = (Parent) loader.load();
    	Stage stage = new Stage();
    	addSellerSceneController addseller = loader.getController();
    	
    	//ObservableList<Lot> list = FXCollections.observableArrayList();
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int centerX = screenSize.width/2;
    	int centerY = screenSize.height/2;
    	String no = MyDao.generate_no("seller", schema_db, postgresConnStr);
    	//System.out.println(no.substring(2));
    	StringBuilder sellerNo = new StringBuilder();
    	sellerNo.append("sell-"+no.substring(2));
    	addseller.sellerNoTxt.setText(sellerNo.toString());
    	stage.setScene(new Scene(root));
    	stage.setResizable(false);
    	stage.setX(centerX-210);
    	stage.setY(centerY-190);
    	stage.setTitle("ADD SELLER");
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();
    	
    }
    
    @FXML
    void showActualAgentComm(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/agentCommissionScene.fxml"));
    	Parent root = (Parent) loader.load();
    	Stage stage = new Stage();
    	agentCommissionSceneController agentComm = loader.getController();
    	
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int centerX = screenSize.width/2;
    	int centerY = screenSize.height/2;
    	String no = MyDao.generate_no("actual_agentcommission", schema_db, postgresConnStr);
    	//System.out.println(no.substring(2));
    	StringBuilder commNo = new StringBuilder();
    	commNo.append("Comm-"+no);
    	agentComm.agentCommNo.setText(commNo.toString());
    	agentComm.agentCommDate.setValue(LocalDate.now());
    	
    	stage.setScene(new Scene(root));
    	stage.setResizable(false);
    	stage.setX(centerX-110);
    	stage.setY(centerY-300);
    	stage.setTitle("AGENT COMMISSION");
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();
    }
    
    @FXML
    void showExpense(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/addexpenseScene.fxml"));
    	Parent root = (Parent) loader.load();
    	Stage stage = new Stage();
    	addExpenseSceneController addexpense = loader.getController();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    	
		ObservableList<String> listBal = FXCollections.observableArrayList();
		listBal.add("Dorin"); listBal.add("Commission");
		listBal.add("DBP Payment"); listBal.add("Engr Bjay Silva");
		listBal.add("Engr Randie Villarante"); listBal.add("Others");
		
		ObservableList<String> listAbu = FXCollections.observableArrayList();
		listAbu.add("Alcazar"); listAbu.add("Commission");
		listAbu.add("Others");
		
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int centerX = screenSize.width/2;
    	int centerY = screenSize.height/2;
    	String no = MyDao.generate_no("expenses", schema_db, postgresConnStr);
    	//System.out.println(no.substring(2));
    	StringBuilder expNo = new StringBuilder();
    	expNo.append("exp-"+no);
    	addexpense.expenseNo.setText(expNo.toString());
    	addexpense.expensedate.setValue(LocalDate.now());
    	
    	if(schema_db.equals("balanga")) {
    		addexpense.expenseCategory.setItems(listBal);
    	}else {
    		addexpense.expenseCategory.setItems(listAbu);
    	}
    	
    	stage.setScene(new Scene(root));
    	stage.setResizable(false);
    	stage.setX(centerX-110);
    	stage.setY(centerY-300);
    	stage.setTitle("ADD EXPENSES");
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();
    }
	
    @FXML
    void showData(ActionEvent event) {
    	
    	showCustomer();
    	dataPane.toFront();
    }
    
    @FXML
    void  cancelReservation(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/cancelReserveScene.fxml"));
    	Parent root = (Parent) loader.load();
    	Stage stage = new Stage();
    	cancelReserveSceneController cancel = loader.getController();
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int centerX = screenSize.width/2;
    	int centerY = screenSize.height/2;
    	
    	cancel.CustIDText.setText(selectedContract.get(0).getSummarycustomerNo());
    	List<Contract> contractList = new ArrayList<>();
		contractList = MyDao.getContractList(selectedContract.get(0).getSummarycustomerNo(),postgresConnStr,schema_db,"contract");
		cancel.ContIDText.setText(contractList.get(0).getContractNo());
		cancel.BlkNoText.setText(contractList.get(0).getBlockNo());
		cancel.LotNoText.setText(contractList.get(0).getLotNo());
		cancel.LotAreaText.setText(contractList.get(0).getLotArea());
		cancel.DateStartText.setText(contractList.get(0).getDateStart());
		cancel.DateEndText.setText(contractList.get(0).getDateEnd());
		cancel.DurationText.setText(contractList.get(0).getDuration());
		cancel.PriceText.setText(contractList.get(0).getPrice());
		cancel.TpriceText.setText(contractList.get(0).getTotalPrice());
		cancel.PaymentText.setText(contractList.get(0).getPayment());
		cancel.BalanceText.setText(contractList.get(0).getBalance());
		cancel.AgentNoText.setText(contractList.get(0).getAgentNo());
		cancel.SellerNoText.setText(contractList.get(0).getSellerNo());
    	//System.out.println(no.substring(2));
    	stage.setScene(new Scene(root));
    	stage.setResizable(false);
    	stage.setX(centerX-230);
    	stage.setY(centerY-350);
    	stage.setTitle("CANCEL RESRVATION");
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.showAndWait();
    	showContractList(null);
    }
    
    @FXML
    void onSelectContract(MouseEvent event) {
    	cancelReserve.setDisable(false);
    	editInfoBtn.setDisable(false);
    	selectedContract = ViewContractTbl.getSelectionModel().getSelectedItems();
    }
    
    @FXML
    void onSelectPayment(MouseEvent event) {
    	editpayBtn.setDisable(false);
    }
    
    @FXML
    void showeditPayment (ActionEvent event) throws IOException {
    	if(historypaymentTbl.getSelectionModel().getSelectedItem()!=null) {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/editPaymentScene.fxml"));
        	Parent root = (Parent) loader.load();
        	Stage stage = new Stage();
        	editPaymentSceneController editpayment = loader.getController();
        	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        	int centerX = screenSize.width/2;
        	int centerY = screenSize.height/2;
        	
        	editpayment.editPaymentCustID.setText(historypaymentTbl.getSelectionModel().getSelectedItem().getCustomerNo());
        	editpayment.editPaymentAmount.setText(historypaymentTbl.getSelectionModel().getSelectedItem().getPaymentAmount());
        	editpayment.editPaymentDate.setText(historypaymentTbl.getSelectionModel().getSelectedItem().getPaymentDate());
        	editpayment.TCPamount.setText(historyTCP.getText());
        	editpayment.oldAmount.setText(historypaymentTbl.getSelectionModel().getSelectedItem().getPaymentAmount());
        	
        	stage.setScene(new Scene(root));
        	stage.setResizable(false);
        	stage.setX(centerX-70);
        	stage.setY(centerY-290);
        	stage.setTitle("EDIT PAYMENT");
        	stage.initModality(Modality.APPLICATION_MODAL);
        	stage.showAndWait();
        	paymenthistoryInitialize();
    	}
    }
    
    @FXML
    void showEditInfo(ActionEvent event) throws IOException{
    	if(ViewContractTbl.getSelectionModel().getSelectedItem()!=null) {
    	
    		List<Customer>list = new ArrayList<>();
    		list = MyDao.getCustomerInfo(postgresConnStr,schema_db,"customer",selectedContract.get(0).getSummarycustomerNo());
    		
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/editCustomerInfoScene.fxml"));
        	Parent root = (Parent) loader.load();
        	Stage stage = new Stage();
        	editCustomerInfoSceneController editCustomer = loader.getController();
        	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        	int centerX = screenSize.width/2;
        	int centerY = screenSize.height/2;
        	
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        	LocalDate date = LocalDate.parse(list.get(0).getBirthDay(), formatter);
        	
        	editCustomer.cust_no_txt.setText(list.get(0).getCustNo());
        	editCustomer.lname_txt.setText(list.get(0).getLastName());
        	editCustomer.fname_txt.setText(list.get(0).getFirstName());
        	editCustomer.mname_txt.setText(list.get(0).getMiddleName());
        	editCustomer.bday.setValue(date);
        	editCustomer.phone_txt.setText(list.get(0).getPhoneNo());
        	editCustomer.mail_txt.setText(list.get(0).getEmailAdd());
        	
           	stage.setScene(new Scene(root));
        	stage.setResizable(false);
        	stage.setX(centerX-240);
        	stage.setY(centerY-290);
        	stage.setTitle("EDIT CUSTOMER INFO");
        	stage.initModality(Modality.APPLICATION_MODAL);
        	stage.showAndWait();
        	showContractList(null);
    	}
    	
    }
    
    @FXML
    void showReportPane (ActionEvent event) {
    	paymenthistoryInitialize();
    	historyPane.toFront();
    }
    
    @FXML
    void historyClear (ActionEvent event) {
    	paymenthistoryInitialize();
    }
    
    private void paymenthistoryInitialize() {
    	historySearchContract.clear(); historyCustID.clear(); historyContID.clear(); historyName.clear(); historyBalance.clear();
    	historyBlkNo.clear(); historyLotNo.clear(); historyLotArea.clear(); historyTCP.clear(); historyPayment.clear();
    	paymentListGlobal.clear();
    	contractListGlobal.clear();
    	customerListGlobal.clear();
    	ReportSOABtn.setDisable(true);
    	editpayBtn.setDisable(true);
    	historypaymentTbl.setItems(null);
    }
    
    @FXML
    void historysearchcontractShow (ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/com/mygroup/paymentmonitoring/fxmlandothers/searchCustomerScene.fxml"));
    	DialogPane customerDialogPane = loader.load();
    	searchCustomerSceneController customerController = loader.getController();
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int centerX = screenSize.width/2;
    	int centerY = screenSize.height/2;
    	ObservableList<Customer> list = FXCollections.observableArrayList();
    	String searchtxt = historySearchContract.getText();
    	
    	list = MyDao.searchCustomerList(searchtxt,postgresConnStr,schema_db,"customer");
    	loadCustomerTbl(list,customerController);
    	
    	Dialog<ButtonType> dialog = new Dialog<>();
    	dialog.setDialogPane(customerDialogPane);
    	dialog.setX(centerX-130);
    	dialog.setY(centerY-270);
    	dialog.setTitle("Select Customer");
    	DecimalFormat formatter = new DecimalFormat("#,###");
    	
    	Optional<ButtonType> clickButton = dialog.showAndWait();
    	if(clickButton.get() == ButtonType.OK) {
    		ReportSOABtn.setDisable(false);
    		historyCustID.setText(customerController.selectedCustomer.get(0).getCustNo());
    		historyName.setText(customerController.selectedCustomer.get(0).getLastName()+", "+
    				customerController.selectedCustomer.get(0).getFirstName()+" "+customerController.selectedCustomer.get(0).getMiddleName());
    		
    		List<Contract> contractList = new ArrayList<>();
    		contractList = MyDao.getContractList(historyCustID.getText(),postgresConnStr,schema_db,"contract");
    		historyContID.setText(contractList.get(0).getContractNo());
    		historyBlkNo.setText(contractList.get(0).getBlockNo());
    		historyLotNo.setText(contractList.get(0).getLotNo());
    		historyLotArea.setText(contractList.get(0).getLotArea());
    		historyTCP.setText(contractList.get(0).getTotalPrice());
    		historyPayment.setText(formatter.format(Integer.parseInt(contractList.get(0).getPayment())));
    		historyBalance.setText(contractList.get(0).getBalance());
    		
        	paymentListGlobal.clear();
        	contractListGlobal.clear();
        	customerListGlobal.clear();
        	
        	customerListGlobal.addAll(list);
        	contractListGlobal.addAll(contractList);
        	paymentListGlobal = MyDao.getPaymentList(postgresConnStr,schema_db);
        	//System.out.println(paymentListGlobal);
        	for(PaymentHistory paymentList:paymentListGlobal) {
        		String fullName = MyDao.getCustomerName(postgresConnStr,schema_db,"customer", paymentList.getCustomerNo());
        		//System.out.println(list.getAgentComm());
        		paymentList.setCustomerName(fullName);
        	}
        	paymentListGlobal = paymentListGlobal.stream()
    				.filter(data -> data.getCustomerNo().equals(historyCustID.getText()) || data.getCustomerNo() == historyCustID.getText())
    				.collect(Collectors.toList());
    		
        	loadPaymentHistoryTbl(paymentListGlobal);
    		
    	}
    }
    
    @FXML
    void doGenerateSOA (ActionEvent event) {
      	 String currentDir = System.getProperty("user.dir");
    	 String templateStr = ""; String outputStr = "";
    	 //System.out.println("Current dir using System:" + currentDir);
    	 String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    	 
    	 if(schema_db == "balanga" || schema_db.equals("balanga")) {
    		 templateStr = currentDir + "\\template\\SOATemplateBalanga.xlsx";
    		 outputStr = currentDir + "\\report\\"+timeStamp+"_SOA.xlsx";
    	 }else {
    		 templateStr = currentDir + "\\template\\SOATemplateAbucay.xlsx";
    		 outputStr = currentDir + "\\report\\"+timeStamp+"_SOA.xlsx";
    	 }
    	 
    	 WriteToExcel.writeSOA(historyCustID.getText(),historyName.getText(),contractListGlobal,paymentListGlobal,templateStr,outputStr);
    }
    
    @FXML
    void dogenerateSummary (ActionEvent event) {
    	 String currentDir = System.getProperty("user.dir");
    	 String templateStr = ""; String outputStr = "";
    	 //System.out.println("Current dir using System:" + currentDir);
    	 String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    	 
    	 if(schema_db == "balanga" || schema_db.equals("balanga")) {
    		 templateStr = currentDir + "\\template\\summaryTemplateBalanga.xlsx";
    		 outputStr = currentDir + "\\report\\"+timeStamp+"_Summary_Report.xlsx";
    	 }else {
    		 templateStr = currentDir + "\\template\\summaryTemplateAbucay.xlsx";
    		 outputStr = currentDir + "\\report\\"+timeStamp+"_Summary_Report.xlsx";
    	 }
    	 WriteToExcel.writeSummary(ViewContractTbl.getItems(),templateStr,outputStr,tcp_textfield.getText(),payment_textfield.getText(),
    			 bal_textfield.getText());
    }
    
    @FXML
    void showContractList(ActionEvent event) {
    	contractListGlobal.clear();
    	contractListSummary.clear();
    	cancelReserve.setDisable(true);
    	editInfoBtn.setDisable(true);
    	ContractPane.toFront();
    	contractListGlobal = MyDao.getContractListAll(postgresConnStr,schema_db,"contract");
    	DecimalFormat formatter = new DecimalFormat("#,###");
    	ObservableList<String>monthList = FXCollections.observableArrayList();
    	monthList.add("01: January");   monthList.add("02: February"); monthList.add("03: March"); 	  monthList.add("04: April");
    	monthList.add("05: May");	    monthList.add("06: June"); 	   monthList.add("07: July"); 	  monthList.add("08: August");
    	monthList.add("09: September"); monthList.add("10: October");  monthList.add("11: November"); monthList.add("12: December");
    	MonthCombo.setItems(monthList);
    	
    	for(Contract list:contractListGlobal) {
    		String fullName = MyDao.getCustomerName(postgresConnStr,schema_db,"customer", list.getCustomerNo());
    		String sellerName = MyDao.getSellerName(postgresConnStr,schema_db,"seller", list.getSellerNo());
    		int sellerComm = MyDao.computeCommision(postgresConnStr, schema_db, "sellercommission", "amount", list.getCustomerNo());
    		String agentName = MyDao.getAgentName(postgresConnStr,schema_db,"agent", list.getAgentNo());
    		String lastPayment = MyDao.getLastPayment(postgresConnStr, schema_db, "payments", list.getCustomerNo());
    		
    		SummaryContract summaryModel = new SummaryContract(list.getCustomerNo(), fullName, list.getBlockNo(), 
    				list.getLotNo(), list.getLotArea(), list.getDuration(), list.getPrice(), list.getTotalPrice(), 
    				formatter.format(Integer.parseInt(list.getPayment())), list.getBalance(), list.getDateStart(), list.getDateEnd(), 
    				sellerName, formatter.format(sellerComm), agentName,lastPayment);
    		
    		contractListSummary.add(summaryModel);
    	}
    	
    	loadViewContactTbl(contractListSummary);
    	computeTotalofSummary();
    }
    
    private void computeTotalofSummary() {
    	int tcp_total=0, payment_total=0, bal_total=0;
    	DecimalFormat formatter = new DecimalFormat("#,###");
    	
    	for(SummaryContract list:ViewContractTbl.getItems()) {
    		tcp_total += Integer.parseInt(list.getSummarytotalPrice().replace(",", ""));
    		payment_total += Integer.parseInt(list.getSummarypayment().replace(",", ""));
    		bal_total += Integer.parseInt(list.getSummarybalance().replace(",", ""));
    	}
    	tcp_textfield.setText(formatter.format(tcp_total));
    	payment_textfield.setText(formatter.format(payment_total));
    	bal_textfield.setText(formatter.format(bal_total));
    }
    
    @FXML
    void FilterSummaryTbl(ActionEvent event) {
    	filteredListSummary.clear();
    	String selectedMonth = MonthCombo.getValue().substring(0,2);
    	String toFilterDate  = selectedMonth+"/01/"+Year.now().getValue();
    	LocalDate convertedDate = LocalDate.parse(toFilterDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    	convertedDate = convertedDate.withDayOfMonth(
    	                                convertedDate.getMonth().length(convertedDate.isLeapYear()));
    	//System.out.println(convertedDate);
    	//filteredList.addAll(contractListSummary);
    	//lengthOfMonth = (int) ChronoUnit.MONTHS.between(lastPayDate,today);
    	DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		//LocalDate lastPayDate; = LocalDate.parse(paymentList.get(paymentList.size()-1).getDatePay(),format);
		
    	for(SummaryContract list:contractListSummary) {
    		LocalDate lastPayDate = LocalDate.parse(list.getSummaryLastPayment(),format);
    		int monthlenght = (int) ChronoUnit.MONTHS.between(lastPayDate,convertedDate);
    		if(monthlenght>0) {
    			//System.out.println(lastPayDate);
    			filteredListSummary.add(list);
    		}	
    	}
    	loadViewContactTbl(filteredListSummary);
    	computeTotalofSummary();
    }
    
    @FXML
    void ClearFilter(ActionEvent event) {
    	filteredListSummary.clear();
    	MonthCombo.getSelectionModel().clearSelection();
    	MonthCombo.setValue(null);
    	MonthCombo.setPromptText("Select Month here..");
    	viewFilterData(null);
    }
    
    @FXML
    void ClearSearch(ActionEvent event) {
    	filteredListSummary.clear();
    	viewSearchContract.clear();
    	viewFilterData(null);
    }
    
       
    @FXML
    void doSelection(MouseEvent event) {
    	//System.out.println(TabDataPane.getSelectionModel().getSelectedIndex());
    	switch(TabDataPane.getSelectionModel().getSelectedIndex()) {	
    	case 0:
    		showCustomer();
    		break;
    	case 1:
    		showSellerAgent();
    		break;
    	case 2:
    		viewExpense();
    		break;
    	case 3:
    		viewRefund();
    		break;
    	}
    }
    
    private void viewRefund() {
    	refundListGlobal.clear();
    	refundListGlobal = MyDao.getRefundList(postgresConnStr,schema_db,"refund");
    	loadRefundTbl(refundListGlobal);
    }
    


	private void viewExpense() {
    	expenseListGlobal.clear();
    	expenseListGlobal = MyDao.getExpenseList(postgresConnStr,schema_db,"expenses");
    	
    	ObservableList<String>monthList = FXCollections.observableArrayList();
    	monthList.add("01: January");   monthList.add("02: February"); monthList.add("03: March"); 	  monthList.add("04: April");
    	monthList.add("05: May");	    monthList.add("06: June"); 	   monthList.add("07: July"); 	  monthList.add("08: August");
    	monthList.add("09: September"); monthList.add("10: October");  monthList.add("11: November"); monthList.add("12: December");
    	filterComboMonth.setItems(monthList);
    	
    	loadExpenseTbl(expenseListGlobal); 
    	expenseTotal();
    }
	
	private void expenseTotal() {
		double myTotal = 0.0;
		
		for(Expenses item: viewExpenseTbl.getItems()) {
			myTotal += Double.parseDouble(item.getExp_amount().replace(",", ""));
		}
		DecimalFormat formatter2 = new DecimalFormat("#,###");
		expenseTotal.setText(formatter2.format(myTotal));
	}
    
    private void showCustomer() {
		// TODO Auto-generated method stub
		customerListGlobal.clear();
		TabDataPane.getSelectionModel().select(0);
		customerListGlobal = MyDao.getCustomerList(postgresConnStr,schema_db,"customer");
		/*
		 * for(Customer list:customerListGlobal) {
		 * System.out.println(list.getCustNo()+" "+list.getLastName()); }
		 */
		loadCustomerAllTbl(customerListGlobal);
	}

    @FXML
	private void showLot(ActionEvent event) {
    	lotListGlobal.clear();
    	lotListGlobal = MyDao.getLotList(postgresConnStr,schema_db);
    	//for(Lot list:lotListGlobal) {
    	//	System.out.println(list.gettPricesqm());
    	//}
    	loadLotTbl(lotListGlobal);
    	//editLotBtn.setDisable(true);
    	viewEditLotPane.toFront();
    }
    
    @FXML
    private void onSelectLot(MouseEvent event) {
    	
    	if(lotTbl.getSelectionModel().getSelectedItems().size()>0) {
    		editLotBtn.setDisable(false);
    		selectedLot = lotTbl.getSelectionModel().getSelectedItems();
    	}else {
    		editLotBtn.setDisable(true);
    	}
    	
    }
  
    private void showSellerAgent() {
    	sellerListGlobal.clear();
        agentListGlobal.clear();
        
        sellerListGlobal = MyDao.getSellerAgentList(postgresConnStr,schema_db,"seller");
        for(SellerAgentInfo list:sellerListGlobal) {
        	int comm = MyDao.computeMyCommision(postgresConnStr,schema_db,"sellercommission","seller_no",list.getSellerAgentNo());
        	DecimalFormat formatter = new DecimalFormat("#,###");
        	list.setSellerAgentCommission(formatter.format(comm));
        }
        loadSellerTbl(sellerListGlobal);
        
        agentListGlobal = MyDao.getAgentList(postgresConnStr,schema_db,"actual_agentcommission");
        loadAgentTbl(agentListGlobal);
    }
    

	private void loadPaymentHistoryTbl(List<PaymentHistory> paymentList) {
		// TODO Auto-generated method stub
		ObservableList<PaymentHistory>list = FXCollections.observableArrayList();
		list.addAll(paymentList);
		historypaymentCustIdCol.setCellValueFactory(new PropertyValueFactory<PaymentHistory,String>("customerNo"));
		historypaymentNameCol.setCellValueFactory(new PropertyValueFactory<PaymentHistory,String>("customerName"));
		historypaymentAmountCol.setCellValueFactory(new PropertyValueFactory<PaymentHistory,String>("paymentAmount"));
		historypaymentDateCol.setCellValueFactory(new PropertyValueFactory<PaymentHistory,String>("paymentDate"));
		historypaymentSellCol.setCellValueFactory(new PropertyValueFactory<PaymentHistory,String>("sellerComm"));
		historypaymentAgentCol.setCellValueFactory(new PropertyValueFactory<PaymentHistory,String>("agentComm"));
		historypaymentTbl.setItems(list);
	}
	
	
    private void loadLotTbl(List<Lot> lotList) {
		// TODO Auto-generated method stub
		ObservableList<Lot>list = FXCollections.observableArrayList();
		list.addAll(lotList);
		lotblockNoCol.setCellValueFactory(new PropertyValueFactory<Lot,String>("blockNum"));
		lotlotNoCol.setCellValueFactory(new PropertyValueFactory<Lot,String>("lotNum"));
		lotlotAreCol.setCellValueFactory(new PropertyValueFactory<Lot,String>("lotAreasqm"));
		lotPriceCol.setCellValueFactory(new PropertyValueFactory<Lot,String>("pricesqm"));
		lotTPriceCol.setCellValueFactory(new PropertyValueFactory<Lot,String>("TotalPricesqm"));
		lotAvailCol.setCellValueFactory(new PropertyValueFactory<Lot,String>("lotAvail"));
		lotTbl.setItems(list);
		
	}
    
    private void loadSellerTbl(List<SellerAgentInfo> sellerList) {
		// TODO Auto-generated method stub
		ObservableList<SellerAgentInfo>list = FXCollections.observableArrayList();
		list.addAll(sellerList);
		sellerNoCol.setCellValueFactory(new PropertyValueFactory<SellerAgentInfo,String>("SellerAgentNo"));
		sellerNameCol.setCellValueFactory(new PropertyValueFactory<SellerAgentInfo,String>("SellerAgentName"));
		sellerTotalComm.setCellValueFactory(new PropertyValueFactory<SellerAgentInfo,String>("SellerAgentCommission"));
		sellerTbl.setItems(list);
		
	}
    
    private void loadAgentTbl(List<AngentInfo> agentList) {
		// TODO Auto-generated method stub
		ObservableList<AngentInfo>list = FXCollections.observableArrayList();
		list.addAll(agentList);
		agentNoCol.setCellValueFactory(new PropertyValueFactory<AngentInfo,String>("AgentNo"));
		agentNameCol.setCellValueFactory(new PropertyValueFactory<AngentInfo,String>("AgentName"));
		agentTotalComm.setCellValueFactory(new PropertyValueFactory<AngentInfo,String>("AgentCommission"));
		agentCategoryCol.setCellValueFactory(new PropertyValueFactory<AngentInfo,String>("AgentDescription"));
		agentDateCol.setCellValueFactory(new PropertyValueFactory<AngentInfo,String>("AgentDate"));
		agentTbl.setItems(list);
		
	}
    
	private void loadCustomerAllTbl(List<Customer> customerList) {
		// TODO Auto-generated method stub
		ObservableList<Customer>list = FXCollections.observableArrayList();
		list.addAll(customerList);
		viewCustomerNoCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("CustNo"));
		viewFullNameCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("lastName"));
		viewBirthdayCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("birthDay"));
		viewPhoneNoCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("phoneNo"));
		viewEmailAddCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("emailAdd"));
		viewStatCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("status"));
		viewCustomerTbl.setItems(list);
	}
	
	private void loadExpenseTbl(List<Expenses> expenseList) {
		ObservableList<Expenses>list = FXCollections.observableArrayList();
		list.addAll(expenseList);
		expenseNoCol.setCellValueFactory(new PropertyValueFactory<Expenses,String>("exp_no"));
		expenseDateCol.setCellValueFactory(new PropertyValueFactory<Expenses,String>("exp_date"));
		expenseCatCol.setCellValueFactory(new PropertyValueFactory<Expenses,String>("exp_cat"));
		expenseTitleCol.setCellValueFactory(new PropertyValueFactory<Expenses,String>("exp_title"));
		expenseDescpCol.setCellValueFactory(new PropertyValueFactory<Expenses,String>("exp_descp"));
		expenseAmountCol.setCellValueFactory(new PropertyValueFactory<Expenses,String>("exp_amount"));
		viewExpenseTbl.setItems(list);
	}
	
    private void loadRefundTbl(List<Contract> refundList) {
		// TODO Auto-generated method stub
    	ObservableList<Contract>list = FXCollections.observableArrayList();
		list.addAll(refundList);
		refundContNoCol.setCellValueFactory(new PropertyValueFactory<Contract,String>("contractNo"));
		refundCustNoCol.setCellValueFactory(new PropertyValueFactory<Contract,String>("customerNo"));
		refundBlockNoCol.setCellValueFactory(new PropertyValueFactory<Contract,String>("blockNo"));
		refundLotNoCol.setCellValueFactory(new PropertyValueFactory<Contract,String>("lotNo"));
		refundAmountCol.setCellValueFactory(new PropertyValueFactory<Contract,String>("payment"));
		refundTbl.setItems(list);
	}
	
    @FXML
    void validateInput(KeyEvent event) {
    	
    	filterInput(event, amountToPay_txt);
    }
    
    private void filterInput(KeyEvent event, TextField txtfield) {
    	
    	if(!event.getCode().equals(KeyCode.BACK_SPACE) && !event.getCode().equals(KeyCode.DELETE)
    			&& !event.getCode().isDigitKey() && !event.getCode().isArrowKey()
    			&& !event.getCode().equals(KeyCode.ENTER) && !event.getCode().equals(KeyCode.TAB)
    			&& !event.getCode().equals(KeyCode.PERIOD) && !event.getCode().equals(KeyCode.SHIFT)) {
    		
    		Alert alert = Alerts.error("Invalid Input!!", "Error");
    		if(alert.getResult() == ButtonType.OK) {
    			txtfield.clear();
    		}
    	}
    }
	

}
