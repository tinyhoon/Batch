package com.skb.mi.batch.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBController {
	public	final static	int ORACLE_DB 		= 1;
	public  final static	int MARIA_DB  		= 2;
	public	final static	int INFORMIX_DB   	= 3;
	
	int				dbKind = 0;					// DB 설정이 안되었음.
	String			dbUrl;
	Connection		connection = null ;
	Statement		stmt = null ;

	public DBController (int dbKind, String dbUrl, String loginName, String passwd) throws Exception {
		String	className = "";
		switch (dbKind) {
			case ORACLE_DB: 	className = "oracle.jdbc.driver.OracleDriver"; break;
			case MARIA_DB:		className = "org.mariadb.jdbc.Driver"; break;
			case INFORMIX_DB:	className = "com.informix.jdbc.IfxDriver"; break;
			default:			className = ""; break;
		}
		Class.forName(className);
		connection = DriverManager.getConnection(dbUrl, loginName, passwd);
		stmt = connection.createStatement();
	}
	
	public DBController (int dbKind){
		this.dbKind = dbKind;
		if (dbKind == ORACLE_DB) {
			oracleDbController ();
		}
		else if (dbKind == MARIA_DB) {
			mariaDbController ();
		} else if (dbKind == INFORMIX_DB) {
			informixDbController ();
		} else {
			System.out.println ("지원되지 않는 DB입니다.");
		}
	}

	public DBController (String url) {
		String	userId = "iqcsdb" ;
		String	userPass = "white_00" ;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("DBController:Driver를 찾을 수 없습니다." + e);
		} catch (Exception etc) {
			System.out.println("DBController:" + etc.getMessage());
		}
		
		try {
			connection = DriverManager.getConnection(url, userId, userPass);
			stmt = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("DBController:SQLException : " + e.getMessage());
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
	
	private void oracleDbController () {
		String	dbUrl = "jdbc:oracle:thin:@10.250.212.207:1521:xe";
		String	userId = "iqcsdb" ;
		String	userPass = "white_00" ;
		String	oneLine;
		try {
			FileReader		fileReader = new FileReader ("data" + File.separator + "db.info");
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
			System.out.println("oracleDbController:Driver를 찾을 수 없습니다." + e);
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
	
	private void mariaDbController () {
		String	dbUrl = "jdbc:mariadb://localhost/networkems?useUnicode=true&characterEncoding=UTF8&jdbcCompliantTruncation=false" ;
		String	userId = "westktx" ;
		String	userPass = "NetworkEms" ;
		try {
			Class.forName("org.mariadb.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException e) {
			System.out.println("mariaDbController:Driver를 찾을 수 없습니다." + e);
		} catch (Exception etc) {
			System.out.println("mariaDbController:" + etc.getMessage());
		}
		
		try {
			connection = DriverManager.getConnection(dbUrl, userId, userPass);
			stmt = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("SmariaDbController:QLException : " + e.getMessage());
		}		
	}
	
	private void informixDbController () {
		String	dbUrl = "jdbc:mariadb://localhost/networkems?useUnicode=true&characterEncoding=UTF8&jdbcCompliantTruncation=false" ;
		String	userId = "westktx" ;
		String	userPass = "NetworkEms" ;
		try {
			Class.forName("org.mariadb.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException e) {
			System.out.println("mariaDbController:Driver를 찾을 수 없습니다." + e);
		} catch (Exception etc) {
			System.out.println("mariaDbController:" + etc.getMessage());
		}
		
		try {
			connection = DriverManager.getConnection(dbUrl, userId, userPass);
			stmt = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("SmariaDbController:QLException : " + e.getMessage());
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
