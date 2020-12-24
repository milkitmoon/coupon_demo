package com.milkit.app.util;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

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

import org.apache.commons.lang3.RandomStringUtils;
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
public class Base62UtilTest {

 
    @Test
    public void Base62Util_TEST() {
//		String number = "0000011604908499567000001";
		String number = "9999999999999999999999999";
		
		String encodeResult = Base62Util.encode(number);
		String decodeResult = Base62Util.decode(encodeResult);
		
log.debug("encodeResult:"+encodeResult);
log.debug("decodeResult:"+decodeResult);
    	assumeTrue(decodeResult.equals(number));

	}

}
