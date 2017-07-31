package com.skb.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CmapTest {
	
	
		Connection connection = null;
		PreparedStatement pstmt = null;
	
	public void head(){

		Statement stmt = null;
		
		String pms;
		String query;
		
		pms = "jdbc:oracle:thin:@192.168.0.98:7208:NEXCORE";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		try {
			connection = DriverManager.getConnection(pms, "SKBBATCH", "SKBBATCH");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (connection != null) {
            System.out.println("You made it, take control your database now!");
          
        } else {
            System.out.println("Failed to make connection!");
        }
		

		query = "INSERT INTO TEST (ID,NAME) VALUES (?,?)";
		
		/*
		query = "MERGE INTO OANETANM_17 USING DUAL ON (ANNC_ID=?) ";
		query += "WHEN MATCHED THEN ";
		query += "UPDATE SET ANNC_REG_DT=?,CLASS_TY=?,SVC_LINK_YN=?,NET_TY=?,ANNC_REG_NM=?,OCCR_DT=?,LAST_RECOV_DT=?,PLN_RECOV_DT=?,ANNC_TYP=? ";
		query += "WHEN NOT MATCHED THEN ";
		query += " INSERT (ANNC_ID,ANNC_REG_DT,CLASS_TY,SVC_LINK_YN,NET_TY,ANNC_REG_NM,OCCR_DT,LAST_RECOV_DT,PLN_RECOV_DT,ANNC_TYP) ";
		query +=  "VALUES(?,?,?,?,?,?,?,?,?,?)";
		*/
		
		try {		
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1,"3");
			pstmt.setString(2,"홍홍홍");
			pstmt.executeQuery();
			
			//stmt = connection.createStatement();
			//stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

    public static void main(String[] argv) {

		CmapTest ct = new CmapTest();
		ct.head();
		ct.test();
	}
    
    public void test(){
    	System.out.println("Hi");
    }
	
}
