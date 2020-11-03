package com.milkit.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringUtil {

	
	public static String leftPad(String srcStr, String padStr, int totalLength) {
	    if (srcStr.length() >= totalLength) {
	        return srcStr;
	    }
	    StringBuilder strBuilder = new StringBuilder();
	    while (strBuilder.length() < totalLength - srcStr.length()) {
	        strBuilder.append(padStr);
	    }
	    strBuilder.append(srcStr);
	 
	    return strBuilder.toString();
	}
	
	public static String toJsonString(Object obj) throws JsonProcessingException {
	    return (new ObjectMapper()).writerWithDefaultPrettyPrinter().writeValueAsString(obj);
	}
	
    public static String removeMinusChar(String str) {
        return remove(str, '-');
    }
    
    public static String remove(String str, char remove) {
        if (isEmpty(str) || str.indexOf(remove) == -1) {
            return str;
        }
        char[] chars = str.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != remove) {
                chars[pos++] = chars[i];
            }
        }
        return new String(chars, 0, pos);
    }
    
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuffer(str).reverse().toString();
    }
	
}
