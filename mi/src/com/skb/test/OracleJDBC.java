package com.skb.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleJDBC {

    public static void main(String[] argv) {
    	
    	
    	String conn =null;
    	String cmap = null;
    	String pms = null;
    	
    	
    	//************* ITMS ************//
    	
    	//************* SWMS ************//
       	
    	//************* CMAP ************//
    	cmap = "jdbc:oracle:thin:@218.50.241.81:1521:CVMAPDB"; 

    	//************* PMS  ************//
    	pms = "jdbc:oracle:thin:@192.168.0.98:7208:NEXCORE";
    	
        System.out.println("-------- Oracle JDBC Connection Testing ------");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("Oracle JDBC Driver Registered!");

        Connection connection = null;
       // Statement stmt;
        try {
            connection = DriverManager.getConnection(pms, "SKBBATCH", "SKBBATCH");
        	//connection = DriverManager.getConnection(pms);
			//stmt = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

}