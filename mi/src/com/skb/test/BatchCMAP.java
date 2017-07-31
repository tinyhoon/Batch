package com.skb.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class BatchCMAP {
	
	PreparedStatement preparedStatement = null;
	Connection connection = null;
	
	public void excute(){
		
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
		
	
		String		oneLine  = null;
		query = "MERGE INTO OANETANM_17 USING DUAL ON (ANNC_ID=?) ";
		query += "WHEN MATCHED THEN ";
		query += "UPDATE SET ANNC_REG_DT=?,CLASS_TY=?,SVC_LINK_YN=?,NET_TY=?,ANNC_REG_NM=?,OCCR_DT=?,LAST_RECOV_DT=?,PLN_RECOV_DT=?,ANNC_TYP=? ";
		query += "WHEN NOT MATCHED THEN ";
		query += "INSERT (ANNC_ID,ANNC_REG_DT,CLASS_TY,SVC_LINK_YN,NET_TY,ANNC_REG_NM,OCCR_DT,LAST_RECOV_DT,PLN_RECOV_DT,ANNC_TYP) ";
		query +=  "VALUES(?,?,?,?,?,?,?,?,?,?)";
		StringTokenizer	tokens;
		//List<String> output = new ArrayList<String>();
		
		String annc_id = null;
		String annc_reg_dt = null;
		String class_ty = null;
		String svc_link_yn = null;
		String net_ty = null;
		String annc_reg_nm = null;
		String occr_dt = null;
		String last_recov_dt = null;
		String pln_recov_dt = null;
		String annc_typ = null;
	
			//FileReader		sourceFileReader = new FileReader ("data" + File.separator + "CMAP_IF" + File.separator + "ZOSS_NET_DABL_OPER_20170720.dat");		
			//BufferedReader	sourceBufferedReader = new BufferedReader (sourceFileReader);
			
			File		file = new File ("Resources" + File.separator + "CMAP_IF" + File.separator + "ZOSS_NET_DABL_OPER_20170720.dat");	
			BufferedReader sourceBufferedReader = null;
			try {
				sourceBufferedReader = new BufferedReader (new InputStreamReader (new FileInputStream (file), "MS949"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
//			getTargetQuery ();
			try {
				oneLine = sourceBufferedReader.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}				// Skip.
			try {
				preparedStatement = connection.prepareStatement(query);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				while ((oneLine = sourceBufferedReader.readLine()) != null) {
					oneLine = oneLine.substring(0, oneLine.length () - 1);				// ; 제거\	
					
					if (oneLine.equals(""))
						continue;

					tokens = new StringTokenizer (oneLine, "|");
		
					annc_id = tokens.nextToken();
					annc_reg_dt = tokens.nextToken();
					class_ty = tokens.nextToken();
					svc_link_yn = tokens.nextToken();
					net_ty  = tokens.nextToken();
					annc_reg_nm = tokens.nextToken();
					occr_dt = tokens.nextToken();
					last_recov_dt = tokens.nextToken();
					pln_recov_dt = tokens.nextToken();
					annc_typ = tokens.nextToken();
					mergeTable(annc_id,annc_reg_dt,class_ty,svc_link_yn,net_ty,	annc_reg_nm,occr_dt,last_recov_dt,pln_recov_dt,	annc_typ);

				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println ("Hi1");
			try {
				sourceBufferedReader.close ();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println ("Hi2");
			//connection.commit();
			//sourceFileReader.close ();
		
	}
	
	public boolean mergeTable (String annc_id,String annc_reg_dt,String class_ty,String svc_link_yn,String net_ty
			,String annc_reg_nm,String occr_dt,String last_recov_dt,String pln_recov_dt,String annc_typ) {
					try {
					preparedStatement.setString (1, annc_id);
					preparedStatement.setString (2, annc_reg_dt);
					preparedStatement.setString (3, class_ty);
					preparedStatement.setString (4, svc_link_yn);
					preparedStatement.setString (5, net_ty);
					preparedStatement.setString (6, annc_reg_nm);
					preparedStatement.setString (7, occr_dt);
					preparedStatement.setString (8, last_recov_dt);
					preparedStatement.setString (9, pln_recov_dt);
					preparedStatement.setString (10, annc_typ);
					preparedStatement.setString (11, annc_id);
					preparedStatement.setString (12, annc_reg_dt);
					preparedStatement.setString (13, class_ty);
					preparedStatement.setString (14, svc_link_yn);
					preparedStatement.setString (15, net_ty);
					preparedStatement.setString (16, annc_reg_nm);
					preparedStatement.setString (17, occr_dt);
					preparedStatement.setString (18, last_recov_dt);
					preparedStatement.setString (19, pln_recov_dt);
					preparedStatement.setString (20, annc_typ);
					preparedStatement.executeQuery();
					connection.commit();
				} catch (Exception e) {
				System.out.println ("mergeTable Exception :" + e);
				}
				return true;
		}
	
	
	public static void main(String[] args) {
		
		BatchCMAP bc =new BatchCMAP();
		bc.excute();
		
	}
	
	

	
}
