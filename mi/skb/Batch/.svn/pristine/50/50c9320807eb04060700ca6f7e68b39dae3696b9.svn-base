package com.skb.mi.batch.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.Hashtable;
import java.util.StringTokenizer;

import com.skb.mi.batch.common.DBController;

public class BatchDB {
	
	final private String		MI_DB = "jdbc:oracle:thin:@192.168.0.98:7208:nexcore";
	final private String		SOURCE_DB = "jdbc:oracle:thin:@218.50.241.81:1521:CVMAPDB";
	
	DBController				miDb, sourceDb;
	Hashtable<String,String>	targetQuery;
	
	public BatchDB () {
		miDb = connectionDb (1, MI_DB, "SKBBATCH", "SKBBATCH");
		if (miDb != null) {
			sourceDb = connectionDb (1, SOURCE_DB, "cvmapdba", "vocmp5337!");
			if (sourceDb != null) {
				batchCollection ();
				sourceDb.closeDB();
			}
			miDb.closeDB();
		}
	}
	
	public void getTargetQuery () throws Exception {
		FileReader		targetFileReader = new FileReader ("data" + File.separator + "TargetTable.txt");		
		BufferedReader	targetBufferedReader = new BufferedReader (targetFileReader);
		String			oneLine, value, temp;
		StringTokenizer	tokens;
		String			key;

		targetQuery = new Hashtable<String,String> ();
		while ((oneLine = targetBufferedReader.readLine ()) != null) {
			tokens = new StringTokenizer (oneLine, ":");
			key = tokens.nextToken ();
			value = tokens.nextToken ();
			temp = targetQuery.get (key);
			if (temp == null)
				targetQuery.put(key, value);
			else
				targetQuery.put(key, temp + "|" + value);
		}
		targetBufferedReader.close ();
		targetFileReader.close ();
	}
	
	public void batchCollection () {
		Statement sourceStatement = sourceDb.getStmt();
		Statement miStatement = miDb.getStmt ();
		String		rQuery = "";
		String		wQuery = "";
		String		oneLine, backUpLine = null;
		String []	replaceString;
		String []	query;
		
		String		key;
		
		StringTokenizer	tokens;
		
		try {
			getTargetQuery ();

			FileReader	sourceFileReader = new FileReader ("data" + File.separator + "SourceTable.txt");
			BufferedReader	sourceBufferedReader = new BufferedReader (sourceFileReader);
			while ((oneLine = sourceBufferedReader.readLine ()) != null) {
				tokens = new StringTokenizer (oneLine, ":");
				key = tokens.nextToken ();
				rQuery = tokens.nextToken();
				
				System.out.println ("rQuery : " + rQuery);
				
				backUpLine = targetQuery.get(key);
				tokens = new StringTokenizer (backUpLine, "|");
				query = new String [tokens.countTokens()];
				for (int index = tokens.countTokens () - 1; 0 <= index; --index)
					query [index] = tokens.nextToken();
				
				ResultSet resultSet = sourceStatement.executeQuery(rQuery);
				ResultSetMetaData	resultSetMetaData = resultSet.getMetaData ();
				int columnCount = resultSetMetaData.getColumnCount();
				replaceString = new String [columnCount];
				for (int index = 1; index <= columnCount; ++ index) 
					replaceString [index - 1] = "#" + index;

				while (resultSet.next()) {
					for (int index = 0; index < query.length; ++ index) {
						wQuery = query [index];
						for (int index1 = columnCount; 0 < index1; -- index1)
							wQuery = replaceAll (wQuery, replaceString [index1 - 1], changeValue (index1, resultSetMetaData.getColumnType(index1), resultSet));
						System.out.println ("wQuery : " + wQuery);
						miStatement.executeUpdate(wQuery);
					}
				}
				resultSet.close ();
			}
			sourceBufferedReader.close ();
			sourceFileReader.close ();
		} catch (Exception e) {
			System.out.println ("rQuery : " + rQuery);
			System.out.println ("wQuery : " + wQuery);
			System.out.println ("Exception : " + e);
		}
	}
	
	public String changeValue (int index, int columnType, ResultSet resultSet) throws Exception {
		switch (columnType) {
			case Types.NUMERIC: 
			case Types.INTEGER: return String.valueOf(resultSet.getInt(index));
			case Types.FLOAT: return String.valueOf(resultSet.getFloat(index));
			case Types.DOUBLE: return String.valueOf(resultSet.getDouble(index));
			default : return "'" + resultSet.getString(index) + "'";
		}
	}
	
	public String replaceAll(String query, String remove, String replace) {
		query = query.replace(remove, replace);
		return query;
	}

	public DBController connectionDb (int kind, String url, String loginName, String passwd) {
		try {
			DBController dbController = new DBController (kind, url, loginName, passwd);
			return dbController;
		} catch (Exception e) {
			System.out.println ("URL : " + url);
			System.out.println ("접속 에러  : " + e);
			return null;
		}
	}
	
	public static void main(String[] args) {
		new BatchDB ();
	}
}
