package com.skb.test;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import com.skb.mi.batch.common.DBController;

public class CmapFileBatch {
	
	final private String MI_DB = "jdbc:oracle:thin:@192.168.0.98:7208:nexcore";
	private final int COMMIT_UNIT = 10000;
	private final int COMPARE_KEYS_COUNT = 5;
	
	Connection connection = null;
	int []	columnType;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
	DBController miDb;
	Statement delStmt = miDb.getStmt();
	PreparedStatement preparedStatement = (PreparedStatement) miDb.getPreStmt();
	
	public void excute(){
		
		// Step0 : Connection Database
		miDb = connectionDb(1, MI_DB, "SKBBATCH", "SKBBATCH");
		if (miDb != null){
            System.out.printf("You can take control your database now!\n");
		}
		else 
			System.out.printf("You can not take control your database now!\n");
		oanetonm_18();
		
	}
	
	public void selectColumnType (String tableName) {
		try {
			String query = "SELECT * FROM " + tableName + " WHERE ROWNUM = 1";
			Statement stmt = connection.createStatement();
			
			ResultSet resultSet = stmt.executeQuery(query);
			ResultSetMetaData	resultSetMetaData = resultSet.getMetaData ();
			int columnCount = resultSetMetaData.getColumnCount();
			columnType = new int [columnCount];
			for (int index = 0; index < columnCount; ++ index)
				columnType [index] = resultSetMetaData.getColumnType(index + 1);
		} catch (Exception e) {
		}
	}
	
	public void oanetonm_18(){
		
		int	commitCount = 0;
		
		// Step1 : Get Column Type
		selectColumnType("oanetonm_18");

		Date startDate = new Date();
		System.out.printf("Your Task is just started~! : ");
		System.out.println(dateFormat.format(startDate));
        
		// Step2 : Delete Table 
		String delQuery = "DELETE FROM OANETONM_18";
		deleteTable(delQuery);
		
		String		oneLine  = null;
		String query = "INSERT INTO OANETONM_18 (ANNC_ID,MG_TPO_CD,TPO_CD,EQP_NO,TID,CELL_NO,RACK_NO,SHELF_NO,CARD_NO,PORT_NO,RECOV_NO,CUST_CNT) ";
		query +=  "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		StringTokenizer	tokens;
		String [] columnValue;
		
		// Step3 : Reading File 
		File		file = new File ("data" + File.separator + "CMAP_IF" + File.separator + "ZOSS_NET_DABL_EQUIP_20170719.dat") ;	
		BufferedReader sourceBufferedReader = null;
		try {
			sourceBufferedReader = new BufferedReader (new InputStreamReader (new FileInputStream (file), "MS949"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			oneLine = sourceBufferedReader.readLine();
			preparedStatement = miDb.getPreStmt().getConnection().prepareStatement(oneLine);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Step4 : Line Reading and Token
		try {
			while ((oneLine = sourceBufferedReader.readLine()) != null) {
				oneLine = f1ilteringString(oneLine);
				tokens = new StringTokenizer (oneLine, "|");
				//System.out.println ("Count : " + tokens.countTokens() + ", " + oneLine);		
				columnValue = new String [tokens.countTokens()];
				int index = 0;
				while(tokens.hasMoreTokens()) 
					columnValue [index ++] = tokens.nextToken();
		// Step5 : Insert Table 
				insertTable(columnValue);
				++ commitCount;
				if (commitCount % COMMIT_UNIT == 0)
					preparedStatement.executeBatch();
			}
			preparedStatement.executeBatch ();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			sourceBufferedReader.close ();
			preparedStatement.close();
			miDb.closeDB();
			
		// Step6 : Finish work~!
			Date endDate = new Date();
			System.out.printf("Your Task is done! : ");
			System.out.println(dateFormat.format(endDate));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void changeValue (int index, int columnType, String columnValue) throws Exception {
		try {
			switch (columnType) {
				case Types.NUMERIC: 
				case Types.INTEGER:
					if (columnValue == null || columnValue.equals(" "))
						preparedStatement.setString (index, "");
					else
						preparedStatement.setLong(index, Long.valueOf(columnValue));
					break;
				case Types.FLOAT: preparedStatement.setFloat(index, Float.valueOf(columnValue));
					break;
				case Types.DOUBLE: preparedStatement.setDouble (index, Double.valueOf(columnValue));
					break;
				default :
					if (columnValue == null)
						columnValue = "";
					preparedStatement.setString(index, columnValue);
			}
		} catch (Exception e) {
			System.out.println ("index : " + index + ", columnType : " + columnType + ", columnValue : " + columnValue);
		}
	}
	
	public boolean insertTable (String [] columnValue){
		try {
			for (int index = 1; index <= columnValue.length; ++ index)
				changeValue (index, columnType [index - 1], columnValue [index - 1]);
			
			preparedStatement.addBatch ();
		} catch (Exception e) {
			System.out.println ("insertTable Exception :" + e);
		}
		return true;
	}
	
	public boolean deleteTable (String delQuery){
			
		try {
			delStmt = miDb.getConnection().createStatement();
			delStmt.executeQuery(delQuery);
			delStmt.close();
			//stmt = connection.createStatement();
			//stmt.execute(delQuery);
			//stmt.close();
			//connection.commit();
			Date deleteDate = new Date();
			System.out.printf("Your DeleteTask is just Done~! : ");
			System.out.println(dateFormat.format(deleteDate));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean mergeTable (String [] columnValue) {
		int total;
		try {
			for (total = 1; total <= COMPARE_KEYS_COUNT; ++ total)
				changeValue (total, columnType [total - 1], columnValue [total - 1]);	
			for (int index = total; index <= columnValue.length; ++ index, ++ total)
				changeValue (total, columnType [index - 1], columnValue [index - 1]);
			for (int index = 1; index <= columnValue.length; ++ index, ++ total)
				changeValue (total, columnType [index - 1], columnValue [index - 1]);
			
			preparedStatement.addBatch ();
		} catch (Exception e) {
			System.out.println ("mergeTable Exception :" + e);
		}
		return true;
	}
	
	public String f1ilteringString (String oneLine) {
		String str = "";
		for (int index = 0; index < oneLine.length(); ++ index) {
			if (oneLine.charAt(index) == '|' && oneLine.charAt(index + 1) == '|')
				str += oneLine.charAt(index) + " ";
			else if (oneLine.charAt(index) == '|' && oneLine.charAt(index + 1) == ';') {
				str += oneLine.charAt(index) + " ";
			} else if (oneLine.charAt(index) == ';')
				break;
			else
				str += oneLine.charAt(index);
		}
		return str;
	}

	public DBController connectionDb (int kind, String url, String loginName, String passwd) {
		try {
			DBController dbController = new DBController (kind, url, loginName, passwd);
			return dbController;
		} catch (Exception e) {
			System.out.println ("URL : " + url);
			System.out.println ("���� ����  : " + e);
			return null;
		}
	}
	
	
	public static void main(String[] args) {
		
		CmapFileBatch cmap = new CmapFileBatch();
		cmap.excute();
		
	}
}
