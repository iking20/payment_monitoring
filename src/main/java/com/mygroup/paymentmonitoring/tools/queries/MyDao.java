package com.mygroup.paymentmonitoring.tools.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mygroup.paymentmonitoring.tools.model.ActualAgentCommission;
import com.mygroup.paymentmonitoring.tools.model.Agent;
import com.mygroup.paymentmonitoring.tools.model.AgentCommission;
import com.mygroup.paymentmonitoring.tools.model.AgentCommissionSummary;
import com.mygroup.paymentmonitoring.tools.model.AgentCommissionbyClient;
import com.mygroup.paymentmonitoring.tools.model.AngentInfo;
import com.mygroup.paymentmonitoring.tools.model.ClientSummaryonDue;
import com.mygroup.paymentmonitoring.tools.model.Contract;
import com.mygroup.paymentmonitoring.tools.model.Customer;
import com.mygroup.paymentmonitoring.tools.model.CustomerPayment;
import com.mygroup.paymentmonitoring.tools.model.Expenses;
import com.mygroup.paymentmonitoring.tools.model.Lot;
import com.mygroup.paymentmonitoring.tools.model.PaymentHistory;
import com.mygroup.paymentmonitoring.tools.model.Seller;
import com.mygroup.paymentmonitoring.tools.model.SellerAgentInfo;
import com.mygroup.paymentmonitoring.tools.model.SellerCommission;
import com.mygroup.paymentmonitoring.tools.utils.Alerts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;

public class MyDao {
	
	
	public static String generate_no(String tbl_name, String schema_db, String postgresConnStr) {
		
		String no="";
		try {
			String query = "Select count(*) from " +schema_db+"."+tbl_name;
			//System.out.println(query);
			Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			rs.next();
			int counter  = rs.getInt(1);
			//System.out.println(rs.getRow());
			counter +=1;
			conn.close();
			rs.close();
			ps.close();
			//System.out.println(Integer.toString(counter).length());
			String str = String.format("%05d", counter);
			no = str;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		return no;
	}

	public static ObservableList<String> generate_item(String tbl_name, String schema_db, 
			String postgresConnStr) {
		
		// TODO Auto-generated method stub	
		ObservableList<String> list = FXCollections.observableArrayList();
		try {
			String query = "Select distinct blk_no from " +schema_db+"."+tbl_name + " order by blk_no asc";
			Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				//System.out.println(rs.getString("blk_no"));
				list.addAll(rs.getString("blk_no"));
			}
			conn.close();
			rs.close();
			ps.close();
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		return list;
	}

	public static ObservableList<String> generate_item2(String tbl_name, String schema_db, String blkNo,
			String postgresConnStr) {
		// TODO Auto-generated method stub
		ObservableList<String> list = FXCollections.observableArrayList();
		try {
			String query = "select distinct lot_no from " +schema_db+"."+tbl_name+ 
					" where blk_no = '" +blkNo+ "' order by lot_no asc";
			
			Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				//System.out.println(rs.getString("blk_no"));
				list.addAll(rs.getString("lot_no"));
			}
			conn.close();
			rs.close();
			ps.close();
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		return list;
	}

	public static boolean check_availability(String tbl_name, String schema_db, String blkNo, String lotNo,
			String postgresConnStr) {
		// TODO Auto-generated method stub
		String query = "select availability from " +schema_db+"."+tbl_name+ 
				" where blk_no = '" +blkNo+ "' and lot_no = '" +lotNo+ "'";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean avail = false;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				avail = rs.getBoolean("availability");
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}

		return avail;
	}

