package com.skb.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FileTokenTest {
	
	private static String[] outputs;

	public static void main(String[] args) throws IOException { 
		
		FileReader fr = null;
		BufferedReader br = null;
		String tokenStr = null;
		StringTokenizer tokens;
		String oneLine =null;
		//String[] output = null;
		List<String> output = new ArrayList<String>();
		
		try {
			
			
			File fileDir = new File("C:/Project/Resources/CMAP_IF/ZOSS_NET_DABL_OPER_20170719.dat");
			
			try {
				//fr = new FileReader("C:/Project/Resources/test.txt");
				br = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "MS949"));
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			oneLine = br.readLine();				// Skip.
			while ((oneLine = br.readLine()) != null) {
				oneLine = oneLine.substring(0, oneLine.length () - 1);
				
				tokens = new StringTokenizer(oneLine,"|");
				
				
				while(tokens.hasMoreTokens()){
					for (int i=0;i<tokens.countTokens();i++)
					{
						output.add(tokens.nextToken());
					}
				}
			}
			
			/*		
			while ((tokenStr = br.readLine()) != null) {

				tokens = new StringTokenizer(tokenStr, "|");

				while (tokens.hasMoreTokens()) {

					System.out.print(tokens.nextToken());
					//System.out.print(tokens.nextToken());
					System.out.print("\n");
				}
			}
			
			*/
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}

