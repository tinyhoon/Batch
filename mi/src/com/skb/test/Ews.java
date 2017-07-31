package com.skb.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Ews {

    public static void main(String[] argv) {

    	//Statement	stmt;
   
        System.out.println("-------- EWS Connection Testing ------");

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

            connection = DriverManager.getConnection(
            	"jdbc:informix-sqli://12.4.8.206:9189/commstsdb:INFORMIXSERVER=oltp_voip;user=voip;password=voip03!@");

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
    		connection.close ();
        } catch (SQLException e) {

            System.out.println("EWS Connection Failed! Check output console");
            e.printStackTrace();
        }
    }

}