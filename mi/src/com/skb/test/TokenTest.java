package com.skb.test;

import java.util.StringTokenizer;

public class TokenTest { 
	
	public static void main(String[] args) { 
		
		String str = "this is my string"; 
		
		String str2 = "this|is||my||string"; 
		
		StringTokenizer st = new StringTokenizer(str); 
		
		StringTokenizer st2 = new StringTokenizer(str2,"|"); 
		
		
		System.out.println("******** The first Token *******");
		System.out.println(st.countTokens());
		
		while(st.hasMoreTokens()) { 
			
			System.out.println(st.nextToken());
		} 
		
		System.out.println(st.countTokens());
		System.out.println("******** The Ending of first Token *******");
		
		
		System.out.println("******** The Second Token *******");
		System.out.println(st2.countTokens());
		
		while(st2.hasMoreTokens()) { 
			
			System.out.println(st2.nextToken());
		} 
		
		System.out.println(st2.countTokens());
		System.out.println("******** The Ending of Second Token *******");
	} 
	
}
