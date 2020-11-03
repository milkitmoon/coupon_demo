package com.milkit.app.util;

import java.util.ArrayList;
import java.util.List;

public class ByteUtil {
	
	public  byte[] intToByteArray(int value) {
		byte[] byteArray = new byte[4];
		byteArray[0] = (byte)(value >> 24);
		byteArray[1] = (byte)(value >> 16);
		byteArray[2] = (byte)(value >> 8);
		byteArray[3] = (byte)(value);
		return byteArray;
	}
	
	public  int byteArrayToInt(byte[] bytes) {
		return ((((int)bytes[0] & 0xff) << 24) |
				(((int)bytes[1] & 0xff) << 16) |
				(((int)bytes[2] & 0xff) << 8) |
				(((int)bytes[3] & 0xff)));
	} 

	public int[] byte2IntList(byte[] bytes) {
    	List<Integer> intList = new ArrayList<Integer>();
    	
    	for(int i=0; i<bytes.length; i++) {
	        int s1 = bytes[i%4] & 0xFF;i++;
	        int s2 = bytes[i%4] & 0xFF;i++;
	        int s3 = bytes[i%4] & 0xFF;i++;
	        int s4 = bytes[i%4] & 0xFF;
	        
	        int intVal = ((s1 << 24) + (s2 << 16) + (s3 << 8) + (s4 << 0));
	        intList.add(Integer.valueOf(intVal));
    	}
    	
        return intList.stream().mapToInt(Integer::intValue).toArray();
    }

}
