package com.skb.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlJDBC {

    public static void main(String[] argv) {

    	Statement	stmt;
   
        System.out.println("-------- Mysql JDBC Connection Testing ------");

        try {

            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Mysql JDBC Driver?");
            e.printStackTrace();
            return;

        }

        System.out.println("Mysql JDBC Driver Registered!");

        Connection connection = null;

        try {


            connection = DriverManager.getConnection(
            	"jdbc:mysql://localhost:3306/MySQL","root","5264");

            stmt = connection.createStatement();
   			String	query = "SELECT * FROM test.TB_TEST"; 

			ResultSet	resultSet;
			resultSet = stmt.executeQuery(query);
			

			while (resultSet.next()) {
	
				System.out.printf("1:%20ss,2:%20SS, 3:%20s\n",resultSet.getString(1), resultSet.getString(2),resultSet.getString(3));
			}
		
    		stmt.close ();
    		connection.close ();
        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
    }

}