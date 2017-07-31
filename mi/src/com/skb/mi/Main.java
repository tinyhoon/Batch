package com.skb.mi;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.skb.common.DBController;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class Main {
	
	DBController	dbController;
	ResultSet	resultSet;
	Sheet getSheet;
	String tmp;
	
	
	public Main(){
		
		dbController = new DBController(3);			// MYSQL
		System.out.printf("Sucess\n");

				try {
					resultSet = mysqlSelect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					while (resultSet.next()) {
						
						System.out.printf("1:%20ss,2:%20SS, 3:%20s\n",resultSet.getString(1), resultSet.getString(2),resultSet.getString(3));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					tmp = getExcel();
					System.out.printf("Excel : %s\n",tmp);
				} catch (BiffException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		dbController.closeDB();
		System.out.printf("Bye");
		
	}
		

	public ResultSet mysqlSelect () throws SQLException{
	
		String	query = "SELECT * FROM test.TB_TEST"; 
		
		resultSet = dbController.getStmt().executeQuery(query);
		
		return resultSet;		
	}
	
	
	public String getExcel() throws BiffException, IOException{
		
			Workbook getbook = Workbook.getWorkbook(new File("C:/Project/Resources/test.xls"));
			getSheet = getbook.getSheet(0);
			tmp =getSheet.getCell(1, 1).getContents();
			getbook.close();
			
		return tmp;
		
	}
	
	public static void main(String[] argv) {
	 new Main();
	}
	

}

