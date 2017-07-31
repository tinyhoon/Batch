package com.skb.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBController {
	public	final static	int ORACLE_DB = 1;
	public	final static	int INFORMIX_DB  = 2;
	public	final static	int MYSQL_DB  = 3;
	
	int				dbKind = 0;				
	Connection		connection = null ;
	Statement		stmt = null ;

	public DBController (int dbOpen) {
		if (dbOpen == ORACLE_DB) {
			dbKind = dbOpen;
			oracleDbController ();
		}
		else if (dbOpen == INFORMIX_DB) {
			dbKind = dbOpen;
			informixDbController ();
		}
		else if (dbOpen == MYSQL_DB) {
			dbKind = dbOpen;
			mysqlDbController ();
		}
		else {
			System.out.println ("Not Supported Database System.");
		}
	}
	
	public void closeDB () {
		try {
			stmt.close ();
			connection.close ();
		} catch (Exception e) {
		}
	}
	
	public boolean isOpen () { 
		if (dbKind == 0)
			return false;
		return true;
	}
	
	public void oracleDbController () {
		String	dbUrl = "jdbc:oracle:thin:@10.250.212.207:1521:xe";
		String	userId = "iqcsdb" ;
		String	userPass = "white_00" ;
		String	oneLine;
		try {
			FileReader		fileReader = new FileReader ("data" + File.separator + "Oracle.info");
			BufferedReader	bufferedReader = new BufferedReader (fileReader);
			oneLine = bufferedReader.readLine ();
			dbUrl = "jdbc:oracle:thin:@" + oneLine;
			bufferedReader.close ();
			fileReader.close ();
		} catch (Exception e) {
			dbUrl = "jdbc:oracle:thin:@10.250.212.207:1521:xe";
		}

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("oracleDbController:DriverÎ•? Ï∞æÏùÑ ?àò ?óÜ?äµ?ãà?ã§." + e);
		} catch (Exception etc) {
			System.out.println("oracleDbController:" + etc.getMessage());
		}
		
		try {
			connection = DriverManager.getConnection(dbUrl, userId, userPass);
			stmt = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("oracleDbController:SQLException : " + e.getMessage());
		}		
	}

	public void informixDbController () {
		String	dbUrl = "jdbc:informix-sqli://12.4.8.206:9189/commstsdb:INFORMIXSERVER=oltp_voip";
		String	userId = "voip" ;
		String	userPass = "voip03!@" ;
		String	oneLine;

		try {
			FileReader		fileReader = new FileReader ("data" + File.separator + "Infromix.info");
			BufferedReader	bufferedReader = new BufferedReader (fileReader);
			oneLine = bufferedReader.readLine ();
			dbUrl = oneLine;
			bufferedReader.close ();
			fileReader.close ();
		} catch (Exception e) {
			dbUrl = "jdbc:informix-sqli://12.4.8.206:9189/commstsdb:INFORMIXSERVER=oltp_voip";
		}

		try {
			Class.forName("com.informix.jdbc.IfxDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Can not find Informix Driver" + e);
		} catch (Exception etc) {
			System.out.println("InformixDbController:" + etc.getMessage());
		}
		
		try {
			connection = DriverManager.getConnection(dbUrl, userId, userPass);
			stmt = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("InformixDbController:SQLException : " + e.getMessage());
		}		
	}
	
	public void mysqlDbController () {
		String	dbUrl = "jdbc:mysql://localhost:3306/MySQL";
		String	userId = "root" ;
		String	userPass = "5264" ;
		String	oneLine;
	
		try {
			FileReader		fileReader = new FileReader ("data" + File.separator + "Mysql.info");
			BufferedReader	bufferedReader = new BufferedReader (fileReader);
			oneLine = bufferedReader.readLine ();
			dbUrl = oneLine;
			bufferedReader.close ();
			fileReader.close ();
		} catch (Exception e) {
			dbUrl = "jdbc:mysql://localhost:3306/MySQL";
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Can not find mysql Driver" + e);
		} catch (Exception etc) {
			System.out.println("MysqlController:" + etc.getMessage());
		}
		
		try {
			connection = DriverManager.getConnection(dbUrl, userId, userPass);
			stmt = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("MysqlDbController:SQLException : " + e.getMessage());
		}		
	}
	
	public void executeUpdate (String query) {
		try {
			stmt.executeUpdate (query);
		} catch (Exception e) {
			System.out.println ("executeUpdate:query = " + query);
			System.out.println ("Exception: " + e);
		}
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
}