	public static List<String> setPrice(String tbl_name, String schema_db, String blkNo, String lotNo,
			String postgresConnStr) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		String query = "select lot_area, price_per_sqm, tcp from " +schema_db+"."+tbl_name+ 
				" where blk_no = '" +blkNo+ "' and lot_no = '" +lotNo+ "'";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getString("lot_area"));
				list.add(rs.getString("price_per_sqm"));
				list.add(rs.getString("tcp"));
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		return list;
	}

	public static void addCustomer(List<Customer> customerList, String postgresConnStr, String schema_db, String tblName) {
		// TODO Auto-generated method stub
		String query = "Insert into " + schema_db + "." + tblName + " values (?,?,?,?,?,?,?)";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, customerList.get(0).getCustNo());
			ps.setString(2, customerList.get(0).getLastName());
			ps.setString(3, customerList.get(0).getFirstName());
			ps.setString(4, customerList.get(0).getMiddleName());
			ps.setString(5, customerList.get(0).getBirthDay());
			ps.setString(6, customerList.get(0).getPhoneNo());
			ps.setString(7, customerList.get(0).getEmailAdd());
			ps.executeUpdate();
			ps.close();
			conn.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		
	}

	public static void addContract(List<Contract> contractList, String postgresConnStr, String schema_db,
			String tblName) {
		// TODO Auto-generated method stub
		String query = "Insert into " + schema_db + "." + tblName + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, contractList.get(0).getContractNo());
			ps.setString(2, contractList.get(0).getCustomerNo());
			ps.setString(3, contractList.get(0).getBlockNo());
			ps.setString(4, contractList.get(0).getLotNo());
			ps.setString(5, contractList.get(0).getLotArea());
			ps.setString(6, contractList.get(0).getDuration());
			ps.setString(7, contractList.get(0).getPrice());
			ps.setString(8, contractList.get(0).getTotalPrice());
			ps.setString(9, contractList.get(0).getPayment());
			ps.setString(10, contractList.get(0).getBalance());
			ps.setString(11, contractList.get(0).getDateStart());
			ps.setString(12, contractList.get(0).getDateEnd());
			ps.setString(13, contractList.get(0).getSellerNo());
			ps.setString(14, contractList.get(0).getAgentNo());
			ps.setString(15, contractList.get(0).getStatus());
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
	}
	
	public static void insertRefund(String postgresConnStr, String schema_db, List<Contract> refundList,
			String tblName) {
		// TODO Auto-generated method stub
		String query = "Insert into " + schema_db + "." + tblName + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, refundList.get(0).getContractNo());
			ps.setString(2, refundList.get(0).getCustomerNo());
			ps.setString(3, refundList.get(0).getBlockNo());
			ps.setString(4, refundList.get(0).getLotNo());
			ps.setString(5, refundList.get(0).getLotArea());
			ps.setString(6, refundList.get(0).getDuration());
			ps.setString(7, refundList.get(0).getPrice());
			ps.setString(8, refundList.get(0).getTotalPrice());
			ps.setString(9, refundList.get(0).getPayment());
			ps.setString(10, refundList.get(0).getBalance());
			ps.setString(11, refundList.get(0).getDateStart());
			ps.setString(12, refundList.get(0).getDateEnd());
			ps.setString(13, refundList.get(0).getSellerNo());
			ps.setString(14, refundList.get(0).getAgentNo());
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
	}

	public static void updateLotAvailability(String postgresConnStr, String schema_db, String tblName, String blNo,
			String ltNo, boolean availability) {
		// TODO Auto-generated method stub
		String query = "Update " +schema_db+"."+tblName+ " set availability = ? where blk_no = ? and lot_no = ?";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setBoolean(1, availability);
			ps.setString(2, blNo);
			ps.setString(3, ltNo);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
	}
	
	public static void updateCustomerContractStatus(String postgresConnStr, String schema_db, String tblName,
			String custID) {
		// TODO Auto-generated method stub
		String query = "Update " +schema_db+"."+tblName+ " set status = ? where cust_id = ?";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, "Cancelled");
			ps.setString(2, custID);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
	}

	public static void addPayment(List<CustomerPayment> paymentList, String postgresConnStr, String schema_db,
			String tblName) {
		// TODO Auto-generated method stub
		
		String query = "Insert into " + schema_db + "." + tblName + " values (?,?,?,?)";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, paymentList.get(0).getCustomerNo());
			ps.setString(2, paymentList.get(0).getPaymentNo());
			ps.setString(3, paymentList.get(0).getAmount());
			ps.setString(4, paymentList.get(0).getDatePay());
			ps.executeUpdate();
			ps.close();
			conn.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
	}

	public static void addSellerCommision(List<SellerCommission> sellerCommisionList, String postgresConnStr,
			String schema_db, String tblName) {
		// TODO Auto-generated method stub
		
		String query = "Insert into " + schema_db + "." + tblName + " values (?,?,?,?)";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, sellerCommisionList.get(0).getCustomerNo());
			ps.setString(2, sellerCommisionList.get(0).getSellerNo());
			ps.setString(3, sellerCommisionList.get(0).getAmount());
			ps.setString(4, sellerCommisionList.get(0).getDatePay());
			ps.executeUpdate();
			ps.close();
			conn.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
	}

	public static void addAgentCommision(List<AgentCommission> agentCommissionList, String postgresConnStr,
			String schema_db, String tblName) {
		// TODO Auto-generated method stub
		
		String query = "Insert into " + schema_db + "." + tblName + " values (?,?,?,?)";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, agentCommissionList.get(0).getCustomerNo());
			ps.setString(2, agentCommissionList.get(0).getAgentNo());
			ps.setString(3, agentCommissionList.get(0).getAmount());
			ps.setString(4, agentCommissionList.get(0).getDatePay());
			ps.executeUpdate();
			ps.close();
			conn.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
	}

	public static ObservableList<Customer> searchCustomerList(String searchtxt, String postgresConnStr, String schema_db,
			String tblName) {
		// TODO Auto-generated method stub
		ObservableList<Customer> list = FXCollections.observableArrayList();
		String query = "Select * from " + schema_db + "." + tblName + " where status is null and " 
				+ "(cust_id ilike '%"+searchtxt+"%' or l_name ilike '%"+searchtxt+"%' or "
				+ "f_name ilike '%"+searchtxt+"%' or m_name ilike '%"+searchtxt+"%')";
		
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				Customer customerModel  = new Customer(rs.getString("cust_id"), 
						rs.getString("l_name"), rs.getString("f_name"), rs.getString("m_name"), 
						rs.getString("b_day"), rs.getString("phone_no"), rs.getString("email_add"),"");
				
				list.add(customerModel);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		
		
		return list;
	}

	public static List<Contract> getContractList(String custNo, String postgresConnStr, String schema_db, String tblName) {
		// TODO Auto-generated method stub
		List<Contract> contractList = new ArrayList<>();
		String query = "Select * from " + schema_db + "." + tblName + " where " 
				+ "cust_id = '"+custNo+"' and status = 'reserved'";
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				Contract contractModel  = new Contract(rs.getString("cont_no"), 
						rs.getString("cust_id"), rs.getString("blk_no"), rs.getString("lot_no"), 
						rs.getString("lot_area"), rs.getString("duration"),rs.getString("price"), rs.getString("total_contract_price"), 
						rs.getString("payment"), rs.getString("balance"), rs.getString("date_start"), rs.getString("date_end"), 
						rs.getString("seller_no"), rs.getString("agent_no"),rs.getString("status"));
				
				contractList.add(contractModel);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		
		return contractList;
	}
	
	public static List<Contract> getContractListAll(String postgresConnStr, String schema_db, String tblName) {
		// TODO Auto-generated method stub
		List<Contract> contractList = new ArrayList<>();
		String query = "Select * from " + schema_db + "." + tblName + " where status = 'reserved' order by cust_id ASC";
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				Contract contractModel  = new Contract(rs.getString("cont_no"), 
						rs.getString("cust_id"), rs.getString("blk_no"), rs.getString("lot_no"), 
						rs.getString("lot_area"), rs.getString("duration"),rs.getString("price"), rs.getString("total_contract_price"), 
						rs.getString("payment"), rs.getString("balance"), rs.getString("date_start"), rs.getString("date_end"), 
						rs.getString("seller_no"), rs.getString("agent_no"),rs.getString("status"));
				
				contractList.add(contractModel);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		
		return contractList;
	}

	public static ObservableList<CustomerPayment> getPaymentList(String custNo, String postgresConnStr, String schema_db, String tblName) {
		// TODO Auto-generated method stub
		
		ObservableList<CustomerPayment> paymentList = FXCollections.observableArrayList();
		String query = "Select * from " + schema_db + "." + tblName + " where " 
				+ "cust_id = '"+custNo+"'";
		
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				CustomerPayment paymentModel  = new CustomerPayment(rs.getString("cust_id"), 
						rs.getString("payment_id"), rs.getString("amount"), 
						rs.getString("date_pay"));
				
				paymentList.add(paymentModel);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		
		return paymentList;
		
	}

	public static String selectColor(String blNo, String ltNo, String postgresConnStr, String schema_db,
			String tblName) {
		// TODO Auto-generated method stub
		
		String color_code = "";
		String query = "select color_code from " +schema_db+"."+tblName+ 
				" where blk_no = '" +blNo+ "' and lot_no = '" +ltNo+ "'";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				color_code = rs.getString("color_code");
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		return color_code;
	}

	public static void updateContract(String contID, String custID, int currentpayment, int balance, String postgresConnStr, String schema_db,
			String tblName) {
		// TODO Auto-generated method stub
		DecimalFormat formatter = new DecimalFormat("#,###");
		String query = "Update " +schema_db+"."+tblName+ " set payment = ?, balance = ? where cont_no = ? and cust_id = ?";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, Integer.toString(currentpayment));
			ps.setString(2, formatter.format(balance));
			ps.setString(3, contID);
			ps.setString(4, custID);
			ps.executeUpdate();
			ps.close();
			conn.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
	}

	public static int computeAmount(String postgresConnStr, String schema_db, String tblName, String colName) {
		// TODO Auto-generated method stub
		int amount = 0;
		String query = "Select " + colName + " from " +schema_db+"."+tblName+"";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				amount+= Integer.parseInt(rs.getString(colName).replace(",", ""));
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		return amount;
	}

	public static int computeTotalCommission(String postgresConnStr, String schema_db, String tblName, String colName,
			String colorVal) {
		// TODO Auto-generated method stub
		int amount = 0;
		String query = "Select " + colName + " from " +schema_db+"."+tblName+" where color_code = '" +colorVal+"'";
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				amount+= Integer.parseInt(rs.getString(colName).replace(",", ""));
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		
		return amount;
	}

	public static int computeCommision(String postgresConnStr, String schema_db, String tblName, String colName,
			String customerNo) {
		// TODO Auto-generated method stub
		int amount = 0;
		String query = "Select " + colName + " from " +schema_db+"."+tblName+" where cust_id = '" +customerNo+"'";
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				amount+= Integer.parseInt(rs.getString(colName).replace(",", ""));
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		return amount;
	}

	public static int countItem(String postgresConnStr, String schema_db, String tblName) {
		// TODO Auto-generated method stub
		int no=0;
		try {
			String query = "Select count(*) from " +schema_db+"."+tblName;
			//System.out.println(query);
			Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			rs.next();
			no  = rs.getInt(1);			
			conn.close();
			rs.close();
			ps.close();
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		return no;
	}
	
	public static int countItemAvailable(String postgresConnStr, String schema_db, String tblName) {
		// TODO Auto-generated method stub
		int no=0;
		try {
			String query = "Select count(*) from " +schema_db+"."+tblName + " where availability = true";
			//System.out.println(query);
			Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			rs.next();
			no  = rs.getInt(1);			
			conn.close();
			rs.close();
			ps.close();
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		return no;
	}

	public static String getCustomerName(String postgresConnStr, String schema_db, String tblName, String customerNo) {
		// TODO Auto-generated method stub
		String  fullName = "";
		StringBuilder str = new StringBuilder();
		String query = "Select l_name, f_name, m_name from " +schema_db+"."+tblName+" where cust_id = '" +customerNo+"'";
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				str.append(rs.getString("l_name"));
				str.append(", " + rs.getString("f_name"));
				str.append(" " + rs.getString("m_name"));
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		fullName = str.toString();
		return fullName;
	}

	public static List<PaymentHistory> getPaymentList(String postgresConnStr, String schema_db) {
		// TODO Auto-generated method stub
		List<PaymentHistory> list = new ArrayList<>();
		String query = "Select py.cust_id, py.payment_id, py.amount, py.date_pay, sl.amount, ag.amount "
				+ "from " + schema_db + ".payments py, " + schema_db + ".sellercommission sl, "
				+ schema_db + ".agentcommission ag 	where py.cust_id = sl.cust_id and py.date_pay = sl.date_pay"
						+ "	and ag.cust_id = py.cust_id and ag.date_pay = py.date_pay order by py.date_pay asc";
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				PaymentHistory paymentModel  = new PaymentHistory(rs.getString(1), 
						rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getString(6));
				
				list.add(paymentModel);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		
		return list;

	}

	public static List<Lot> getLotList(String postgresConnStr, String schema_db) {
		// TODO Auto-generated method stub
		List<Lot> list = new ArrayList<>();
		String query = "Select * from " +schema_db+".lot order by lot_no::int ASC";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				Lot lotModel = new Lot(rs.getString("blk_no"), rs.getString("lot_no"),
						rs.getString("lot_area"), rs.getString("price_per_sqm"),
						rs.getString("tcp"), rs.getBoolean("availability"),rs.getString("color_code"),rs.getString("lot_code"));
				list.add(lotModel);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		return list;
	}

	public static List<SellerAgentInfo> getSellerAgentList(String postgresConnStr, String schema_db, String tblName) {
		// TODO Auto-generated method stub
		List<SellerAgentInfo> list = new ArrayList<>();
		StringBuilder name = new StringBuilder();
		String query = "Select * from " +schema_db+"."+tblName;
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				name.append(rs.getString("l_name")+", " + rs.getString("f_name") + " " + rs.getString("m_name"));
				SellerAgentInfo selleragentModerl = new SellerAgentInfo(rs.getString(1), name.toString(),
						"");
				list.add(selleragentModerl);
				name.setLength(0);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		return list;
	}
	
	public static List<AngentInfo> getAgentList(String postgresConnStr, String schema_db, String tblName) {
		// TODO Auto-generated method stub
		List<AngentInfo> list = new ArrayList<>();
		StringBuilder name = new StringBuilder();
		String query = "Select * from " +schema_db+"."+tblName;
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				
				AngentInfo agenInfoModel = new AngentInfo(rs.getString("comm_agentNo"), rs.getString("comm_agentName"),
						rs.getString("comm_amount"),rs.getString("comm_descp"),rs.getString("comm_date"));
				list.add(agenInfoModel);
				name.setLength(0);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		return list;
	}
	

	public static int computeMyCommision(String postgresConnStr, String schema_db, String tblName,
			String colName, String sellerAgentNo) {
		// TODO Auto-generated method stub
		int amount = 0;
		String query = "Select amount from " +schema_db+"."+tblName+" where " +colName+ " = '" +sellerAgentNo+"'";
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				amount+= Integer.parseInt(rs.getString(1).replace(",", ""));
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		return amount;
		
	}

	public static List<Customer> getCustomerList(String postgresConnStr, String schema_db, String tblName) {
		// TODO Auto-generated method stub
		List<Customer> list = new ArrayList<>();
		StringBuilder name = new StringBuilder();
		String query = "Select * from " +schema_db+"."+tblName;
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				name.append(rs.getString("l_name")+", " + rs.getString("f_name") + " " + rs.getString("m_name"));
				Customer customerModel = new Customer(rs.getString("cust_id"), name.toString(),
						"", "", rs.getString("b_day"), rs.getString("phone_no"), rs.getString("email_add"),rs.getString("status"));
				list.add(customerModel);
				name.setLength(0);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		return list;
	}

	public static int addLot(String postgresConnStr, String schema_db, String tblName, List<Lot> lotList) {
		// TODO Auto-generated method stub
		int ctr = 0;
		String query = "Select count(*) from " +schema_db+"."+tblName + " where blk_no = '" +lotList.get(0).getBlockNum() + "'"
				+ " and lot_no = '" + lotList.get(0).getLotNum() + "'";
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		int Rs = 0;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			rs.next();
			ctr  = rs.getInt(1);	
			//System.out.println(ctr);
			if(ctr > 0) {
				Alert alert = Alerts.error("Block No & Lot No already exist!!", "Error!!");
			}else {
				Alert alert = Alerts.confirmation("Block No & Lot No are available.\nDo you want to proceed?", "Confirmation");
				if(alert.getResult() == ButtonType.YES) {
					query = "Insert into " +schema_db+"."+tblName + " values(?,?,?,?,?,?,?,?)";
					ps = conn.prepareStatement(query);
					ps.setString(1, lotList.get(0).getBlockNum());
					ps.setString(2, lotList.get(0).getLotNum());
					ps.setString(3, lotList.get(0).getLotAreasqm());
					ps.setString(4, lotList.get(0).getPricesqm());
					ps.setString(5, lotList.get(0).getTotalPricesqm());
					ps.setBoolean(6, lotList.get(0).getLotAvail());
					ps.setString(7, lotList.get(0).getColorCode());
					ps.setString(8, lotList.get(0).getLotCode());
					Rs = ps.executeUpdate();
					//System.out.println(Rs);
					ps.close();
					conn.close();
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		return Rs;

	}

	public static int addSeller(String postgresConnStr, String schema_db, String tblName, List<Seller> sellerList) {
		// TODO Auto-generated method stub
		int Rs = 0;
		String query = "Insert into " +schema_db+"."+tblName + " values(?,?,?,?)";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, sellerList.get(0).getSellerNumber());
			ps.setString(2, sellerList.get(0).getSellerLastName());
			ps.setString(3, sellerList.get(0).getSellerFirstName());
			ps.setString(4, sellerList.get(0).getSellerMiddleName());
			Rs = ps.executeUpdate();
			//System.out.println(Rs);
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}

		return Rs;
	}

	public static int addAgent(String postgresConnStr, String schema_db, String tblName, List<Agent> agentList) {
		// TODO Auto-generated method stub
		
		int Rs = 0;
		String query = "Insert into " +schema_db+"."+tblName + " values(?,?,?,?,?,?)";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, agentList.get(0).getAgentNumber());
			ps.setString(2, agentList.get(0).getAgentLastName());
			ps.setString(3, agentList.get(0).getAgentFirstName());
			ps.setString(4, agentList.get(0).getAgentMiddleName());
			ps.setString(5, agentList.get(0).getAgentPhoneNumber());
			ps.setString(6, agentList.get(0).getAgentCategory());
			Rs = ps.executeUpdate();
			//System.out.println(Rs);
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}

		return Rs;
	}

	public static ObservableList<Agent> searchAgentList(String searchtxt, String postgresConnStr, String schema_db,
			String tblName) {
		// TODO Auto-generated method stub
		ObservableList<Agent> list = FXCollections.observableArrayList();
		String query = "Select * from " + schema_db + "." + tblName + " where " 
				+ "agent_no ilike '%"+searchtxt+"%' or l_name ilike '%"+searchtxt+"%' or "
				+ "f_name ilike '%"+searchtxt+"%' or m_name ilike '%"+searchtxt+"%' ";
		
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				Agent agentModel  = new Agent(rs.getString("agent_no"), 
						rs.getString("l_name"), rs.getString("f_name"), rs.getString("m_name"), 
						rs.getString("phone_no"),rs.getString("category"));
				
				list.add(agentModel);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		return list;
	}

	public static ObservableList<Seller> searchSellerList(String searchtxt, String postgresConnStr, String schema_db,
			String tblName) {
		// TODO Auto-generated method stub
		ObservableList<Seller> list = FXCollections.observableArrayList();
		String query = "Select * from " + schema_db + "." + tblName + " where " 
				+ "seller_no ilike '%"+searchtxt+"%' or l_name ilike '%"+searchtxt+"%' or "
				+ "f_name ilike '%"+searchtxt+"%' or m_name ilike '%"+searchtxt+"%' ";
		
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				Seller sellerModel  = new Seller(rs.getString("seller_no"), 
						rs.getString("l_name"), rs.getString("f_name"), rs.getString("m_name"));
				
				list.add(sellerModel);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		return list;
	}

	public static String getSellerName(String postgresConnStr, String schema_db, String tblName, String sellerNo) {
		// TODO Auto-generated method stub
		String  fullName = "";
		StringBuilder str = new StringBuilder();
		String query = "Select l_name, f_name, m_name from " +schema_db+"."+tblName+" where seller_no = '" +sellerNo+"'";
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				str.append(rs.getString("l_name"));
				str.append(", " + rs.getString("f_name"));
				str.append(" " + rs.getString("m_name"));
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		fullName = str.toString();
		return fullName;
	}

	public static String getAgentName(String postgresConnStr, String schema_db, String tblName, String agentNo) {
		// TODO Auto-generated method stub
		String  fullName = "";
		StringBuilder str = new StringBuilder();
		String query = "Select l_name, f_name, m_name from " +schema_db+"."+tblName+" where agent_no = '" +agentNo+"'";
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				str.append(rs.getString("l_name"));
				str.append(", " + rs.getString("f_name"));
				str.append(" " + rs.getString("m_name"));
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		fullName = str.toString();
		return fullName;
	}

	public static String selectCategory(String agentNo, String postgresConnStr, String schema_db, String tblName) {
		// TODO Auto-generated method stub
		String  category = "";
		String query = "Select category from " +schema_db+"."+tblName+" where agent_no = '" +agentNo+"'";
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				category = rs.getString("category");
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		return category;
	}

	public static int addExpense(String postgresConnStr, String schema_db, String tblName, List<Expenses> expenseList) {
		// TODO Auto-generated method stub
		int Rs = 0;
		String query = "Insert into " +schema_db+"."+tblName + " values(?,?,?,?,?,?)";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, expenseList.get(0).getExp_no());
			ps.setString(2, expenseList.get(0).getExp_date());
			ps.setString(3, expenseList.get(0).getExp_cat());
			ps.setString(4, expenseList.get(0).getExp_title());
			ps.setString(5, expenseList.get(0).getExp_descp());
			ps.setString(6, expenseList.get(0).getExp_amount());
			Rs = ps.executeUpdate();
			//System.out.println(Rs);
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}

		return Rs;
	}

	public static List<Expenses> getExpenseList(String postgresConnStr, String schema_db, String tblName) {
		// TODO Auto-generated method stub
		List<Expenses> list = new ArrayList<>();
		String query = "Select * from " +schema_db+"."+tblName+ " order by exp_date ASC";
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				Expenses expenseModel = new Expenses(rs.getString("exp_no"), rs.getString("exp_date"),
						rs.getString("exp_cat"), rs.getString("exp_title"), rs.getString("exp_descp"),rs.getString("exp_amount"));
				list.add(expenseModel);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		return list;
	}

	public static void deleteCommission(String postgresConnStr, String schema_db, String tblName, String colName, String CustID,
			String CommNo, String DatePay) {
		// TODO Auto-generated method stub
		String query = "Delete from " +schema_db+"."+tblName + " where cust_id = ? and "+colName+" = ? and date_pay = ?";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, CustID);
			ps.setString(2, CommNo);
			ps.setString(3, DatePay);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
	}

	public static List<Contract> getRefundList(String postgresConnStr, String schema_db, String tblName) {
		// TODO Auto-generated method stub
		List<Contract> list = new ArrayList<>();
		String query = "Select * from " +schema_db+"."+tblName;
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				Contract refundModel = new Contract(rs.getString("cont_no"), rs.getString("cust_id"),
						rs.getString("blk_no"), rs.getString("lot_no"),"","","","",rs.getString("payment"),
						"","","","","","");
				list.add(refundModel);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		return list;
	}

	public static int editLot(String connStr, String schema_db, String tblName, List<Lot> lotList) {
		// TODO Auto-generated method stub
		Connection conn = ConnectPosgreSQL.connect(connStr);
		PreparedStatement ps = null;
		String query = "Update " +schema_db+"."+tblName + " set blk_no=?, lot_no=?, lot_area=?, price_per_sqm=?, "
				+"tcp=?, availability=?, color_code=? where lot_Code=?";
		int resultSet = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, lotList.get(0).getBlockNum());
			ps.setString(2, lotList.get(0).getLotNum());
			ps.setString(3, lotList.get(0).getLotAreasqm());
			ps.setString(4, lotList.get(0).getPricesqm());
			ps.setString(5, lotList.get(0).getTotalPricesqm());
			ps.setBoolean(6, lotList.get(0).getLotAvail());
			ps.setString(7, lotList.get(0).getColorCode());
			ps.setString(8, lotList.get(0).getLotCode());
			resultSet = ps.executeUpdate();
			//System.out.println(Rs);
			ps.close();
			conn.close();	
			//System.out.println(ctr);

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		return resultSet;
	}

	public static String getLastPayment(String postgresConnStr, String schema_db, String tblName, String customerNo) {
		// TODO Auto-generated method stub
		String lastPayment = "";
		List<String> list = new ArrayList<String>();
		String query = "Select date_pay from " +schema_db+"."+tblName+" where cust_id = '" +customerNo+"' order by date_pay asc";
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getString("date_pay"));
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		lastPayment = list.get(list.size()-1);
		return lastPayment;
		
	}

	public static int getLeftOverAmountDue(String postgresConnStr, String schema_db, String tblName, String customerNo) {
		// TODO Auto-generated method stub
		int leftoveramount = -0;
		String query = "Select amount from " +schema_db+"."+tblName+" where cust_id = '" +customerNo+"'";
		List<String> list = new ArrayList<String>();
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getString("amount"));
			}
			conn.close();
			rs.close();
			ps.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		if(!list.isEmpty()) {
			leftoveramount = Integer.parseInt(list.get(list.size()-1));
		}
		
		//System.out.println(leftoveramount);
		return leftoveramount;
	}

	public static void addLeftOver(List<CustomerPayment> leftoverList, String postgresConnStr, String schema_db,
			String string) {
		// TODO Auto-generated method stub
		
	}

	public static int addActualAgentCommission(String connStr, String schemaDb, String tblName,
			List<ActualAgentCommission> commList) {
		// TODO Auto-generated method stub
		int Rs = 0;
		String query = "Insert into " +schemaDb+"."+tblName + " values(?,?,?,?,?,?)";
		Connection conn = ConnectPosgreSQL.connect(connStr);
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, commList.get(0).getComm_no());
			ps.setString(2, commList.get(0).getComm_date());
			ps.setString(3, commList.get(0).getComm_agentNo());
			ps.setString(4, commList.get(0).getComm_agentName());
			ps.setString(5, commList.get(0).getComm_descp());
			ps.setString(6, commList.get(0).getComm_amount());
			Rs = ps.executeUpdate();
			//System.out.println(Rs);
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}

		return Rs;
	}

	public static int editPayment(String connStr, String schemaDb, String tblName, String custID, String paymentDate,
			String amount) {
		// TODO Auto-generated method stub
		int Rs = 0;
		String query = "Update " +schemaDb+"."+tblName + " set amount = ? where cust_id = ? and date_pay = ?";
		Connection conn = ConnectPosgreSQL.connect(connStr);
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, amount);
			ps.setString(2, custID);
			ps.setString(3, paymentDate);
			Rs = ps.executeUpdate();
			//System.out.println(Rs);
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		
		return Rs;
	}

	public static void editContract(String connStr, String schemaDb, String tblName, String custID, String balance,
			int totalPayment) {
		// TODO Auto-generated method stub
		String query = "Update " +schemaDb+"."+tblName + " set payment = ?, balance = ? where cust_id = ?";
		Connection conn = ConnectPosgreSQL.connect(connStr);
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, String.valueOf(totalPayment));
			ps.setString(2, balance);
			ps.setString(3, custID);
			ps.executeUpdate();
			//System.out.println(Rs);
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
	}

	public static List<Customer> getCustomerInfo(String connStr, String schemaDb, String tblName,
			String custID) {
		// TODO Auto-generated method stub
		List<Customer> list = new ArrayList<>();
		String query = "Select * from "+schemaDb+"."+tblName+" where cust_id = '"+custID+"'";
		
		Connection conn = ConnectPosgreSQL.connect(connStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				Customer customerModel  = new Customer(rs.getString("cust_id"), 
						rs.getString("l_name"), rs.getString("f_name"), rs.getString("m_name"), 
						rs.getString("b_day"), rs.getString("phone_no"), rs.getString("email_add"),"");
				
				list.add(customerModel);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		return list;
	}

	public static int updateCustomerInfo(String connStr, String schemaDb, String tblName,
			List<Customer> customerList) {
		// TODO Auto-generated method stub
		String query = "Update " +schemaDb+"."+tblName + " set l_name = ?, f_name = ?, m_name = ?, b_day = ?, "+
		"phone_no = ?, email_add = ? where cust_id = ?";
		Connection conn = ConnectPosgreSQL.connect(connStr);
		PreparedStatement ps = null;
		int Rs = 0;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, customerList.get(0).getLastName());
			ps.setString(2, customerList.get(0).getFirstName());
			ps.setString(3, customerList.get(0).getMiddleName());
			ps.setString(4, customerList.get(0).getBirthDay());
			ps.setString(5, customerList.get(0).getPhoneNo());
			ps.setString(6, customerList.get(0).getEmailAdd());
			ps.setString(7, customerList.get(0).getCustNo());
			Rs = ps.executeUpdate();
			//System.out.println(Rs);
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
		}
		return Rs;
	}

	public static ObservableList<AgentCommissionSummary> getAgentListSummary(String postgresConnStr, String schema_db,
			String tblName) {
		
		ObservableList<AgentCommissionSummary> agentCommSummary = FXCollections.observableArrayList();
		String query = "Select agent_no, CONCAT(l_name, ', ', f_name, ' ', m_name) from " +schema_db+"."+tblName;
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				AgentCommissionSummary agentSummaryModel  = new AgentCommissionSummary(rs.getString(1),rs.getString(2),"");
				agentCommSummary.add(agentSummaryModel);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		return agentCommSummary;
	}

	public static String doComputeCommissionSummary(String postgresConnStr, String schema_db, String tblName,
			String agentNo, LocalDate startDate, LocalDate endtDate) {
		
		String commAmount = "";
		String query = "Select sum(amount::integer) from " +schema_db+"."+tblName+" where agent_no = '"+agentNo+"'"
				+ " AND date_pay BETWEEN '"+startDate+"' AND '"+endtDate+"'";
		
		//System.out.println(query);
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		DecimalFormat df = new DecimalFormat("#,###");
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				commAmount = df.format(rs.getInt(1));
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return commAmount;
		}
		
		return commAmount;
	}

	public static ObservableList<String> getAgentNameList(String postgresConnStr, String schema_db, String tblName) {
		
		ObservableList<String> agentNames = FXCollections.observableArrayList();
		String query = "Select agent_no, CONCAT(l_name, ', ', f_name, ' ', m_name) from " +schema_db+"."+tblName;
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				String agentName = rs.getString(1)+": "+rs.getString(2);
				agentNames.add(agentName);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		return agentNames;
	}

	public static ObservableList<AgentCommissionbyClient> getClientPaymentSummary(String postgresConnStr,
			String schema_db, String payments, String sellercommission, String agentcommission, 
			String agentNo, LocalDate startDate,LocalDate endtDate) {
		
		ObservableList<AgentCommissionbyClient> agentCommClient = FXCollections.observableArrayList();
		String query = "Select pay.cust_id, pay.amount, sc.amount, ag.amount from "
				+ schema_db+"."+payments + " pay, " + schema_db+"."+sellercommission + " sc, " 
				+ schema_db+"."+agentcommission + " ag"
				+ "	where pay.cust_id = sc.cust_id AND pay.date_pay = sc.date_pay"
				+ "	AND pay.cust_id = ag.cust_id AND pay.date_pay = ag.date_pay"
				+ "	AND ag.agent_no = '"+agentNo+"'"
				+ "	AND ag.date_pay BETWEEN '"+startDate+"' AND '"+endtDate+"' order by pay.cust_id asc";
		
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				AgentCommissionbyClient agentSummaryModel  = new AgentCommissionbyClient(rs.getString(1),"",
						rs.getString(2),rs.getString(3),rs.getString(4));
				agentCommClient.add(agentSummaryModel);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		return agentCommClient;
	}

	public static String getClientName(String postgresConnStr, String schema_db, String tblName, String clientNo) {
		
		String clientName  = "";
		String query = "Select CONCAT(l_name, ', ', f_name, ' ', m_name) from " +schema_db+"."+tblName
				+ " where cust_id = '"+clientNo+"'";
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				clientName = rs.getString(1);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		return clientName;
	}

	public static List<ClientSummaryonDue> getCustomerListSummary(String postgresConnStr,
			String schema_db, String customer, String contract) {
		
		List<ClientSummaryonDue> clientonDueSummary = new ArrayList<>();
		String query = "SELECT cust.cust_id, CONCAT(cust.l_name, ', ', cust.f_name, ' ', cust.m_name), "  
				+"cont.total_contract_price, cont.date_start " 
				+"FROM " + schema_db+"."+customer + " cust, " +schema_db+"."+contract + " cont "
				+"WHERE cust.cust_id = cont.cust_id AND cust.status is null";
		
		Connection conn = ConnectPosgreSQL.connect(postgresConnStr);
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				ClientSummaryonDue clientModel  = new ClientSummaryonDue(rs.getString(1),rs.getString(2),rs.getString(3),
						"","","",rs.getString(4),"","","");
				clientonDueSummary.add(clientModel);
			}
			conn.close();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = Alerts.error(e.toString(), "Error!!");
			return null;
		}
		
		return clientonDueSummary;
	}
}
