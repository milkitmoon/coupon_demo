package com.milkit.app.temp;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import com.milkit.app.common.exception.handler.ApiResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class TempTest {

 
    @Test
    public void fpcalc_TEST() {
    	double a = 7;
    	double b = 0.1;
    	
    	BigDecimal bigA = BigDecimal.valueOf(a);
    	BigDecimal bigB = BigDecimal.valueOf(b);
    	
    	
    	log.debug((a+b)+"");
    	log.debug((a-b)+"");    	
    	log.debug((a*b)+"");
    	log.debug((a/b)+"");
    	log.debug("\n");
    	log.debug((bigA.add(bigB)).doubleValue()+"");
    	log.debug((bigA.subtract(bigB)).doubleValue()+"");    	
    	log.debug((bigA.multiply(bigB)).doubleValue()+"");
    	log.debug((bigA.divide(bigB, 16, BigDecimal.ROUND_CEILING)).doubleValue()+"");
    }
    
    @Test
    public void fpcalc2_TEST() {
    	int apple = 1;
    	double piceUnit = 0.1;
    	int number = 7;
    	
    	double result = apple - number * piceUnit;
    	
    	log.debug("result:"+result);

    	
    	BigDecimal bigApple = BigDecimal.valueOf(apple);
    	BigDecimal bigPiceUnit = BigDecimal.valueOf(piceUnit);
    	BigDecimal bigNumber = BigDecimal.valueOf(number);
    	
    	double bigResult = bigApple.subtract(bigNumber.multiply(bigPiceUnit)).doubleValue();
    	
    	log.debug("result:"+bigResult);
    }
    
    @Test
    public void fpcalc3_TEST() {
    	BigDecimal maxAvailChangeAmtDeci = (new BigDecimal(10000l)).multiply( ((new BigDecimal(100f).subtract(new BigDecimal(60f))).divide(new BigDecimal(100))) );	// faceAmt * ((100 - changeMinUseRate))/100
    	
    	BigDecimal percentageAmountDeci = (new BigDecimal(50000l)).multiply(BigDecimal.valueOf(30.f/100));

    	
    	
    	log.debug("maxAvailChangeAmt:"+maxAvailChangeAmtDeci.longValue());
    	
    	log.debug("percentageAmount:"+percentageAmountDeci.longValue());
    }
    
    @Test
    public void ramdom_TEST() throws NoSuchAlgorithmException {
    	 SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
    	 String randomNum = new Integer(prng.nextInt()).toString();

    	 MessageDigest sha = MessageDigest.getInstance("SHA-1");
    	 byte[] result =  sha.digest(randomNum.getBytes());

log.debug("result size: " + result.length);
    	 
		int[] intArr = byte2IntList(result);
		StringBuffer buff = new StringBuffer();
		for(int intVal : intArr) {
			 buff.append(intVal);
		}
    	
    	 log.debug("Random number: " + randomNum);
    	 log.debug("Message digest: " + new String(result));
    	 log.debug("Message digest Int: " + buff.toString());
    	 
    	 log.debug("UUID randomUUID: " +  UUID.randomUUID().toString());
    }
    
    @Test
    public void ramdom_TEST2() throws NoSuchAlgorithmException {
    	SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        IntStream.range(0, 100).forEach( n -> 
        	executor.execute(() -> {
	    		 log.debug("Random number: " + (new Integer(prng.nextInt()).toString()));
	        })
        );
    }
    
    @Test
    public void ramdom_TEST3() throws NoSuchAlgorithmException {
    	int SET_SIZE_REQUIRED = 1000;
    	int NUMBER_RANGE = 100000000;
    	Set<Integer> set = new HashSet<Integer>(SET_SIZE_REQUIRED);
    	
    	Random random = new Random(System.nanoTime());
    	
    	while(set.size()< SET_SIZE_REQUIRED) {
            while (set.add(random.nextInt(NUMBER_RANGE)) != true);
        }
    	
    	log.debug("Random size : " + set.size());
    	for(Integer val: set) {
    		log.debug("Random: " + val);
    	}
    }
    
    private int[] byte2IntList(byte[] src) {
    	List<Integer> intList = new ArrayList<Integer>();
    	
    	for(int i=0; i<src.length; i++) {
	        int s1 = src[i%4] & 0xFF;i++;
	        int s2 = src[i%4] & 0xFF;i++;
	        int s3 = src[i%4] & 0xFF;i++;
	        int s4 = src[i%4] & 0xFF;
	        
	        int intVal = ((s1 << 24) + (s2 << 16) + (s3 << 8) + (s4 << 0));
	        intList.add(Integer.valueOf(intVal));
    	}
    	
        return intList.stream().mapToInt(Integer::intValue).toArray();
    }
    
    
    
    @Test
    public void Checksum_TEST1() throws NoSuchAlgorithmException {
    	long  result = getCRC32Checksum(intToByteArray(1000000012));
    	
    	log.debug("result: " + result);
    }
    
    private long  getCRC32Checksum(byte[] src) {
    	Checksum crc32 = new CRC32();
        crc32.update(src, 0, src.length);
        return crc32.getValue();
    }
    
    private byte[] intToByteArray(int value) {
		byte[] byteArray = new byte[4];
		byteArray[0] = (byte)(value >> 24);
		byteArray[1] = (byte)(value >> 16);
		byteArray[2] = (byte)(value >> 8);
		byteArray[3] = (byte)(value);
		return byteArray;
	}

    
    


}
