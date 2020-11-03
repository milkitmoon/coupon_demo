package com.milkit.app.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.milkit.app.config.jwt.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CouponSizeCommon {
	
    public static final int COUPON_CD_SIZE = 6;
    public static final int COUPON_SEQ_SIZE = 9;
    public static final int COUPON_CHECKSUM_SIZE = 2;
    
    public static final int COUPON_CHECKSUM_EXCEPT_SIZE = COUPON_CD_SIZE+COUPON_SEQ_SIZE;
    public static final int COUPON_NO_SIZE = COUPON_CHECKSUM_EXCEPT_SIZE+COUPON_CHECKSUM_SIZE;
    
    public static final  int COUPON_LOG_APPR_NO_SIZE = 20;

	
}
