package com.skb.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InfomixJDBC {

    public static void main(String[] argv) {

    	Statement	stmt;
    	String conn =null;
    	String comm = null;
    	String stat = null;
    	String toll = null;
    	String local = null;
    	String ews = null;
    	
    	
    	//************* UNMS ************//
    	 comm = "jdbc:informix-sqli://12.4.8.206:9189/commstsdb:INFORMIXSERVER=oltp_voip;user=voip;password=voip03!@";
         stat = "jdbc:informix-sqli://12.4.8.206:9189/voipsttdb:INFORMIXSERVER=oltp_voip;user=voip;password=voip03!@";
    	 toll = "jdbc:informix-sqli://12.4.8.206:9189/tollsttdb:INFORMIXSERVER=oltp_toll;user=voip;password=voip03!@";
    	 local = "jdbc:informix-sqli://12.4.8.206:9190/pstnsttdb:INFORMIXSERVER=oltp_pstn;user=pstn;password=pstn03!@";
    	
    	//************* EWS ************//
    	 ews = "jdbc:informix-sqli://12.4.8.206:9199/ews=ews_svr;user=ews;password=ews666!@";
    	
    	//***** 연결할 DB String값 입력 //
    	 conn = local;
   
        System.out.println("-------- Infomix JDBC Connection Testing ------");

        try {

            Class.forName("com.informix.jdbc.IfxDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Infomix JDBC Driver?");
            e.printStackTrace();
            return;

        }

        System.out.println("Infomix JDBC Driver Registered!");

        Connection connection = null;

        try {

        	/* jdbc:informix-sqli://[hostname]:[port number]/[DB name]:INFORMIXSERVER=[server name] */

            connection = DriverManager.getConnection(conn);
            
            /*
            stmt = connection.createStatement();
   			String	query = "SELECT * FROM CF_TEAM"; 

			ResultSet	resultSet;
			resultSet = stmt.executeQuery(query);
			

			while (resultSet.next()) {

				System.out.printf("1:%20ss, 2:%20s\n",resultSet.getString(1), resultSet.getString(2));
			}
    		stmt.close ();
    		*/
    	
        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
        
    	try {
			connection.close ();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

}