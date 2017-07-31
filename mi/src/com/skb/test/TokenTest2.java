package com.skb.test;

import java.util.Enumeration;

public class TokenTest2 {

	
	public class Tokenizer {
		
		public int currentPosition;
		public int newPosition;
        public int maxPosition;
        public String str;
        public String delimiters;
        public boolean retDelims;
        public boolean delimsChanged;
        public int maxDelimCodePoint;
        public boolean hasSurrogates = false;
        public int[] delimiterCodePoints;
	        
        public void setMaxDelimCodePoint() {
	            if (delimiters == null) {
	                maxDelimCodePoint = 0;
	                return;
	            }
	    
	            int m = 0;
	            int c;
	            int count = 0;
	            for (int i = 0; i < delimiters.length(); i += Character.charCount(c)) {
	                c = delimiters.charAt(i);
	                if (c >= Character.MIN_HIGH_SURROGATE && c <= Character.MAX_LOW_SURROGATE) {
	                    c = delimiters.codePointAt(i);
	                    hasSurrogates = true;
	                }
	                if (m < c)
	                    m = c;
	                count++;
	            }
	            maxDelimCodePoint = m;
	    
	            if (hasSurrogates) {
	                delimiterCodePoints = new int[count];
	                for (int i = 0, j = 0; i < count; i++, j += Character.charCount(c)) {
	                    c = delimiters.codePointAt(j);
	                    delimiterCodePoints[i] = c;
	                }
	            }
	        }
	}
	
	
	
	public static void main(String[] args) {
		new TokenTest2();
	}
}