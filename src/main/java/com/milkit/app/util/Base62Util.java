package com.milkit.app.util;

import java.math.BigInteger;

public class Base62Util {
    
    public static final String BASE62_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    static final char[] BASE62_ARRAY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    static final BigInteger BASE62_LENGTH = BigInteger.valueOf(BASE62_ARRAY.length);
    static final BigInteger ZERO = BigInteger.valueOf(0);


    public static String encode(String value) {
        BigInteger bigNumber = new BigInteger(value);

        StringBuffer sb = new StringBuffer();
        do {
            int i = bigNumber.remainder(BASE62_LENGTH).intValue();

            sb.append(BASE62_ARRAY[i]);
            bigNumber = bigNumber.divide(BASE62_LENGTH);
        } while (bigNumber.compareTo(ZERO) == 1);

        return sb.toString();
    }

    public static String decode(String value) {
        BigInteger result = BigInteger.valueOf(0);
        BigInteger power = BigInteger.valueOf(1);

        for (int i = 0; i < value.length(); i++) {
            BigInteger digit = BigInteger.valueOf( BASE62_STR.indexOf(value.charAt(i)) );

            result = result.add( digit.multiply(power) );
            power = power.multiply(BASE62_LENGTH);
        }

        return result.toString();
    }


}
